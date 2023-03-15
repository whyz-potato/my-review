package whyzpotato.myreview.dto.review;

import lombok.Data;
import whyzpotato.myreview.domain.Review;
import whyzpotato.myreview.dto.item.SimpleItemResponseDto;

@Data
public class SimpleReviewResponseDto {
    Long reviewId;
    SimpleItemResponseDto item;

    public SimpleReviewResponseDto(Review review) {
        this.reviewId = review.getId();
        this.item = new SimpleItemResponseDto(review.getItem());
    }
}
