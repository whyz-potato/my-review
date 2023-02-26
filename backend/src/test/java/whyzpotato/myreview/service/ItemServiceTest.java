package whyzpotato.myreview.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import whyzpotato.myreview.domain.Book;
import whyzpotato.myreview.domain.Movie;
import whyzpotato.myreview.repository.ItemRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemRepository itemRepository;

    @Test
    void saveBook() {
        //given
        Book book = Book.builder()
                .title("bookTitle")
                .isbn("123456")
                .build();

        //when
        itemService.save(book);

        //then
        assertThat(book.getId()).isNotNull();
        Book findBook = (Book) itemRepository.findById(book.getId());
    }

    @Test
    void saveMovie() {
        //given
        Movie movie = Movie.builder()
                .title("movieTitle")
                .director("director")
                .build();

        //when
        itemService.save(movie);

        //then
        assertThat(movie.getId()).isNotNull();
        Movie findMovie = (Movie) itemRepository.findById(movie.getId());
    }

    @Test
    @DisplayName("이미 DB에 존재하는 책을 등록하려고 하는 경우 IllegalStateException이 발생해야한다.")
    void duplicatedBook() {
        //given
        Book book1 = Book.builder()
                .title("bookTitle")
                .isbn("123456")
                .build();
        itemService.save(book1);

        //when
        //book2는 아이디는 없지만 book1과 같은 책이다.
        Book book2 = Book.builder()
                .title("bookTitle")
                .isbn("123456")
                .build();

        //then
        assertThrows(IllegalStateException.class, () -> itemService.save(book2));
    }

    @Test
    @DisplayName("이미 DB에 존재하는 영화을 등록하려고 하는 경우 IllegalStateException이 발생해야한다.")
    void duplicatedMovie() {
        //given
        Movie movie1 = Movie.builder()
                .title("movieTitle")
                .director("dir")
                .build();
        itemService.save(movie1);

        //when
        //movie2는 아이디는 없지만 movie1과 같은 책이다.
        Movie movie2 = Movie.builder()
                .title("movieTitle")
                .director("dir")
                .build();

        //then
        assertThrows(IllegalStateException.class, () -> itemService.save(movie2));
    }

}