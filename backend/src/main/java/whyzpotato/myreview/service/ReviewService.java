package whyzpotato.myreview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whyzpotato.myreview.domain.Review;
import whyzpotato.myreview.domain.ReviewStatus;
import whyzpotato.myreview.domain.Users;
import whyzpotato.myreview.repository.ReviewRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Long save(Review review) {
        reviewRepository.save(review);
        return review.getId();
    }

    public void update(Long id, ReviewStatus status) {
        Review findReview = reviewRepository.findById(id);
        findReview.changeStatus(status);
    }

    public void update(Long id, ReviewStatus status, LocalDate date, int rate, String content) {
        if (status == ReviewStatus.LIKE)
            throw new IllegalArgumentException("관심 상태에서는 리뷰를 작성할 수 없습니다.");
        Review findReview = reviewRepository.findById(id);
        findReview.update(status, date, rate, content);
    }

    @Transactional(readOnly = true)
    public int reviewCount(Users users, int year) {
        return reviewRepository.countByUserYear(users, year).intValue();
    }


}
