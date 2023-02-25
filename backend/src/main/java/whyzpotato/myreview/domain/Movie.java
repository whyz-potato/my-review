package whyzpotato.myreview.domain;

import lombok.AccessLevel;
import lombok.Builder;
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

    @Builder
    public Movie(String title, LocalDate releaseDate, String image, String description, String director, String actors){
        this.title = title;
        this.releaseDate = releaseDate;
        this.image = image;
        this.description = description;
        this.director = director;
        this.actors = actors;
    }

}
