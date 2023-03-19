package whyzpotato.myreview.security.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import whyzpotato.myreview.domain.Users;
import whyzpotato.myreview.repository.UsersRepository;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UsersRepository usersRepository;

    private static final String KAKAO = "kakao";


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuthAttributes extractAttributes = OAuthAttributes.of(userNameAttributeName, attributes);

        Users createdUser = getUser(extractAttributes);
        return new CustomOAuth2User(
                //Collections.singleton(new SimpleGrantedAuthority(createdUser.getRoles().getKey())),
                Collections.singleton(new SimpleGrantedAuthority(createdUser.getRoles().get(0))),
                attributes,
                extractAttributes.getNameAttributeKey(),
                createdUser.getEmail(),
                createdUser.getRoles().get(0)
        );
    }

    private Users getUser(OAuthAttributes attributes) {
        Users findUser = usersRepository.findBySocialId(attributes.getOauth2UserInfo().getId()).orElse(null);
        if(findUser == null) {
            return saveUser(attributes);
        }
        return findUser;
    }

    private Users saveUser(OAuthAttributes attributes) {
        Users createdUser = attributes.toEntity(attributes.getOauth2UserInfo());
        return usersRepository.save(createdUser);
    }
}
