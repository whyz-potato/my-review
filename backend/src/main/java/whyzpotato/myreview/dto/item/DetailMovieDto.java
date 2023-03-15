package whyzpotato.myreview.dto.item;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import whyzpotato.myreview.domain.Item;
import whyzpotato.myreview.domain.Movie;

import static whyzpotato.myreview.CommonUtils.toLocalDate;
import static whyzpotato.myreview.CommonUtils.unProxy;

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

    public DetailMovieDto(Item movie) {
        Movie unProxyMovie = (Movie) unProxy(movie);
        this.itemId = unProxyMovie.getId();
        this.title = unProxyMovie.getTitle();
        this.image = unProxyMovie.getImage();
        this.releaseDate = Integer.toString(unProxyMovie.getReleaseDate().getYear());
        this.director = unProxyMovie.getDirector();
        this.actors = unProxyMovie.getActors();
    }

    public Movie toEntity() {
        return Movie.builder()
                .title(this.title)
                .director(this.director)
                .releaseDate(toLocalDate(this.releaseDate))
                .image(this.image)
                .actors(this.actors)
                .build();
    }
}
