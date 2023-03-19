package whyzpotato.myreview.security.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import whyzpotato.myreview.domain.Users;
import whyzpotato.myreview.repository.UsersRepository;
import whyzpotato.myreview.security.JwtTokenProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtService;
    private final UsersRepository usersRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            if(oAuth2User.getRole().equals("ROLE_GUEST")) {
                // 추가정보 입력 후 JWT 발행
                String accessToken = jwtService.createToken(oAuth2User.getEmail(), new ArrayList<>(List.of(oAuth2User.getRole())));
                response.addHeader("X-AUTH-TOKEN", accessToken);    //TODO Bearer 추가
                response.sendRedirect("oauth2/sign-up");
                response.setStatus(HttpServletResponse.SC_OK);
                response.setHeader("X-AUTH-TOKEN", accessToken);

//                Users findUser = usersRepository.findByEmail(oAuth2User.getEmail())
//                        .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 시도입니다."));
            } else {
                loginSuccess(response, oAuth2User);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtService.createToken(oAuth2User.getEmail(), new ArrayList<>(List.of(oAuth2User.getRole())));
        response.addHeader("X-AUTH-TOKEN", accessToken);    //TODO Bearer 추가
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("X-AUTH-TOKEN", accessToken);
    }
}
