package whyzpotato.myreview.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import whyzpotato.myreview.domain.*;
import whyzpotato.myreview.repository.UsersRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
public class YearlyGoalServiceTest {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private YearlyGoalService yearlyGoalService;

    @Test
    public void 목표_히스토리() {
        //given
        Users users = Users.builder().email("test1234@test.com").name("seoyeong").pw("azsxdcfv!1").createDate(LocalDateTime.now()).build();
        usersRepository.save(users);
        Book book1 = Book.builder().title("책1").releaseDate(LocalDate.of(2012, 8, 1)).author("au").description("---").image("img").isbn("7811").build();
        Book book2 = Book.builder().title("책2").releaseDate(LocalDate.of(2012, 8, 2)).author("au").description("---").image("img").isbn("7812").build();
        Book book3 = Book.builder().title("책3").releaseDate(LocalDate.of(2012, 8, 3)).author("au").description("---").image("img").isbn("7813").build();
        Book book4 = Book.builder().title("책4").releaseDate(LocalDate.of(2012, 8, 4)).author("au").description("---").image("img").isbn("7814").build();
        Book book5 = Book.builder().title("책5").releaseDate(LocalDate.of(2012, 8, 5)).author("au").description("---").image("img").isbn("7815").build();
        Book book6 = Book.builder().title("책6").releaseDate(LocalDate.of(2012, 8, 6)).author("au").description("---").image("img").isbn("7816").build();
        itemService.save(book1);
        itemService.save(book2);
        itemService.save(book3);
        itemService.save(book4);
        itemService.save(book5);
        itemService.save(book6);
        reviewService.save((Review.builder().users(users).item(book1).date(LocalDate.of(2021,1,1)).status(ReviewStatus.DONE).rate(4).content("보고있는데 재밌음1").build()));
        reviewService.save((Review.builder().users(users).item(book2).date(LocalDate.of(2022,2,1)).status(ReviewStatus.DONE).rate(4).content("보고있는데 재밌음2").build()));
        reviewService.save((Review.builder().users(users).item(book3).date(LocalDate.of(2022,3,1)).status(ReviewStatus.DONE).rate(4).content("보고있는데 재밌음3").build()));
        reviewService.save((Review.builder().users(users).item(book4).date(LocalDate.of(2022,4,1)).status(ReviewStatus.DONE).rate(4).content("보고있는데 재밌음4").build()));
        reviewService.save((Review.builder().users(users).item(book5).date(LocalDate.of(2023,5,1)).status(ReviewStatus.DONE).rate(4).content("보고있는데 재밌음5").build()));
        reviewService.save((Review.builder().users(users).item(book6).date(LocalDate.of(2023,6,1)).status(ReviewStatus.DONE).rate(4).content("보고있는데 재밌음6").build()));
        //when
        yearlyGoalService.historyYearlyGoal(users.getId());
    }
}
