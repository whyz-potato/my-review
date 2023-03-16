package whyzpotato.myreview.dto.review;

import lombok.Data;
import lombok.NoArgsConstructor;
import whyzpotato.myreview.domain.Review;

import static whyzpotato.myreview.CommonUtils.toLocalDate;
import static whyzpotato.myreview.CommonUtils.toReviewStatus;

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
        if (!status.equals("LIKE")) {
            System.out.println("this.status = " + this.status);
            this.rate = review.getRate();
            this.viewDate = review.getDate().toString();
            this.content = review.getContent();
        }
    }

    public Review toEntity() {
        if (status.equals("LIKE"))
            return Review.builder()
                    .status(toReviewStatus(this.status))
                    .build();
        return Review.builder()
                .status(toReviewStatus(this.status))
                .rate(this.rate)
                .content(this.content)
                .date(toLocalDate(this.viewDate))
                .build();
    }

}
