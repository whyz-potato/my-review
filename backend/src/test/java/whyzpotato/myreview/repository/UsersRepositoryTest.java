package whyzpotato.myreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import whyzpotato.myreview.domain.Users;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void saveTest() {

        //given
        Users originUsers = Users
                .builder()
                .email("test1234@test.com")
                .name("seoyeong")
                .pw("azsxdcfv!1")
                .createDate(LocalDateTime.now())
                .build();
        //when
        Users savedUsers = usersRepository.save(originUsers);

        //then
        assertThat(savedUsers.getId()).isNotNull();
        assertThat(savedUsers.getEmail()).isEqualTo("test1234@test.com");
        assertThat(savedUsers.getName()).isEqualTo("seoyeong");
        assertThat(savedUsers.getPw()).isEqualTo("azsxdcfv!1");
        assertThat(savedUsers.getCreateDate()).isEqualTo(originUsers.getCreateDate());

    }

    @Test
    public void findByEmailTest() {
        //given
        Users originUsers = Users
                .builder()
                .email("test1234@test.com")
                .name("seoyeong")
                .pw("azsxdcfv!1")
                .createDate(LocalDateTime.now())
                .build();
        usersRepository.save(originUsers);

        //when
        Optional<Users> findUsers = usersRepository.findByEmail("test1234@test.com");

        //then
        assertThat(findUsers.get().getId()).isNotNull();
        assertThat(findUsers.get().getEmail()).isEqualTo("test1234@test.com");
        assertThat(findUsers.get().getName()).isEqualTo("seoyeong");
        assertThat(findUsers.get().getPw()).isEqualTo("azsxdcfv!1");
        assertThat(findUsers.get().getCreateDate()).isEqualTo(originUsers.getCreateDate());

    }

    @Test
    public void findByNonExistentEmail() {
        //when
        Optional<Users> findUsers = usersRepository.findByEmail("nonExistent@test.com");

        //then
        assertThat(findUsers.isEmpty()).isTrue();
    }
}
