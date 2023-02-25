package whyzpotato.myreview.repository;

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


    @Test
    void save() {
        //given
        Users users1 = Users
                .builder()
                .email("test1@test.com")
                .name("user1")
                .pw("aa")
                .createDate(LocalDateTime.now())
                .build();
        usersRepository.save(users1);
        Book book = Book.builder()
                .title("책")
                .releaseDate(LocalDate.of(2022, 8, 13))
                .author("au")
                .description("---")
                .image("img")
                .isbn("7813")
                .build();
        itemRepository.save(book);

        //when
        Review review = (Review.builder()
                .users(users1)
                .item(book)
                .date(LocalDate.now())
                .status(ReviewStatus.WATCHING)
                .rate(4)
                .content("보고있는데 재밌음")
                .build());
        reviewRepository.save(review);

        //then
        assertThat(review.getId()).isNotNull();
    }

    @Test
    void findById() {
        //given
        Users users1 = Users
                .builder()
                .email("test1@test.com")
                .name("user1")
                .pw("aa")
                .createDate(LocalDateTime.now())
                .build();
        usersRepository.save(users1);
        Book book = Book.builder()
                .title("책")
                .releaseDate(LocalDate.of(2022, 8, 13))
                .author("au")
                .description("---")
                .image("img")
                .isbn("7813")
                .build();
        itemRepository.save(book);
        Review review = (Review.builder()
                .users(users1)
                .item(book)
                .date(LocalDate.now())
                .status(ReviewStatus.WATCHING)
                .rate(4)
                .content("보고있는데 재밌음")
                .build());
        reviewRepository.save(review);

        //when
        Review findReview = reviewRepository.findById(review.getId());

        //then
        assertThat(findReview).isSameAs(review);
    }

    @Test
    void findAll() {
        //given
        Users users1 = Users
                .builder()
                .email("test1@test.com")
                .name("user1")
                .pw("aa")
                .createDate(LocalDateTime.now())
                .build();
        usersRepository.save(users1);
        Book book1 = Book.builder()
                .title("b1")
                .releaseDate(LocalDate.of(2022, 8, 13))
                .author("au")
                .description("---")
                .image("img")
                .isbn("1234")
                .build();
        itemRepository.save(book1);
        Movie movie1 = Movie.builder()
                .title("m1")
                .releaseDate(LocalDate.of(2019, 8, 13))
                .image("url")
                .description("desc")
                .director("dir1")
                .actors("a1, a2, a3")
                .build();
        itemRepository.save(movie1);
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
    void findAllBookReviewByUser() {
        Users users1 = Users
                .builder()
                .email("test1@test.com")
                .name("user1")
                .pw("aa")
                .createDate(LocalDateTime.now())
                .build();
        usersRepository.save(users1);
        Book book1 = Book.builder()
                .title("b1")
                .releaseDate(LocalDate.of(2022, 8, 13))
                .author("au")
                .description("---")
                .image("img")
                .isbn("1234")
                .build();
        Book book2 = Book.builder()
                .title("b2")
                .releaseDate(LocalDate.of(2018, 8, 13))
                .author("au")
                .description("---")
                .image("img")
                .isbn("56789")
                .build();
        Book book3 = Book.builder()
                .title("b3")
                .releaseDate(LocalDate.of(2022, 8, 13))
                .author("au")
                .description("---")
                .image("img")
                .isbn("101112")
                .build();
        Movie movie1 = Movie.builder()
                .title("m1")
                .releaseDate(LocalDate.of(2019, 8, 13))
                .image("url")
                .description("desc")
                .director("dir1")
                .actors("a1, a2, a3")
                .build();
        Movie movie2 = Movie.builder()
                .title("m2")
                .releaseDate(LocalDate.of(2022, 4, 3))
                .image("url")
                .description("desc")
                .director("dir2")
                .actors("actors")
                .build();
        Movie movie3 = Movie.builder()
                .title("m3")
                .releaseDate(LocalDate.of(2023, 12, 7))
                .image("url")
                .description("desc")
                .director("dir3")
                .actors("actors")
                .build();
        itemRepository.save(book1);
        itemRepository.save(book2);
        itemRepository.save(book3);
        itemRepository.save(movie1);
        itemRepository.save(movie2);
        itemRepository.save(movie3);

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
        List<Review> reviews = reviewRepository.findAllBookReviewByUser(users1);

        //then
        assertThat(reviews.size()).isEqualTo(3);
    }

    @Test
    void findLikeBookByUer() {
        //given
        Users users1 = Users
                .builder()
                .email("test1@test.com")
                .name("user1")
                .pw("aa")
                .createDate(LocalDateTime.now())
                .build();
        usersRepository.save(users1);
        Book book1 = Book.builder()
                .title("b1")
                .releaseDate(LocalDate.of(2022, 8, 13))
                .author("au")
                .description("---")
                .image("img")
                .isbn("1234")
                .build();
        Book book2 = Book.builder()
                .title("b2")
                .releaseDate(LocalDate.of(2018, 8, 13))
                .author("au")
                .description("---")
                .image("img")
                .isbn("56789")
                .build();
        Book book3 = Book.builder()
                .title("b3")
                .releaseDate(LocalDate.of(2022, 8, 13))
                .author("au")
                .description("---")
                .image("img")
                .isbn("101112")
                .build();
        itemRepository.save(book1);
        itemRepository.save(book2);
        itemRepository.save(book3);
        reviewRepository.save(Review.builder()
                .users(users1)
                .item(book1)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("content")
                .build());
        reviewRepository.save(Review.builder()
                .users(users1)
                .item(book3)
                .date(LocalDate.now())
                .status(ReviewStatus.LIKE)
                .rate(5)
                .content("content")
                .build());
        reviewRepository.save(Review.builder()
                .users(users1)
                .item(book2)
                .date(LocalDate.now())
                .status(ReviewStatus.LIKE)
                .rate(5)
                .content("content")
                .build());

        //when
        List<Review> reviews = reviewRepository.findLikeBookByUser(users1);

        //then
        assertThat(reviews.size()).isEqualTo(2);
        assertThat(reviews.get(0).getItem()).isSameAs(book3);
        assertThat(reviews.get(1).getItem()).isSameAs(book2);

    }

    @Test
    void findAllMovieReviewByUser() {
        //given
        Users users1 = Users
                .builder()
                .email("test1@test.com")
                .name("user1")
                .pw("aa")
                .createDate(LocalDateTime.now())
                .build();
        Users users2 = Users
                .builder()
                .email("test2@test.com")
                .name("user2")
                .pw("22")
                .createDate(LocalDateTime.now())
                .build();
        Users users3 = Users
                .builder()
                .email("test3@test.com")
                .name("user3")
                .pw("33")
                .createDate(LocalDateTime.now())
                .build();
        usersRepository.save(users1);
        usersRepository.save(users2);
        usersRepository.save(users3);
        Book book1 = Book.builder()
                .title("b1")
                .releaseDate(LocalDate.of(2022, 8, 13))
                .author("au")
                .description("---")
                .image("img")
                .isbn("1234")
                .build();
        Book book2 = Book.builder()
                .title("b2")
                .releaseDate(LocalDate.of(2018, 8, 13))
                .author("au")
                .description("---")
                .image("img")
                .isbn("56789")
                .build();
        Book book3 = Book.builder()
                .title("b3")
                .releaseDate(LocalDate.of(2022, 8, 13))
                .author("au")
                .description("---")
                .image("img")
                .isbn("101112")
                .build();
        itemRepository.save(book1);
        itemRepository.save(book2);
        itemRepository.save(book3);
        Movie movie1 = Movie.builder()
                .title("m1")
                .releaseDate(LocalDate.of(2019, 8, 13))
                .image("url")
                .description("desc")
                .director("dir1")
                .actors("a1, a2, a3")
                .build();
        Movie movie2 = Movie.builder()
                .title("m2")
                .releaseDate(LocalDate.of(2022, 4, 3))
                .image("url")
                .description("desc")
                .director("dir2")
                .actors("actors")
                .build();
        Movie movie3 = Movie.builder()
                .title("m3")
                .releaseDate(LocalDate.of(2023, 12, 7))
                .image("url")
                .description("desc")
                .director("dir3")
                .actors("actors")
                .build();
        itemRepository.save(movie1);
        itemRepository.save(movie2);
        itemRepository.save(movie3);
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
        List<Review> reviews = reviewRepository.findAllMovieReviewByUser(users2);

        //then
        assertThat(reviews.size()).isEqualTo(2);
        assertThat(reviews.get(0)).isSameAs(review5);
        assertThat(reviews.get(1)).isSameAs(review7);
    }

    @Test
    void findLikeMovieByUser() {
        Users users1 = Users
                .builder()
                .email("test1@test.com")
                .name("user1")
                .pw("aa")
                .createDate(LocalDateTime.now())
                .build();
        usersRepository.save(users1);
        Movie movie1 = Movie.builder()
                .title("m1")
                .releaseDate(LocalDate.of(2019, 8, 13))
                .image("url")
                .description("desc")
                .director("dir1")
                .actors("a1, a2, a3")
                .build();
        Movie movie2 = Movie.builder()
                .title("m2")
                .releaseDate(LocalDate.of(2022, 4, 3))
                .image("url")
                .description("desc")
                .director("dir2")
                .actors("actors")
                .build();
        Movie movie3 = Movie.builder()
                .title("m3")
                .releaseDate(LocalDate.of(2023, 12, 7))
                .image("url")
                .description("desc")
                .director("dir3")
                .actors("actors")
                .build();
        itemRepository.save(movie1);
        itemRepository.save(movie2);
        itemRepository.save(movie3);
        Review review1 = Review.builder()
                .users(users1)
                .item(movie1)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        Review review2 = Review.builder()
                .users(users1)
                .item(movie3)
                .date(LocalDate.now())
                .status(ReviewStatus.LIKE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        Review review3 = Review.builder()
                .users(users1)
                .item(movie2)
                .date(LocalDate.now())
                .status(ReviewStatus.LIKE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build();
        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);

        //when
        List<Review> reviews = reviewRepository.findLikeMovieByUser(users1);

        //then
        assertThat(reviews.size()).isEqualTo(2);
        assertThat(reviews.get(0).getItem()).isSameAs(movie3);
        assertThat(reviews.get(1).getItem()).isSameAs(movie2);
    }

}