package whyzpotato.myreview.dto.item;

import lombok.Getter;
import whyzpotato.myreview.domain.Book;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class BookSaveRequestDto {
    private String title;
    private String releaseDate;
    private String image;
    private String description;
    private String author;
    private String isbn;

    public Book toEntity() {
        return Book.builder()
                .title(this.title)
                .releaseDate(toLocalDate(this.releaseDate))
                .image(this.image)
                .description(this.description)
                .author(this.author)
                .isbn(this.isbn)
                .build();
    }

    public LocalDate toLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }


}
