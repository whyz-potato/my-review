package whyzpotato.myreview.dto.review;

import lombok.Data;
import whyzpotato.myreview.domain.Review;
import whyzpotato.myreview.dto.item.DetailBookDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class NewBookReviewRequestDto {
    @NotNull
    DetailBookDto item;
    @NotNull
    ReviewDto review;

}
