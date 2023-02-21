package whyzpotato.myreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import whyzpotato.myreview.domain.Users;
import whyzpotato.myreview.domain.YearlyGoal;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class YearlyGoalRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private YearlyGoalRepository yearlyGoalRepository;

    @Test
    public void saveTest() {

        //given
        Users users = Users
                .builder()
                .email("test1234@test.com")
                .name("seoyeong")
                .pw("azsxdcfv!1")
                .createDate(LocalDateTime.now())
                .build();

        usersRepository.save(users);

        YearlyGoal yearlyGoal = YearlyGoal
                .builder()
                .users(users)
                .period(2023)
                .target(12)
                .build();

        //when
        YearlyGoal savedYearlyGoal = yearlyGoalRepository.save(yearlyGoal);

        //then
        assertThat(savedYearlyGoal.getId()).isNotNull();
        assertThat(savedYearlyGoal.getUsers()).isEqualTo(users);
        assertThat(savedYearlyGoal.getPeriod()).isEqualTo(2023);
        assertThat(savedYearlyGoal.getTarget()).isEqualTo(12);
    }

    @Test
    public void findByUsersAndYearTest() {
        //given
        Users users = Users
                .builder()
                .email("test1234@test.com")
                .name("seoyeong")
                .pw("azsxdcfv!1")
                .createDate(LocalDateTime.now())
                .build();

        usersRepository.save(users);

        yearlyGoalRepository.save(YearlyGoal
                .builder()
                .users(users)
                .period(2023)
                .target(12)
                .build());

        //when
        YearlyGoal findYearlyGoal = yearlyGoalRepository.findByUsersAndPeriod(users, 2023);

        //then
        assertThat(findYearlyGoal.getId()).isNotNull();
        assertThat(findYearlyGoal.getUsers()).isEqualTo(users);
        assertThat(findYearlyGoal.getPeriod()).isEqualTo(2023);
        assertThat(findYearlyGoal.getTarget()).isEqualTo(12);
    }

    @Test
    public void findAllByUsersTest() {
        //given
        Users users = Users
                .builder()
                .email("test1234@test.com")
                .name("seoyeong")
                .pw("azsxdcfv!1")
                .createDate(LocalDateTime.now())
                .build();

        usersRepository.save(users);

        yearlyGoalRepository.save(YearlyGoal
                .builder()
                .users(users)
                .period(2023)
                .target(12)
                .build());
        yearlyGoalRepository.save(YearlyGoal
                .builder()
                .users(users)
                .period(2022)
                .target(11)
                .build());
        yearlyGoalRepository.save(YearlyGoal
                .builder()
                .users(users)
                .period(2021)
                .target(10)
                .build());

        //when
        List<YearlyGoal> findYearlyGoalList = yearlyGoalRepository.findAllByUsers(users);

        //then
        assertThat(findYearlyGoalList.size()).isEqualTo(3);
    }
}
