package whyzpotato.myreview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whyzpotato.myreview.domain.Review;
import whyzpotato.myreview.domain.ReviewStatus;
import whyzpotato.myreview.repository.ReviewRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Long saveReview(Review review){
        reviewRepository.save(review);
        return review.getId();
    }

    public void updateReview(Long id, ReviewStatus status){
        Review findReview = reviewRepository.findById(id);
        findReview.changeIntoLike();
    }

    public void updateReview(Long id, ReviewStatus status, LocalDate date, int rate, String content){
        Review findReview = reviewRepository.findById(id);
        findReview.update(status, date, rate, content);
    }


}
