package whyzpotato.myreview.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
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

    private String content;

    public static Review createReview(Users users, Item item, LocalDate date, ReviewStatus status, int rate, String content) {
        Review review = new Review();
        review.users = users;
        review.item = item;
        review.date = date;
        review.status = status;
        review.rate = rate;
        review.content = content;
        return review;
    }


}
