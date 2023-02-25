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
public class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UsersRepository usersRepository;

    @Test
    public void saveBook() {
        //given
        Book book = Book.builder()
                .title("test")
                .releaseDate(LocalDate.of(2022, 8, 13))
                .author("au")
                .description("---")
                .image("img")
                .isbn("7813")
                .build();

        //when
        itemRepository.save(book);

        //then
        assertThat(book.getId()).isNotNull();
    }

    @Test
    public void saveMovie() {
        //given
        Movie movie = Movie.builder()
                .title("test")
                .releaseDate(LocalDate.of(2022, 8, 13))
                .image("url")
                .description("desc")
                .director("dir")
                .actors("a1, a2, a3")
                .build();

        //when
        itemRepository.save(movie);

        //then
        assertThat(movie.getId()).isNotNull();
    }

    @Test
    void findById() {
        //given
        Book book = Book.builder()
                .title("test")
                .releaseDate(LocalDate.of(2022, 8, 13))
                .author("au")
                .description("---")
                .image("img")
                .isbn("7813")
                .build();
        itemRepository.save(book);

        //when
        Item findItem = itemRepository.findById(book.getId());

        //then
        assertThat(findItem).isSameAs(book);
    }

    /*
    책검색
    */
    @Test
    void findBookByIsbn() {
        //given
        Book book = Book.builder()
                .title("test")
                .releaseDate(LocalDate.of(2022, 8, 13))
                .author("au")
                .description("---")
                .image("img")
                .isbn("7813")
                .build();
        itemRepository.save(book);

        //when
        Book findBook = itemRepository.findBookByIsbn("7813");

        //then
        assertThat(findBook).isSameAs(book);
    }

    @Test
    void findAllBook() {
        //given
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
                .releaseDate(LocalDate.of(2019, 8, 13))
                .image("url")
                .description("desc")
                .director("dir2")
                .actors("actors")
                .build();
        Movie movie3 = Movie.builder()
                .title("m3")
                .releaseDate(LocalDate.of(2019, 8, 13))
                .image("url")
                .description("desc")
                .director("dir3")
                .actors("actors")
                .build();
        itemRepository.save(movie1);
        itemRepository.save(movie2);
        itemRepository.save(movie3);

        //when
        List<Book> books = itemRepository.findAllBook();

        //then
        assertThat(books.size()).isEqualTo(3);
        assertThat(book1).isIn(books);
        assertThat(book2).isIn(books);
        assertThat(book3).isIn(books);
    }

    @Test
    void top10Book() {
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

        reviewRepository.save(Review.builder()
                .users(users1)
                .item(book2)
                .date(LocalDate.now())
                .status(ReviewStatus.WATCHING)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build());
        reviewRepository.save(Review.builder()
                .users(users2)
                .item(book2)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build());
        reviewRepository.save(Review.builder()
                .users(users3)
                .item(book2)
                .date(LocalDate.now())
                .status(ReviewStatus.WATCHING)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build());
        reviewRepository.save(Review.builder()
                .users(users2)
                .item(book3)
                .date(LocalDate.now())
                .status(ReviewStatus.WATCHING)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build());
        reviewRepository.save(Review.builder()
                .users(users3)
                .item(book3)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build());

        //when
        List<Book> topBooks = itemRepository.top10Book();

        //then
        assertThat(topBooks.size()).isEqualTo(2);
        assertThat(topBooks.get(0)).isSameAs(book2);
        assertThat(topBooks.get(1)).isSameAs(book3);
    }

    @Test
    void newBooks() {
        //given
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

        //when
        List<Book> newBooks = itemRepository.newBooks(10);

        //then
        assertThat(newBooks.get(0)).isSameAs(book1);
        assertThat(newBooks.get(1)).isSameAs(book3);
        assertThat(newBooks.get(2)).isSameAs(book2);

    }

    @Test
    void findLikeBooksByUser() {
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
        List<Book> likeBooksByUser = itemRepository.findLikeBooksByUser(users1);

        //then
        assertThat(likeBooksByUser.size()).isEqualTo(2);
        assertThat(likeBooksByUser.get(0)).isSameAs(book3);
        assertThat(likeBooksByUser.get(1)).isSameAs(book2);
    }

    /*
     * 영화 검색
     * */

    @Test
    void findMovie() {
        //given
        Movie movie = Movie.builder()
                .title("test")
                .releaseDate(LocalDate.of(2022, 8, 13))
                .image("url")
                .description("desc")
                .director("dir")
                .actors("a1, a2, a3")
                .build();
        itemRepository.save(movie);

        //when
        Movie findMovie = itemRepository.findMovieByTitleDirector("test", "dir");

        //then
        assertThat(findMovie).isSameAs(movie);
    }

    @Test
    void findAllMovies() {
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

        //when
        List<Movie> movies = itemRepository.findAllMovie();

        //then
        assertThat(movies.size()).isEqualTo(3);
        assertThat(movie1).isIn(movies);
        assertThat(movie2).isIn(movies);
        assertThat(movie3).isIn(movies);
    }

    @Test
    void top10Movie() {
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

        reviewRepository.save(Review.builder()
                .users(users1)
                .item(movie2)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build());
        reviewRepository.save(Review.builder()
                .users(users2)
                .item(movie2)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build());
        reviewRepository.save(Review.builder()
                .users(users3)
                .item(movie2)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build());
        reviewRepository.save(Review.builder()
                .users(users2)
                .item(movie3)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build());
        reviewRepository.save(Review.builder()
                .users(users3)
                .item(movie3)
                .date(LocalDate.now())
                .status(ReviewStatus.DONE)
                .rate(5)
                .content("다시 봐도 재밌을 것 같음")
                .build());

        //when
        List<Movie> topMovies = itemRepository.top10Movie();

        //then
        assertThat(topMovies.size()).isEqualTo(2);
        assertThat(topMovies.get(0)).isSameAs(movie2);
        assertThat(topMovies.get(1)).isSameAs(movie3);
    }

    @Test
    void newMovies() {
        //given
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

        //when
        List<Movie> newMovies = itemRepository.newMovies(10);

        //then
        assertThat(newMovies.get(0)).isSameAs(movie3);
        assertThat(newMovies.get(1)).isSameAs(movie2);
        assertThat(newMovies.get(2)).isSameAs(movie1);

    }

}