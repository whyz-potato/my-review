package whyzpotato.myreview.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import whyzpotato.myreview.domain.Users;
import whyzpotato.myreview.repository.UsersRepository;
import whyzpotato.myreview.service.UsersService;

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

    @Test
    public void 회원가입() {
        //given
        String email = "test1234@test.com";
        String pw = "azsxdcfv!1";
        String name = "seoyeong";
        //when
        Long users_id = usersService.join(email, pw, name);
        Optional<Users> users = usersRepository.findById(users_id);
        //then
        assertThat(email).isEqualTo(users.get().getEmail());
        assertThat(name).isEqualTo(users.get().getName());
    }

    @Test
    public void 중복_회원가입() {
        //given
        String email = "test1234@test.com";
        String pw = "azsxdcfv!1";
        String name = "seoyeong";
        usersService.join(email, pw, name);

        //when
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> usersService.join(email, pw, name));
        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 이메일입니다.");
    }

    @Test
    public void 로그인() {
        //given
        String email = "test1234@test.com";
        String pw = "azsxdcfv!1";
        String name = "seoyeong";
        usersService.join(email, pw, name);

        //when
        String token = usersService.login(email, pw);

        //given
        assertThat(email).isEqualTo(jwtTokenProvider.getUserPK(token));
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
        usersService.join("test1234@test.com", "azsxdcfv!1", "seoyeong");
        //when
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> usersService.login("test1234@test.com", "wrongpassword"));
        //then
        assertThat(e.getMessage()).isEqualTo("잘못된 비밀번호입니다.");
    }
}
