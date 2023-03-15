package whyzpotato.myreview.dto.review;

import lombok.Data;
import lombok.NoArgsConstructor;
import whyzpotato.myreview.domain.Review;
import whyzpotato.myreview.dto.item.DetailMovieDto;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class MovieReviewDto {
    @NotNull
    private DetailMovieDto item;
    @NotNull
    private ReviewDto review;

    public MovieReviewDto(Review review) {
        this.item = new DetailMovieDto(review.getItem());
        this.review = new ReviewDto(review);
    }

}
