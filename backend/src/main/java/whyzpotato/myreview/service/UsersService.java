package whyzpotato.myreview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import whyzpotato.myreview.domain.Users;
import whyzpotato.myreview.repository.UsersRepository;
import whyzpotato.myreview.security.CustomUserDetails;
import whyzpotato.myreview.security.JwtTokenProvider;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsersRepository usersRepository;

    /**
     * 회원가입
     */
    public Long join(String email, String pw, String name) {
        validateDuplicateUsers(email);
        return usersRepository.save(Users.builder()
                        .email(email)
                        .pw(passwordEncoder.encode(pw))
                        .name(name)
                        .createDate(LocalDateTime.now())
                        .roles(Collections.singletonList("ROLE_USER"))
                        .build()).getId();
    }

    private void validateDuplicateUsers(String email) {
        usersRepository.findByEmail(email).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        });
    }

    /**
     * 로그인
     */
    public String login(String email, String pw) {
        CustomUserDetails users = new CustomUserDetails(usersRepository.findByEmail(email)
                 .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다.")));
        if (!passwordEncoder.matches(pw, users.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(users.getUsername(), users.getRoles());
    }

}
