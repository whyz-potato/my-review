package whyzpotato.myreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import whyzpotato.myreview.domain.Users;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
        assertThat(savedUsers.getDeleteDate()).isNull();
        assertThat(savedUsers.getSnsAccessToken()).isNull();
        assertThat(savedUsers.getProfileImage()).isNull();

    }

    @Test
    public void findByEmailTest(){
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
        Users findUsers = usersRepository.findByEmail("test1234@test.com");

        //then
        assertThat(findUsers.getId()).isNotNull();
        assertThat(findUsers.getEmail()).isEqualTo("test1234@test.com");
        assertThat(findUsers.getName()).isEqualTo("seoyeong");
        assertThat(findUsers.getPw()).isEqualTo("azsxdcfv!1");
        assertThat(findUsers.getCreateDate()).isEqualTo(originUsers.getCreateDate());
        assertThat(findUsers.getDeleteDate()).isNull();
        assertThat(findUsers.getSnsAccessToken()).isNull();
        assertThat(findUsers.getProfileImage()).isNull();

    }
}
