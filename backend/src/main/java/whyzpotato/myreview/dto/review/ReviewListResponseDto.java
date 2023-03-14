package whyzpotato.myreview.dto.review;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ReviewListResponseDto {
    int total;
    int start;
    int display;
    List<SimpleReviewResponseDto> reviews;

    @Builder
    public ReviewListResponseDto(int total, int start, int display, List<SimpleReviewResponseDto> reviews){
        this.total = total;
        this.start = start;
        this.display = display;
        this.reviews = reviews;
    }
}
