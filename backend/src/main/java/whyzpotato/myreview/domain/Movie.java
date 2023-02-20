package whyzpotato.myreview.domain;

import lombok.Getter;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Getter
public class Movie extends Item {

    private String director;

    private String actors;

    public static Movie createMovie(String title, LocalDate releaseDate, String image, String description, String director, String actors){
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
