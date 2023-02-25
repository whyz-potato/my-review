package whyzpotato.myreview.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance
@Getter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id", unique = true, nullable = false)
    protected Long id;

    protected String title;

    protected LocalDate releaseDate;

    protected String image;

    protected String description;

}
