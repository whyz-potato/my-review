package whyzpotato.myreview.dto.item;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import whyzpotato.myreview.domain.Book;
import whyzpotato.myreview.domain.Item;

import static whyzpotato.myreview.CommonUtils.toLocalDate;
import static whyzpotato.myreview.CommonUtils.unProxy;

@Data
@NoArgsConstructor
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

    public DetailBookDto(Item book){
        Book unProxyBook = (Book)unProxy(book);
        this.itemId = unProxyBook.getId();
        this.title = unProxyBook.getTitle();
        this.image = unProxyBook.getImage();
        this.description = unProxyBook.getDescription();
        this.releaseDate = unProxyBook.getReleaseDate().toString();
        this.author = unProxyBook.getAuthor();
        this.isbn = unProxyBook.getIsbn();
    }

    public Book toEntity(){
        return Book.builder()
                .title(this.title)
                .isbn(this.isbn)
                .author(this.author)
                .releaseDate(toLocalDate(this.releaseDate))
                .image(this.image)
                .description(this.description)
                .build();
    }


}

