package whyzpotato.myreview.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import whyzpotato.myreview.domain.Users;
import whyzpotato.myreview.exception.DuplicateResourceException;
import whyzpotato.myreview.repository.UsersRepository;
import whyzpotato.myreview.service.UsersService;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class UsersServiceTest {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void 회원가입() {
        //given
        String email = "test1234@test.com";
        String name = "seoyeong";
        String pw = "azsxdcfv!1";
        //when
        Long users_id = usersService.join(email, name, pw);
        Optional<Users> users = usersRepository.findById(users_id);
        //then
        assertThat(users.get().getEmail()).isEqualTo(email);
        assertThat(users.get().getName()).isEqualTo(name);
        assertThat(passwordEncoder.matches(pw, users.get().getPw())).isTrue();
    }

    @Test
    public void 중복_회원가입() {
        //given
        String email = "test1234@test.com";
        String name = "seoyeong";
        String pw = "azsxdcfv!1";
        usersService.join(email, name, pw);

        //when
        DuplicateResourceException e = assertThrows(DuplicateResourceException.class, () -> usersService.join(email, name, pw));
        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 이메일입니다.");
    }

    @Test
    public void 로그인() {
        //given
        String email = "test1234@test.com";
        String name = "seoyeong";
        String pw = "azsxdcfv!1";
        usersService.join(email, name, pw);

        //when
        String token = usersService.login(email, pw);

        //given
        assertThat(jwtTokenProvider.getUserPK(token)).isEqualTo(email);
        System.out.println(">>> token: " + token);
    }

    @Test
    public void 가입되지_않은_이메일() {
        //when
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> usersService.login("test1234@test.com", "azsxdcfv!1"));
        //then
        assertThat(e.getMessage()).isEqualTo("가입되지 않은 이메일입니다.");
    }

    @Test
    public void 비밀번호_오류() {
        //given
        usersService.join("test1234@test.com", "seoyeong", "azsxdcfv!1");
        //when
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> usersService.login("test1234@test.com", "wrongpassword"));
        //then
        assertThat(e.getMessage()).isEqualTo("잘못된 비밀번호입니다.");
    }

    @Test
    public void 회원정보_조회() {
        //given
        usersService.join("test1234@test.com", "seoyeong", "azsxdcfv!1");
        //when
        Map<String, String> usersInfo = usersService.findUsersInfo(usersRepository.findByEmail("test1234@test.com").get().getId());
        //then
        assertThat(usersInfo.get("email")).isEqualTo("test1234@test.com");
        assertThat(usersInfo.get("name")).isEqualTo("seoyeong");
    }

    @Test
    public void 회원정보_변경() {
        //given
        usersService.join("test1234@test.com", "seoyeong", "azsxdcfv!1");
        //when
        usersService.updateUsersInfo(usersRepository.findByEmail("test1234@test.com").get().getId(), "sejong", "HSY");
        //then
        Optional<Users> users2 = usersRepository.findByEmail("test1234@test.com");
        assertThat(users2.get().getName()).isEqualTo("sejong");
        assertThat(passwordEncoder.matches("HSY", users2.get().getPw())).isTrue();
    }

    @Test
    public void 회원탈퇴() {
        //given
        usersService.join("test1234@test.com", "seoyeong", "azsxdcfv!1");
        //when
        usersService.deleteUsers(usersRepository.findByEmail("test1234@test.com").get().getId());
        //
        assertThat(usersRepository.findByEmail("test1234@test.com").isPresent()).isFalse();
    }
}
