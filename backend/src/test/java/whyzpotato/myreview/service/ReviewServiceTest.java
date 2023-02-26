package whyzpotato.myreview.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import whyzpotato.myreview.domain.*;
import whyzpotato.myreview.repository.ReviewRepository;
import whyzpotato.myreview.repository.UsersRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UsersRepository usersRepository;

    private Users users;
    private Book book;
    private Movie movie1, movie2, movie3;


    @BeforeEach
    void init() {
        users = Users
                .builder()
                .email("test1@test.com")
                .name("user1")
                .pw("aa")
                .createDate(LocalDateTime.now())
                .build();
        usersRepository.save(users);
        book = Book.builder()
                .title("책")
                .releaseDate(LocalDate.of(2022, 8, 13))
                .author("au")
                .description("---")
                .image("img")
                .isbn("7813")
                .build();
        itemService.save(book);
        movie1 = Movie.builder()
                .title("m1")
                .releaseDate(LocalDate.of(2019, 8, 13))
                .image("url")
                .description("desc")
                .director("dir1")
                .actors("a1, a2, a3")
                .build();
        movie2 = Movie.builder()
                .title("m2")
                .releaseDate(LocalDate.of(2022, 4, 3))
                .image("url")
                .description("desc")
                .director("dir2")
                .actors("actors")
                .build();
        movie3 = Movie.builder()
                .title("m3")
                .releaseDate(LocalDate.of(2023, 12, 7))
                .image("url")
                .description("desc")
                .director("dir3")
                .actors("actors")
                .build();
        itemService.save(movie1);
        itemService.save(movie2);
        itemService.save(movie3);
    }

    @Test
    void save() {
        //given
        Review review = (Review.builder()
                .users(users)
                .item(book)
                .date(LocalDate.now())
                .status(ReviewStatus.WATCHING)
                .rate(4)
                .content("보고있는데 재밌음")
                .build());

        //when
        reviewService.save(review);

        //then
        assertThat(review.getId()).isNotNull();
    }

    @Test
    void updateToLike() {
        //given
        Review review = (Review.builder()
                .users(users)
                .item(book)
                .date(LocalDate.now())
                .status(ReviewStatus.WATCHING)
                .rate(4)
                .content("보고있는데 재밌음")
                .build());
        reviewService.save(review);

        //when
        reviewService.update(review.getId(), ReviewStatus.LIKE);

        //then
        Review findReview = reviewRepository.findById(review.getId());
        assertThat(findReview.getStatus()).isEqualTo(ReviewStatus.LIKE);
    }

    @Test
    void updateToLikeFail() {
        //given
        Review review = (Review.builder()
                .users(users)
                .item(book)
                .date(LocalDate.now())
                .status(ReviewStatus.WATCHING)
                .rate(4)
                .content("보고있는데 재밌음")
                .build());
        reviewService.save(review);

        //then
        assertThrows(IllegalArgumentException.class,
                () -> reviewService.update(review.getId(), ReviewStatus.LIKE, LocalDate.now(), 4, "관심인데"));
    }

    @Test
    void updateToDone() {
        //given
        Review review = (Review.builder()
                .users(users)
                .item(book)
                .status(ReviewStatus.LIKE)
                .build());
        reviewService.save(review);

        //when
        reviewService.update(review.getId(), ReviewStatus.DONE, LocalDate.now(), 5, "아주 재밌다");

        //then
        Review findReview = reviewRepository.findById(review.getId());
        assertThat(findReview.getStatus()).isEqualTo(ReviewStatus.DONE);
        assertThat(findReview.getRate()).isEqualTo(5);
        assertThat(findReview.getContent()).isEqualTo("아주 재밌다");
    }


    @Test
    void reviewCount() {
        //given
        Review review1 = Review.builder()
                .users(users)
                .item(movie1)
                .date(LocalDate.of(2022, 8, 11))
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        Review review2 = Review.builder()
                .users(users)
                .item(movie3)
                .date(LocalDate.of(2022, 3, 11))
                .status(ReviewStatus.LIKE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        Review review3 = Review.builder()
                .users(users)
                .item(movie2)
                .date(LocalDate.of(2023, 3, 11))
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewService.save(review1);
        reviewService.save(review2);
        reviewService.save(review3);

        //when
        int nReview2021 = reviewService.reviewCount(users, 2021);
        int nReview2022 = reviewService.reviewCount(users, 2022);
        int nReview2023 = reviewService.reviewCount(users, 2023);


        //then
        assertThat(nReview2021).isEqualTo(0);
        assertThat(nReview2022).isEqualTo(1);
        assertThat(nReview2023).isEqualTo(1);
    }
}