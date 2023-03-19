package whyzpotato.myreview.dto.review;

import lombok.Data;
import lombok.NoArgsConstructor;
import whyzpotato.myreview.domain.Review;
import whyzpotato.myreview.dto.item.DetailBookDto;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class BookReviewDto {
    @NotNull
    private DetailBookDto item;
    @NotNull
    private ReviewDto review;

    public BookReviewDto(Review review) {
        this.item = new DetailBookDto(review.getItem());
        this.review = new ReviewDto(review);
    }

}
