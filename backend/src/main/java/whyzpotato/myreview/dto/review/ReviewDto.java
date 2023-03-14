package whyzpotato.myreview.dto.review;

import lombok.Data;
import lombok.NoArgsConstructor;
import whyzpotato.myreview.domain.Review;
import whyzpotato.myreview.domain.ReviewStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class ReviewDto {
    private Long reviewId;
    private String status;
    private int rate;
    private String viewDate;
    private String content;

    public ReviewDto(Review review) {
        this.reviewId = review.getId();
        this.status = review.getStatus().toString();
        this.rate = review.getRate();
        this.viewDate = review.getDate().toString();
        this.content = review.getContent();
    }

}
