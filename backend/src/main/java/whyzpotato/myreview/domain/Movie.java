package whyzpotato.myreview.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie extends Item {

    private String director;

    private String actors;

    public static Movie createMovie(String title, LocalDate releaseDate, String image, String description, String director, String actors) {
        Movie movie = new Movie();
        movie.title = title;
        movie.releaseDate = releaseDate;
        movie.image = image;
        movie.description = description;
        movie.director = director;
        movie.actors = actors;
        return movie;
    }
}
