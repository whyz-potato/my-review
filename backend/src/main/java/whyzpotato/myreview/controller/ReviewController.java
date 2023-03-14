package whyzpotato.myreview.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import whyzpotato.myreview.dto.review.NewBookReviewRequestDto;
import whyzpotato.myreview.dto.review.ReviewListResponseDto;
import whyzpotato.myreview.repository.ReviewRepository;
import whyzpotato.myreview.service.ReviewService;

import javax.validation.Valid;

import static java.lang.Math.min;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;

    static int startToInt(String start) {
        try {
            Integer number = Integer.valueOf(start);
            return min(1000, number);
        } catch (NumberFormatException ex) {
            return 1;
        }
    }

    static int displayToInt(String display) {
        try {
            Integer number = Integer.valueOf(display);
            return min(100, number);
        } catch (NumberFormatException ex) {
            return 10;
        }
    }

    @PostMapping("/v1/review/book/new")
    public ResponseEntity newBookReview(@RequestParam("id") Long userId,
                                        @RequestBody @Valid NewBookReviewRequestDto request) {
        return new ResponseEntity(new createdBodyDto(reviewService.save(userId, request.getItem(), request.getReview())), HttpStatus.CREATED);
    }

    @GetMapping("/v1/review/book/search")
    public ResponseEntity<ReviewListResponseDto> myBookReview(@RequestParam("id") Long userId,
                                                              @RequestParam("q") String query,
                                                              @RequestParam("start") String start,
                                                              @RequestParam("display") String display) {

        return new ResponseEntity<>(reviewService.search(userId, query, startToInt(start), displayToInt(display)), HttpStatus.OK);
    }

    @Data
    protected class createdBodyDto {
        private Long id;

        public createdBodyDto(Long id) {
            this.id = id;
        }
    }
}
