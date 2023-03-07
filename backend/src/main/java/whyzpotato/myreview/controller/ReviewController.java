package whyzpotato.myreview.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import whyzpotato.myreview.repository.ReviewRepository;
import whyzpotato.myreview.service.ReviewService;

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

    @GetMapping("/reviews/book/search")
    public void myBookReview(@RequestParam("id") Long userId,
                             @RequestParam("q") String query,
                             @RequestParam("start") String start,
                             @RequestParam("display") String display){

    }

}
