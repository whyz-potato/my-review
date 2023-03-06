package whyzpotato.myreview.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import whyzpotato.myreview.repository.ReviewRepository;
import whyzpotato.myreview.service.ReviewService;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;

}
