package whyzpotato.myreview.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import whyzpotato.myreview.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UsersRepository usersRepository;

    Users users1, users2, users3;
    Book book1, book2, book3;
    Movie movie1, movie2, movie3;

    @BeforeEach
    void init() {
        users1 = Users
                .builder()
                .email("test1@test.com")
                .name("user1")
                .pw("aa")
                .createDate(LocalDateTime.now())
                .build();
        users2 = Users
                .builder()
                .email("test2@test.com")
                .name("user2")
                .pw("22")
                .createDate(LocalDateTime.now())
                .build();
        users3 = Users
                .builder()
                .email("test3@test.com")
                .name("user3")
                .pw("33")
                .createDate(LocalDateTime.now())
                .build();
        usersRepository.save(users1);
        usersRepository.save(users2);
        usersRepository.save(users3);
        book1 = Book.builder()
                .title("b1")
                .releaseDate(LocalDate.of(2022, 8, 13))
                .author("au")
                .description("---")
                .image("img")
                .isbn("1234")
                .build();
        book2 = Book.builder()
                .title("b2")
                .releaseDate(LocalDate.of(2018, 8, 13))
                .author("au")
                .description("---")
                .image("img")
                .isbn("56789")
                .build();
        book3 = Book.builder()
                .title("b3")
                .releaseDate(LocalDate.of(2022, 8, 13))
                .author("au")
                .description("---")
                .image("img")
                .isbn("101112")
                .build();
        movie1 = Movie.builder()
                .title("m1")
                .releaseDate(LocalDate.of(2019, 8, 13))
                .image("url")
                .director("dir1")
                .actors("a1, a2, a3")
                .build();
        movie2 = Movie.builder()
                .title("m2")
                .releaseDate(LocalDate.of(2022, 4, 3))
                .image("url")
                .director("dir2")
                .actors("actors")
                .build();
        movie3 = Movie.builder()
                .title("m3")
                .releaseDate(LocalDate.of(2023, 12, 7))
                .image("url")
                .director("dir3")
                .actors("actors")
                .build();
        itemRepository.save(book1);
        itemRepository.save(book2);
        itemRepository.save(book3);
        itemRepository.save(movie1);
        itemRepository.save(movie2);
        itemRepository.save(movie3);
    }


    @Test
    void save() {
        //given
        Review review = (Review.builder()
                .users(users1)
                .item(book1)
                .date(LocalDate.now())
                .status(ReviewStatus.WATCHING)
                .rate(4)
                .content("보고있는데 재밌음")
                .build());


        //when
        reviewRepository.save(review);

        //then
        assertThat(review.getId()).isNotNull();
    }

    @Test
    void findById() {
        //given
        Review review = (Review.builder()
                .users(users1)
                .item(book1)
                .date(LocalDate.now())
                .status(ReviewStatus.WATCHING)
                .rate(4)
                .content("보고있는데 재밌음")
                .build());
        reviewRepository.save(review);

        //when
        Review findReview = reviewRepository.findById(review.getId()).get();

        //then
        assertThat(findReview).isSameAs(review);
    }

    @Test
    void findAll() {
        //given
        Review review1 = Review.builder()
                .users(users1)
                .item(movie1)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review1);
        Review review2 = Review.builder()
                .users(users1)
                .item(book1)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review2);

        //when
        List<Review> reviews = reviewRepository.findAll();

        //then
        assertThat(reviews.size()).isEqualTo(2);
        assertThat(review1).isIn(reviews);
        assertThat(review2).isIn(reviews);
    }

    @Test
    void findByUsersItem() {
        //given
        Review review1 = Review.builder()
                .users(users1)
                .item(movie1)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review1);

        //when
        Review findReview = reviewRepository.findByUsersItem(users1, movie1).get();

        //then
        assertThat(findReview).isEqualTo(review1);
    }

    @Test
    void findByUsersItemNull() {
        //given

        //when
        Review findReview = reviewRepository.findByUsersItem(users1, null).orElse(null);

        //then
        assertThat(findReview).isNull();

    }

    @Test
    void findAllBookReviewByUser() {
        Review review1 = Review.builder()
                .users(users1)
                .item(book1)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review1);
        Review review2 = Review.builder()
                .users(users1)
                .item(book3)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review2);
        Review review3 = Review.builder()
                .users(users1)
                .item(book2)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review3);

        //when
        List<Review> reviews = reviewRepository.findAllBookReviewByUser(users1, 0, 100);

        //then
        assertThat(reviews.size()).isEqualTo(3);
    }

    @Test
    void findAllMovieReviewByUser() {
        //given
        Review review1 = Review.builder()
                .users(users1)
                .item(book1)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review1);
        Review review2 = Review.builder()
                .users(users1)
                .item(book3)
                .date(LocalDate.now())
                .status(ReviewStatus.LIKE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review2);
        Review review3 = Review.builder()
                .users(users1)
                .item(book2)
                .date(LocalDate.now())
                .status(ReviewStatus.LIKE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review3);
        Review review4 = Review.builder()
                .users(users1)
                .item(book2)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review4);
        Review review5 = Review.builder()
                .users(users2)
                .item(movie2)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review5);
        Review review6 = Review.builder()
                .users(users3)
                .item(movie2)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review6);
        Review review7 = Review.builder()
                .users(users2)
                .item(movie3)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review7);
        Review review8 = Review.builder()
                .users(users3)
                .item(movie3)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review8);

        //when
        List<Review> reviews = reviewRepository.findAllMovieReviewByUser(users2, 0, 10);

        //then
        assertThat(reviews.size()).isEqualTo(2);
        assertThat(reviews.get(0)).isSameAs(review5);
        assertThat(reviews.get(1)).isSameAs(review7);
    }


    @Test
    void findAllByUserYear() {
        //given
        Review review1 = Review.builder()
                .users(users1)
                .item(movie1)
                .date(LocalDate.of(2022, 8, 11))
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        Review review2 = Review.builder()
                .users(users1)
                .item(movie3)
                .date(LocalDate.of(2022, 3, 11))
                .status(ReviewStatus.LIKE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        Review review3 = Review.builder()
                .users(users1)
                .item(movie2)
                .date(LocalDate.of(2023, 3, 11))
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);

        //when
        List<Review> reviews2022 = reviewRepository.findAllByUserYear(users1, 2022);
        List<Review> reviews2023 = reviewRepository.findAllByUserYear(users1, 2023);

        //then
        assertThat(reviews2022.size()).isEqualTo(1);
        assertThat(review1).isIn(reviews2022);
        assertThat(reviews2023.size()).isEqualTo(1);
        assertThat(review3).isIn(reviews2023);
    }

    @Test
    void countByUserYear() {
        //given
        Review review1 = Review.builder()
                .users(users1)
                .item(movie1)
                .date(LocalDate.of(2022, 8, 11))
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        Review review2 = Review.builder()
                .users(users1)
                .item(movie3)
                .date(LocalDate.of(2022, 3, 11))
                .status(ReviewStatus.LIKE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        Review review3 = Review.builder()
                .users(users1)
                .item(movie2)
                .date(LocalDate.of(2023, 3, 11))
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);

        //when
        Long nReviews2021 = reviewRepository.countByUserYear(users1, 2021);
        Long nReviews2022 = reviewRepository.countByUserYear(users1, 2022);
        Long nReviews2023 = reviewRepository.countByUserYear(users1, 2023);

        //then
        assertThat(nReviews2021).isEqualTo(0);
        assertThat(nReviews2022).isEqualTo(1);
        assertThat(nReviews2023).isEqualTo(1);
    }

    @Test
    void searchReview() {
        //given
        Review review1 = Review.builder()
                .users(users1)
                .item(book1)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review1);
        Review review2 = Review.builder()
                .users(users1)
                .item(book3)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review2);
        Review review3 = Review.builder()
                .users(users1)
                .item(book2)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review3);

        //when
        List<Review> reviews = reviewRepository.findBookReviewByUserTitle(users1, "1", 0, 100);

        //then
        assertThat(reviews.size()).isEqualTo(1);
        assertThat(review1).isIn(reviews);
    }

    @Test
    void searchReviewNoResult() {
        //given
        Review review1 = Review.builder()
                .users(users1)
                .item(book1)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review1);
        Review review2 = Review.builder()
                .users(users1)
                .item(book3)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review2);
        Review review3 = Review.builder()
                .users(users1)
                .item(book2)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review3);

        //when
        List<Review> reviews = reviewRepository.findBookReviewByUserTitle(users1, "m", 0, 100);

        //then
        assertThat(reviews).isEmpty();
    }

    @Test
    void searchReviewEmptyString() {
        //given
        Review review1 = Review.builder()
                .users(users1)
                .item(book1)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review1);
        Review review2 = Review.builder()
                .users(users1)
                .item(book3)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review2);
        Review review3 = Review.builder()
                .users(users1)
                .item(book2)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review3);

        //when
        List<Review> reviews = reviewRepository.findBookReviewByUserTitle(users1, null, 0, 100);

        //then
        assertThat(reviews.size()).isEqualTo(3);
        assertThat(review1).isIn(reviews);
        assertThat(review2).isIn(reviews);
        assertThat(review3).isIn(reviews);
    }

}