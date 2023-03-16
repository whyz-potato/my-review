package whyzpotato.myreview.dto.item;

import lombok.Getter;
import whyzpotato.myreview.domain.Book;

import javax.validation.constraints.NotNull;

import static whyzpotato.myreview.CommonUtils.toLocalDate;

@Getter
public class BookSaveRequestDto {
    @NotNull
    private String title;
    @NotNull
    private String releaseDate;
    @NotNull
    private String image;
    private String description;
    @NotNull
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


}
