package whyzpotato.myreview.dto.item;

import lombok.Getter;
import whyzpotato.myreview.domain.Movie;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static whyzpotato.myreview.CommonUtils.toLocalDate;

@Getter
public class MovieSaveRequestDto {
    @NotNull
    private String title;
    @NotNull
    private String releaseDate;
    @NotNull
    private String image;
    @NotNull
    private String director;
    private String actors;

    public Movie toEntity() {
        return Movie.builder()
                .title(this.title)
                .releaseDate(toLocalDate(this.releaseDate))
                .image(this.image)
                .director(this.director)
                .actors(this.actors)
                .build();
    }
}
