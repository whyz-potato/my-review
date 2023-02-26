package whyzpotato.myreview.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private String secretKey = "sejongwhyzpotato";

    private long tokenValidTime = 30 * 60 * 1000L;  //토큰 유효시간 30분

    private final CustomUserDetailsService userDetailsService;

    //객체 초기화
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());   //secretKey를 Base64로 인코딩
    }

    //JWT 생성
    public String createToken(String userPK, List<String> roles) {  //userPK = email / users_id?
        Claims claims = Jwts.claims().setSubject(userPK);   //jwt payload에 저장되는 정보 단위
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)   //토큰 발행 시간
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)  //사용할 암호화 알고리즘과 signature에 들어갈 secret값 세팅
                .compact();
    }

    //JWT 인증 정보 조회
    public Authentication getAuthentication(String token){
        //TODO token에서 'Beare ' 분리
        UserDetails customUserDetails = userDetailsService.loadUserByUsername(this.getUserPK(token));
        return new UsernamePasswordAuthenticationToken(customUserDetails, "", customUserDetails.getAuthorities());
    }

    //JWT 회원 정보 추출
    public String getUserPK(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    //Request의 Header에서 JWT 가져오기 "X-AUTH-TOKEN":"토큰 값"
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    //JWT의 유효성 만료일자 확인
    public boolean validateToken(String token) {
        try {
            //TODO Bearer 검증
            // if (token == null || !token.startsWith("Bearer ")) {
            //      throw new IllegalArgumentException();
            // }
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }





}
