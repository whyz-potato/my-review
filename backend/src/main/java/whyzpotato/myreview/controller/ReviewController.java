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
import java.net.URI;

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

    @PostMapping("/v2/review/book/new")
    public ResponseEntity newBookReviewV2(@RequestParam("id") Long userId,
                                          @RequestBody @Valid BookReviewDto request) {
        Long reviewId = reviewService.save(userId, request.getItem(), request.getReview());
        URI uri = URI.create(String.format("/v1/review/book/%s/%s", userId, reviewId));
        return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    }

    @GetMapping("/v1/review/book/search")
    public ResponseEntity<ReviewListResponseDto> myBookReview(@RequestParam("id") Long userId,
                                                              @RequestParam(value = "q", required = false) String query,
                                                              @RequestParam(value = "start", required = false) String start,
                                                              @RequestParam(value = "display", required = false) String display) {

        return new ResponseEntity<>(reviewService.search(userId, query, startToInt(start, 0), displayToInt(display)), HttpStatus.OK);
    }


    @GetMapping("/v1/review/book/{userId}/{reviewId}")
    public ResponseEntity<BookReviewDto> detailBookReview(@PathVariable("userId") Long userId,
                                                          @PathVariable("reviewId") Long reviewId) {
        return new ResponseEntity<>(reviewService.findBookReview(userId, reviewId), HttpStatus.OK);
    }

    @PutMapping("/v1/review/book/{userId}/{reviewId}")
    public ResponseEntity<BookReviewDto> updateBookReview(@PathVariable("userId") Long userId,
                                                          @PathVariable("reviewId") Long reviewId,
                                                          @RequestBody @Valid ReviewDto requestDto) {
        return new ResponseEntity<>(reviewService.updateBookReview(userId, reviewId, requestDto), HttpStatus.OK);
    }

    @PutMapping("/v2/review/book/{userId}/{reviewId}")
    public ResponseEntity updateBookReviewV2(@PathVariable("userId") Long userId,
                                             @PathVariable("reviewId") Long reviewId,
                                             @RequestBody @Valid ReviewDto requestDto) {
        reviewService.updateBookReview(userId, reviewId, requestDto);
        URI uri = URI.create(String.format("/v1/review/book/%s/%s", userId, reviewId));
        return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    }


    @PostMapping("/v1/review/movie/new")
    public ResponseEntity newMovieReview(@RequestParam("id") Long userId,
                                         @RequestBody @Valid MovieReviewDto request) {
        return new ResponseEntity(new createdBodyDto(reviewService.save(userId, request.getItem(), request.getReview())), HttpStatus.CREATED);
    }

    @PostMapping("/v2/review/movie/new")
    public ResponseEntity newMovieReviewV2(@RequestParam("id") Long userId,
                                           @RequestBody @Valid MovieReviewDto request) {

        Long reviewId = reviewService.save(userId, request.getItem(), request.getReview());

        URI uri = URI.create(String.format("/v1/review/movie/%s/%s", userId, reviewId));
        return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    }


    @GetMapping("/v1/review/movie/search")
    public ResponseEntity<ReviewListResponseDto> myMovieReview(@RequestParam("id") Long userId,
                                                               @RequestParam(value = "q", required = false) String query,
                                                               @RequestParam(value = "start", required = false) String start,
                                                               @RequestParam(value = "display", required = false) String display) {

        return new ResponseEntity<>(reviewService.search(userId, query, startToInt(start, 0), displayToInt(display)), HttpStatus.OK);
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

    @PutMapping("/v2/review/movie/{userId}/{reviewId}")
    public ResponseEntity updateMovieReviewV2(@PathVariable("userId") Long userId,
                                              @PathVariable("reviewId") Long reviewId,
                                              @RequestBody @Valid ReviewDto requestDto) {
        reviewService.updateMovieReview(userId, reviewId, requestDto);
        URI uri = URI.create(String.format("/v1/review/movie/%s/%s", userId, reviewId));
        return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    }

    @DeleteMapping("v1/review/{userId}/{reviewId}")
    public ResponseEntity removeReview(@PathVariable("userId") Long userId,
                                       @PathVariable("reviewId") Long reviewId) {
        reviewService.remove(userId, reviewId);
        return ResponseEntity.noContent().build();
    }

    @Data
    protected class createdBodyDto {
        private Long id;

        public createdBodyDto(Long id) {
            this.id = id;
        }
    }
}
