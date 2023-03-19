package whyzpotato.myreview.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import whyzpotato.myreview.dto.review.BookReviewDto;
import whyzpotato.myreview.dto.review.MovieReviewDto;
import whyzpotato.myreview.dto.review.ReviewDto;
import whyzpotato.myreview.dto.review.ReviewListResponseDto;
import whyzpotato.myreview.service.ReviewService;

import javax.validation.Valid;

import static whyzpotato.myreview.CommonUtils.displayToInt;
import static whyzpotato.myreview.CommonUtils.startToInt;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/v1/review/book/new")
    public ResponseEntity newBookReview(@RequestParam("id") Long userId,
                                        @RequestBody @Valid BookReviewDto request) {
        return new ResponseEntity(new createdBodyDto(reviewService.save(userId, request.getItem(), request.getReview())), HttpStatus.CREATED);
    }

    @GetMapping("/v1/review/book/search")
    public ResponseEntity<ReviewListResponseDto> myBookReview(@RequestParam("id") Long userId,
                                                              @RequestParam("q") String query,
                                                              @RequestParam("start") String start,
                                                              @RequestParam("display") String display) {

        return new ResponseEntity<>(reviewService.search(userId, query, startToInt(start), displayToInt(display)), HttpStatus.OK);
    }


    @GetMapping("/v1/review/book/{userId}/{reviewId}")
    public ResponseEntity<BookReviewDto> myBookReview(@PathVariable("userId") Long userId,
                                                      @PathVariable("reviewId") Long reviewId) {
        return new ResponseEntity<>(reviewService.findBookReview(userId, reviewId), HttpStatus.OK);
    }

    @PutMapping("/v1/review/book/{userId}/{reviewId}")
    public ResponseEntity<BookReviewDto> updateBookReview(@PathVariable("userId") Long userId,
                                                          @PathVariable("reviewId") Long reviewId,
                                                          @RequestBody @Valid ReviewDto requestDto) {
        return new ResponseEntity<>(reviewService.updateBookReview(userId, reviewId, requestDto), HttpStatus.OK);
    }


    @PostMapping("/v1/review/movie/new")
    public ResponseEntity newMovieReview(@RequestParam("id") Long userId,
                                         @RequestBody @Valid MovieReviewDto request) {
        return new ResponseEntity(new createdBodyDto(reviewService.save(userId, request.getItem(), request.getReview())), HttpStatus.CREATED);
    }

    @GetMapping("/v1/review/movie/search")
    public ResponseEntity<ReviewListResponseDto> myMovieReview(@RequestParam("id") Long userId,
                                                               @RequestParam("q") String query,
                                                               @RequestParam("start") String start,
                                                               @RequestParam("display") String display) {

        return new ResponseEntity<>(reviewService.search(userId, query, startToInt(start), displayToInt(display)), HttpStatus.OK);
    }

    @GetMapping("/v1/review/movie/{userId}/{reviewId}")
    public ResponseEntity<MovieReviewDto> myMovieReview(@PathVariable("userId") Long userId,
                                                        @PathVariable("reviewId") Long reviewId) {
        return new ResponseEntity<>(reviewService.findMovieReview(userId, reviewId), HttpStatus.OK);
    }

    @PutMapping("/v1/review/movie/{userId}/{reviewId}")
    public ResponseEntity<MovieReviewDto> updateMovieReview(@PathVariable("userId") Long userId,
                                                            @PathVariable("reviewId") Long reviewId,
                                                            @RequestBody @Valid ReviewDto requestDto) {
        return new ResponseEntity<>(reviewService.updateMovieReview(userId, reviewId, requestDto), HttpStatus.OK);
    }

    @Data
    protected class createdBodyDto {
        private Long id;

        public createdBodyDto(Long id) {
            this.id = id;
        }
    }
}
