package whyzpotato.myreview.dto.item;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DetailBookDto {
    private Long itemId;
    private Long reviewId;
    private String title;
    private String image;
    private String description;
    private String releaseDate;
    private String author;
    private String isbn;

    @Builder
    public DetailBookDto(Long itemId, Long reviewId, String title, String image, String description, String releaseDate, String author, String isbn) {
        this.itemId = itemId;
        this.reviewId = reviewId;
        this.title = title;
        this.image = image;
        this.description = description;
        this.releaseDate = releaseDate;
        this.author = author;
        this.isbn = isbn;
    }
}

