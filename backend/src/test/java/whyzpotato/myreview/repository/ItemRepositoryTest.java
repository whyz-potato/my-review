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
public class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void saveBook() {
        Book book = Book.createBook("test", LocalDate.of(2022, 8, 13), "url", "desc", "au", "7813");

        itemRepository.save(book);

        assertThat(book.getId()).isNotNull();
    }

    @Test
    public void saveMovie() {
        Movie movie = Movie.createMovie("test", LocalDate.of(2022, 8, 13), "url", "desc", "dire", "a1, a2, a3");

        itemRepository.save(movie);

        assertThat(movie.getId()).isNotNull();
    }


    @Test
    void findById() {
        Book book = Book.createBook("test", LocalDate.of(2022, 8, 13), "url", "desc", "au", "7813");
        itemRepository.save(book);

        Item findItem = itemRepository.findById(book.getId());

        assertThat(findItem).isSameAs(book);
    }

    /*
    책검색
    */

    @Test
    void findBookByIsbn() {
        Book book = Book.createBook("test", LocalDate.of(2022, 8, 13), "url", "desc", "au", "7813");
        itemRepository.save(book);

        Book findBook = itemRepository.findBookByIsbn("7813");

        assertThat(findBook).isSameAs(book);
    }

    @Test
    void findAllBook() {
        Book book1 = Book.createBook("b1", LocalDate.of(2015, 8, 13), "url", "desc", "au", "7813");
        Book book2 = Book.createBook("b2", LocalDate.of(2018, 8, 13), "url", "desc", "au", "7813");
        Book book3 = Book.createBook("b3", LocalDate.of(2022, 8, 13), "url", "desc", "au", "7813");
        itemRepository.save(book1);
        itemRepository.save(book2);
        itemRepository.save(book3);
        Movie movie1 = Movie.createMovie("m1", LocalDate.of(2019, 8, 13), "url", "desc", "au", "7813");
        Movie movie2 = Movie.createMovie("m2   ", LocalDate.of(2022, 8, 13), "url", "desc", "au", "7813");
        Movie movie3 = Movie.createMovie("m3", LocalDate.of(2023, 8, 13), "url", "desc", "au", "7813");
        itemRepository.save(movie1);
        itemRepository.save(movie2);
        itemRepository.save(movie3);

        List<Book> books = itemRepository.findAllBook();

        assertThat(books.size()).isEqualTo(3);
        assertThat(book1).isIn(books);
        assertThat(book2).isIn(books);
        assertThat(book3).isIn(books);
    }

    @Test
    void top10Book() {
        Users users1 = Users.createUser("user1");
        Users users2 = Users.createUser("user2");
        Users users3 = Users.createUser("user3");
        userRepository.save(users1);
        userRepository.save(users2);
        userRepository.save(users3);
        Book book1 = Book.createBook("b1", LocalDate.of(2015, 8, 13), "url", "desc", "au", "1234");
        Book book2 = Book.createBook("b2", LocalDate.of(2018, 8, 13), "url", "desc", "au", "5678");
        Book book3 = Book.createBook("b3", LocalDate.of(2022, 8, 13), "url", "desc", "au", "9101");
        itemRepository.save(book1);
        itemRepository.save(book2);
        itemRepository.save(book3);
        reviewRepository.save(Review.createReview(users1, book2, LocalDate.now(), ReviewStatus.DONE, 5, "다시 봐도 재밌을 것 같음"));
        reviewRepository.save(Review.createReview(users2, book2, LocalDate.now(), ReviewStatus.DONE, 5, "다시 봐도 재밌을 것 같음"));
        reviewRepository.save(Review.createReview(users3, book2, LocalDate.now(), ReviewStatus.DONE, 5, "다시 봐도 재밌을 것 같음"));
        reviewRepository.save(Review.createReview(users2, book3, LocalDate.now(), ReviewStatus.DONE, 5, "다시 봐도 재밌을 것 같음"));
        reviewRepository.save(Review.createReview(users3, book3, LocalDate.now(), ReviewStatus.DONE, 5, "다시 봐도 재밌을 것 같음"));

        List<Book> topBooks = itemRepository.top10Book();

        assertThat(topBooks.size()).isEqualTo(2);
        assertThat(topBooks.get(0)).isSameAs(book2);
        assertThat(topBooks.get(1)).isSameAs(book3);
    }

    @Test
    void newBooks() {
        Book book1 = Book.createBook("b1", LocalDate.of(2018, 8, 13), "url", "desc", "au", "7813");
        Book book2 = Book.createBook("b2", LocalDate.of(2015, 8, 13), "url", "desc", "au", "7813");
        Book book3 = Book.createBook("b3", LocalDate.of(2022, 8, 13), "url", "desc", "au", "7813");
        itemRepository.save(book1);
        itemRepository.save(book2);
        itemRepository.save(book3);

        List<Book> newBooks = itemRepository.newBooks(10);

        assertThat(newBooks.get(0)).isSameAs(book3);
        assertThat(newBooks.get(1)).isSameAs(book1);
        assertThat(newBooks.get(2)).isSameAs(book2);

    }

    @Test
    void findLikeBooksByUser() {
        Users users1 = Users.createUser("user1");
        userRepository.save(users1);
        Book book1 = Book.createBook("b1", LocalDate.of(2015, 8, 13), "url", "desc", "au", "1234");
        Book book2 = Book.createBook("b2", LocalDate.of(2018, 8, 13), "url", "desc", "au", "5678");
        Book book3 = Book.createBook("b3", LocalDate.of(2022, 8, 13), "url", "desc", "au", "9101");
        itemRepository.save(book1);
        itemRepository.save(book2);
        itemRepository.save(book3);
        reviewRepository.save(Review.createReview(users1, book1, LocalDate.now(), ReviewStatus.DONE, 5, "다시 봐도 재밌을 것 같음"));
        reviewRepository.save(Review.createReview(users1, book3, LocalDate.now(), ReviewStatus.LIKE, 5, "다시 봐도 재밌을 것 같음"));
        reviewRepository.save(Review.createReview(users1, book2, LocalDate.now(), ReviewStatus.LIKE, 5, "다시 봐도 재밌을 것 같음"));

        List<Book> likeBooksByUser = itemRepository.findLikeBooksByUser(users1);

        assertThat(likeBooksByUser.size()).isEqualTo(2);
        assertThat(likeBooksByUser.get(0)).isSameAs(book3);
        assertThat(likeBooksByUser.get(1)).isSameAs(book2);
    }

    /*
     * 영화 검색
     * */

    @Test
    void findMovie() {
        Movie movie = Movie.createMovie("test", LocalDate.of(2022, 8, 13), "url", "desc", "dr", "actors");
        itemRepository.save(movie);

        Movie findMovie = itemRepository.findMovieByTitleDirector("test", "dr");

        assertThat(findMovie).isSameAs(movie);
    }

    @Test
    void findAllMovies() {
        Book book1 = Book.createBook("b1", LocalDate.of(2015, 8, 13), "url", "desc", "au", "234");
        Book book2 = Book.createBook("b2", LocalDate.of(2018, 8, 13), "url", "desc", "au", "a125");
        Book book3 = Book.createBook("b3", LocalDate.of(2022, 8, 13), "url", "desc", "au", "8987");
        itemRepository.save(book1);
        itemRepository.save(book2);
        itemRepository.save(book3);
        Movie movie1 = Movie.createMovie("m1", LocalDate.of(2019, 8, 13), "url", "desc", "au", "actors");
        Movie movie2 = Movie.createMovie("m2   ", LocalDate.of(2022, 8, 13), "url", "desc", "au", "actors");
        Movie movie3 = Movie.createMovie("m3", LocalDate.of(2023, 8, 13), "url", "desc", "au", "actors");
        itemRepository.save(movie1);
        itemRepository.save(movie2);
        itemRepository.save(movie3);

        List<Movie> movies = itemRepository.findAllMovie();

        assertThat(movies.size()).isEqualTo(3);
        assertThat(movie1).isIn(movies);
        assertThat(movie2).isIn(movies);
        assertThat(movie3).isIn(movies);
    }

    @Test
    void top10Movie() {
        Users users1 = Users.createUser("user1");
        Users users2 = Users.createUser("user2");
        Users users3 = Users.createUser("user3");
        userRepository.save(users1);
        userRepository.save(users2);
        userRepository.save(users3);
        Movie movie1 = Movie.createMovie("m1", LocalDate.of(2019, 8, 13), "url", "desc", "au", "actors");
        Movie movie2 = Movie.createMovie("m2   ", LocalDate.of(2022, 8, 13), "url", "desc", "au", "actors");
        Movie movie3 = Movie.createMovie("m3", LocalDate.of(2023, 8, 13), "url", "desc", "au", "actors");
        itemRepository.save(movie1);
        itemRepository.save(movie2);
        itemRepository.save(movie3);
        reviewRepository.save(Review.createReview(users1, movie2, LocalDate.now(), ReviewStatus.DONE, 5, "다시 봐도 재밌을 것 같음"));
        reviewRepository.save(Review.createReview(users2, movie2, LocalDate.now(), ReviewStatus.DONE, 5, "다시 봐도 재밌을 것 같음"));
        reviewRepository.save(Review.createReview(users3, movie2, LocalDate.now(), ReviewStatus.DONE, 5, "다시 봐도 재밌을 것 같음"));
        reviewRepository.save(Review.createReview(users2, movie3, LocalDate.now(), ReviewStatus.DONE, 5, "다시 봐도 재밌을 것 같음"));
        reviewRepository.save(Review.createReview(users3, movie3, LocalDate.now(), ReviewStatus.DONE, 5, "다시 봐도 재밌을 것 같음"));

        List<Movie> topMovies = itemRepository.top10Movie();

        assertThat(topMovies.size()).isEqualTo(2);
        assertThat(topMovies.get(0)).isSameAs(movie2);
        assertThat(topMovies.get(1)).isSameAs(movie3);
    }

    @Test
    void newMovies() {
        Movie movie1 = Movie.createMovie("m1", LocalDate.of(2019, 8, 13), "url", "desc", "au", "actors");
        Movie movie2 = Movie.createMovie("m2   ", LocalDate.of(2022, 8, 13), "url", "desc", "au", "actors");
        Movie movie3 = Movie.createMovie("m3", LocalDate.of(2023, 8, 13), "url", "desc", "au", "actors");
        itemRepository.save(movie1);
        itemRepository.save(movie2);
        itemRepository.save(movie3);

        List<Movie> newMovies = itemRepository.newMovies(10);

        assertThat(newMovies.get(0)).isSameAs(movie3);
        assertThat(newMovies.get(1)).isSameAs(movie2);
        assertThat(newMovies.get(2)).isSameAs(movie1);

    }

}