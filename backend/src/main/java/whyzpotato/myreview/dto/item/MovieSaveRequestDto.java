package whyzpotato.myreview.dto.item;

import lombok.Getter;
import whyzpotato.myreview.domain.Movie;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class MovieSaveRequestDto {
    private String title;
    private String releaseDate;
    private String image;
    private String description;
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

    public LocalDate toLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }


}
