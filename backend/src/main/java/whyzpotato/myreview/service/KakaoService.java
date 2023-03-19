package whyzpotato.myreview.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import whyzpotato.myreview.domain.Users;
import whyzpotato.myreview.dto.KakaoAccessTokenDto;
import whyzpotato.myreview.dto.LoginResponseDto;
import whyzpotato.myreview.repository.UsersRepository;
import whyzpotato.myreview.security.CustomUserDetails;
import whyzpotato.myreview.security.JwtTokenProvider;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final UsersRepository usersRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String getAccessTokenByCode(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "52d20e3992042b5341982239d7e1f86d");
        params.add("redirect_uri", "localhost:8080"+"/oauth2/code/kakao");
        params.add("code", code);
        params.add("client_secret", "");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        String url = "https://kauth.kakao.com/oauth/token";
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        try {
            return objectMapper.readValue(response.getBody(), KakaoAccessTokenDto.class).getAccess_token();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUserInfoByAccessToken(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        String url = "https://kapi.kakao.com/v2/user/me";
        return restTemplate.postForObject(url, request, String.class);
    }

    public String verificationKakao(String code) {
        String accessToken = getAccessTokenByCode(code);
        String userInfo = getUserInfoByAccessToken(accessToken);
        String kakaoId = null;
        try {
            JsonNode jsonNode = objectMapper.readTree(userInfo);
            kakaoId = String.valueOf(jsonNode.get("id"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return kakaoId;
    }

    public LoginResponseDto kakaoLogin(String kakaoId) {

        CustomUserDetails users = new CustomUserDetails(usersRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다.")));
        // 카카오 연동 계정이 아님
//        if (users.isEmpty()){
//            // TODO 카카오한테 회원가입 삭제 요청??
//            // 연동된 계정이 아니라고 이메일로 로그인해서 연동 후 이용하라고 안내
//        }

        return new LoginResponseDto(users.getId(), jwtTokenProvider.createToken(users.getUsername(), users.getRoles()));
    }

}
