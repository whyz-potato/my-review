package whyzpotato.myreview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import whyzpotato.myreview.domain.Users;
import whyzpotato.myreview.dto.LoginResponseDto;
import whyzpotato.myreview.dto.UsersResponseDto;
import whyzpotato.myreview.repository.UsersRepository;
import whyzpotato.myreview.security.CustomUserDetails;
import whyzpotato.myreview.security.JwtTokenProvider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsersRepository usersRepository;

    /**
     * 회원가입
     */
    public Long join(String email, String name, String pw) {
        validateDuplicateUsers(email);
        return usersRepository.save(Users.builder()
                .email(email)
                .name(name)
                .pw(passwordEncoder.encode(pw))
                .createDate(LocalDateTime.now())
                .roles(new ArrayList<>(List.of("ROLE_USER")))
                .build()).getId();
    }

    private void validateDuplicateUsers(String email) {
        usersRepository.findByEmail(email).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        });
    }

    public Long checkEmail(String email) {
        return usersRepository.countByEmail(email);
    }

    /**
     * 로그인
     */
    public LoginResponseDto login(String email, String pw) {
        CustomUserDetails users = new CustomUserDetails(usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다.")));
        if (!passwordEncoder.matches(pw, users.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return new LoginResponseDto(users.getId(), jwtTokenProvider.createToken(users.getUsername(), users.getRoles()));
    }

    /**
     * 회원정보 조회
     */
    public UsersResponseDto findUsersInfo(Long id) {
        Users users = usersRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("올바르지 않은 시도입니다."));
        UsersResponseDto usersResponseDto = new UsersResponseDto(users.getId(), users.getEmail(), users.getName());
        return usersResponseDto;
    }

    /**
     * 회원정보 변경
     */
    public Long updateUsersInfo(Long id, String name, String pw) {
        Users users = usersRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("올바르지 않은 시도입니다."));
        users.updateUsersInfo(name, passwordEncoder.encode(pw));
        usersRepository.save(users);
        return users.getId();
    }

    /**
     * 회원 탈퇴
     */
    public void deleteUsers(Long id) {
        Users users = usersRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("올바르지 않은 시도입니다."));
        usersRepository.delete(users);
    }
}
