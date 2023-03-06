package whyzpotato.myreview.dto.item;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DetailMovieDto {
    private Long itemId;
    private Long reviewId;
    private String title;
    private String image;
    private String releaseDate;
    private String director;
    private String actors;

    @Builder
    public DetailMovieDto(Long itemId, Long reviewId, String title, String image, String releaseDate, String director, String actors) {
        this.itemId = itemId;
        this.reviewId = reviewId;
        this.title = title;
        this.image = image;
        this.releaseDate = releaseDate;
        this.director = director;
        this.actors = actors;
    }
}
