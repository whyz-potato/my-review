package whyzpotato.myreview.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "review_id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

    private int rate;

    @Column(length = 1000)
    @Size(max = 1000)
    private String content;

    @Builder
    public Review(Users users, Item item, LocalDate date, ReviewStatus status, int rate, String content) {
        this.users = users;
        this.item = item;
        this.date = date;
        this.status = status;
        this.rate = rate;
        this.content = content;
    }


}
