package whyzpotato.myreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import whyzpotato.myreview.domain.*;

import java.time.LocalDate;
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
    UserRepository userRepository;


    @Test
    void save() {
        User user = User.createUser("user1");
        userRepository.save(user);
        Item item = Book.createBook("책1", LocalDate.of(2012, 12, 28), "img", "des", "aut", "isbn");
        itemRepository.save(item);


        Review review = Review.createReview(user, item, LocalDate.now(), ReviewStatus.WATCHING, 4, "보고있는데 재밌음");
        reviewRepository.save(review);

        assertThat(review.getId()).isNotNull();
    }

    @Test
    void findById() {
        User user = User.createUser("user1");
        userRepository.save(user);
        Item item = Book.createBook("책1", LocalDate.of(2012, 12, 28), "img", "des", "aut", "isbn");
        itemRepository.save(item);
        Review review = Review.createReview(user, item, LocalDate.now(), ReviewStatus.WATCHING, 4, "보고있는데 재밌음");
        reviewRepository.save(review);

        Review findReview = reviewRepository.findById(review.getId());

        assertThat(findReview).isSameAs(review);
    }

    @Test
    void findAll() {
        User user = User.createUser("user1");
        userRepository.save(user);
        Item book = Book.createBook("책1", LocalDate.of(2012, 12, 28), "img", "des", "aut", "isbn");
        itemRepository.save(book);
        Item movie = Movie.createMovie("영화1", LocalDate.of(2008, 7, 16), "img", "des", "dir", "actors");
        itemRepository.save(movie);
        reviewRepository.save(Review.createReview(user, book, LocalDate.now(), ReviewStatus.WATCHING, 4, "보고있는데 재밌음"));
        reviewRepository.save(Review.createReview(user, movie, LocalDate.now(), ReviewStatus.DONE, 5, "다시 봐도 재밌을 것 같음"));

        List<Review> reviews = reviewRepository.findAll();

        assertThat(reviews.size()).isEqualTo(2);
    }

    @Test
    void findAllBookReviewByUser() {
        User user1 = User.createUser("user1");
        userRepository.save(user1);
        Book book1 = Book.createBook("b1", LocalDate.of(2015, 8, 13), "url", "desc", "au", "1234");
        Book book2 = Book.createBook("b2", LocalDate.of(2018, 8, 13), "url", "desc", "au", "5678");
        Book book3 = Book.createBook("b3", LocalDate.of(2022, 8, 13), "url", "desc", "au", "9101");
        itemRepository.save(book1);
        itemRepository.save(book2);
        itemRepository.save(book3);
        reviewRepository.save(Review.createReview(user1, book1, LocalDate.now(), ReviewStatus.DONE, 5, "다시 봐도 재밌을 것 같음"));
        reviewRepository.save(Review.createReview(user1, book3, LocalDate.now(), ReviewStatus.LIKE, 5, "다시 봐도 재밌을 것 같음"));
        reviewRepository.save(Review.createReview(user1, book2, LocalDate.now(), ReviewStatus.LIKE, 5, "다시 봐도 재밌을 것 같음"));

        List<Review> reviews = reviewRepository.findAllBookReviewByUser(user1);

        assertThat(reviews.size()).isEqualTo(3);
    }

    @Test
    void findLikeBookByUer() {
        User user1 = User.createUser("user1");
        userRepository.save(user1);
        Book book1 = Book.createBook("b1", LocalDate.of(2015, 8, 13), "url", "desc", "au", "1234");
        Book book2 = Book.createBook("b2", LocalDate.of(2018, 8, 13), "url", "desc", "au", "5678");
        Book book3 = Book.createBook("b3", LocalDate.of(2022, 8, 13), "url", "desc", "au", "9101");
        itemRepository.save(book1);
        itemRepository.save(book2);
        itemRepository.save(book3);
        reviewRepository.save(Review.createReview(user1, book1, LocalDate.now(), ReviewStatus.DONE, 5, "다시 봐도 재밌을 것 같음"));
        reviewRepository.save(Review.createReview(user1, book3, LocalDate.now(), ReviewStatus.LIKE, 5, "다시 봐도 재밌을 것 같음"));
        reviewRepository.save(Review.createReview(user1, book2, LocalDate.now(), ReviewStatus.LIKE, 5, "다시 봐도 재밌을 것 같음"));

        List<Review> reviews = reviewRepository.findLikeBookByUser(user1);

        assertThat(reviews.size()).isEqualTo(2);
        assertThat(reviews.get(0).getItem()).isSameAs(book3);
        assertThat(reviews.get(1).getItem()).isSameAs(book2);

    }

    @Test
    void findAllMovieReviewByUser() {
        User user1 = User.createUser("user1");
        User user2 = User.createUser("user2");
        User user3 = User.createUser("user3");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        Book book1 = Book.createBook("b1", LocalDate.of(2015, 8, 13), "url", "desc", "au", "1234");
        Book book2 = Book.createBook("b2", LocalDate.of(2018, 8, 13), "url", "desc", "au", "5678");
        Book book3 = Book.createBook("b3", LocalDate.of(2022, 8, 13), "url", "desc", "au", "9101");
        itemRepository.save(book1);
        itemRepository.save(book2);
        itemRepository.save(book3);
        Movie movie1 = Movie.createMovie("m1", LocalDate.of(2019, 8, 13), "url", "desc", "au", "actors");
        Movie movie2 = Movie.createMovie("m2   ", LocalDate.of(2022, 8, 13), "url", "desc", "au", "actors");
        Movie movie3 = Movie.createMovie("m3", LocalDate.of(2023, 8, 13), "url", "desc", "au", "actors");
        itemRepository.save(movie1);
        itemRepository.save(movie2);
        itemRepository.save(movie3);
        Review review1 = Review.createReview(user1, book1, LocalDate.now(), ReviewStatus.DONE, 5, "다시 봐도 재밌을 것 같음");
        reviewRepository.save(review1);
        Review review2 = Review.createReview(user1, book3, LocalDate.now(), ReviewStatus.LIKE, 5, "다시 봐도 재밌을 것 같음");
        reviewRepository.save(review2);
        Review review3 = Review.createReview(user1, book2, LocalDate.now(), ReviewStatus.LIKE, 5, "다시 봐도 재밌을 것 같음");
        reviewRepository.save(review3);
        Review review4 = Review.createReview(user1, movie2, LocalDate.now(), ReviewStatus.DONE, 5, "다시 봐도 재밌을 것 같음");
        reviewRepository.save(review4);
        Review review5 = Review.createReview(user2, movie2, LocalDate.now(), ReviewStatus.DONE, 5, "다시 봐도 재밌을 것 같음");
        reviewRepository.save(review5);
        Review review6 = Review.createReview(user3, movie2, LocalDate.now(), ReviewStatus.DONE, 5, "다시 봐도 재밌을 것 같음");
        reviewRepository.save(review6);
        Review review7 = Review.createReview(user2, movie3, LocalDate.now(), ReviewStatus.DONE, 5, "다시 봐도 재밌을 것 같음");
        reviewRepository.save(review7);
        Review review8 = Review.createReview(user3, movie3, LocalDate.now(), ReviewStatus.DONE, 5, "다시 봐도 재밌을 것 같음");
        reviewRepository.save(review8);

        List<Review> reviews = reviewRepository.findAllMovieReviewByUser(user2);

        assertThat(reviews.size()).isEqualTo(2);
        assertThat(reviews.get(0)).isSameAs(review5);
        assertThat(reviews.get(1)).isSameAs(review7);
    }

    @Test
    void findLikeMovieByUser() {
        User user1 = User.createUser("user1");
        userRepository.save(user1);
        Movie movie1 = Movie.createMovie("m1", LocalDate.of(2019, 8, 13), "url", "desc", "au", "actors");
        Movie movie2 = Movie.createMovie("m2   ", LocalDate.of(2022, 8, 13), "url", "desc", "au", "actors");
        Movie movie3 = Movie.createMovie("m3", LocalDate.of(2023, 8, 13), "url", "desc", "au", "actors");
        itemRepository.save(movie1);
        itemRepository.save(movie2);
        itemRepository.save(movie3);
        reviewRepository.save(Review.createReview(user1, movie1, LocalDate.now(), ReviewStatus.DONE, 5, "다시 봐도 재밌을 것 같음"));
        reviewRepository.save(Review.createReview(user1, movie3, LocalDate.now(), ReviewStatus.LIKE, 5, "다시 봐도 재밌을 것 같음"));
        reviewRepository.save(Review.createReview(user1, movie2, LocalDate.now(), ReviewStatus.LIKE, 5, "다시 봐도 재밌을 것 같음"));

        List<Review> reviews = reviewRepository.findLikeMovieByUser(user1);

        assertThat(reviews.size()).isEqualTo(2);
        assertThat(reviews.get(0).getItem()).isSameAs(movie3);
        assertThat(reviews.get(1).getItem()).isSameAs(movie2);
    }

}