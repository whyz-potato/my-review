package whyzpotato.myreview.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import whyzpotato.myreview.domain.*;
import whyzpotato.myreview.repository.ItemRepository;
import whyzpotato.myreview.repository.ReviewRepository;
import whyzpotato.myreview.repository.UserRepository;
import whyzpotato.myreview.repository.YearlyGoalRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class YearlyGoalServiceTest {

    @Autowired UserRepository usersRepository;
    @Autowired YearlyGoalRepository yearlyGoalRepository;
    @Autowired YearlyGoalService yearlyGoalService;
    @Autowired ItemRepository itemRepository;
    @Autowired ReviewRepository reviewRepository;

    @Test
    public void reviewCountTest() {
        //given
        Users users = Users.builder().email("test1234@test.com").name("seoyeong").pw("azsxdcfv!1").createDate(LocalDateTime.now()).build();
        usersRepository.save(users);
//        YearlyGoal yearlyGoal = YearlyGoal.builder().users(users).target(2).period(2023).build();
//        yearlyGoalRepository.save(yearlyGoal);
        Item item = Book.createBook("책1", LocalDate.of(2012, 12, 28), "img", "des", "aut", "isbn");
        Item item2 = Book.createBook("책2", LocalDate.of(2013, 11, 15), "img", "des", "aut", "isbn");
        itemRepository.save(item);
        itemRepository.save(item2);
        Review review = Review.createReview(users, item, LocalDate.now(), ReviewStatus.DONE, 4, "보고있는데 재밌음");
        Review review2 = Review.createReview(users, item2, LocalDate.of(2022,01,22), ReviewStatus.DONE, 5, "보고있는데 재밌음22");
        reviewRepository.save(review);
        reviewRepository.save(review2);

        yearlyGoalService.currentGoal(users.getId());

    }

    @Test
    public void 올해_목표_수정() {
        //given
        Users users = Users.builder().email("test1234@test.com").name("seoyeong").pw("azsxdcfv!1").createDate(LocalDateTime.now()).build();
        usersRepository.save(users);
        YearlyGoal yearlyGoal = YearlyGoal.builder().users(users).target(2).period(2023).build();
        yearlyGoalRepository.save(yearlyGoal);
        //when
        yearlyGoalService.updateYearlyGoal(users.getId(), 100);
        //then
        assertThat(yearlyGoal.getTarget()).isEqualTo(100);
    }
}