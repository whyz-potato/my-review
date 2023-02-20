package whyzpotato.myreview.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item {

    private String author;

    private String isbn;

    public static Book createBook(String title, LocalDate releaseDate, String image, String description, String author, String isbn) {
        Book book = new Book();
        book.title = title;
        book.releaseDate = releaseDate;
        book.image = image;
        book.description = description;
        book.author = author;
        book.isbn = isbn;
        return book;
    }

}



