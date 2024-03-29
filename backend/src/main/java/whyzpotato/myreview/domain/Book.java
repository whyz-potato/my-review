package whyzpotato.myreview.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item {

    private String author;

    private String isbn;

    @Column(length = 1000)
    @Size(max = 1000)
    private String description;

    @Builder
    public Book(String title, LocalDate releaseDate, String image, String description, String author, String isbn) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.image = image;
        this.description = description;
        this.author = author;
        this.isbn = isbn;
    }

}



