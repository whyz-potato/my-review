package whyzpotato.myreview.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.query.criteria.internal.SelectionImplementor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whyzpotato.myreview.domain.Book;
import whyzpotato.myreview.domain.Review;
import whyzpotato.myreview.domain.ReviewStatus;
import whyzpotato.myreview.domain.Users;
import whyzpotato.myreview.dto.item.DetailBookDto;
import whyzpotato.myreview.dto.review.NewBookReviewRequestDto;
import whyzpotato.myreview.dto.review.ReviewDto;
import whyzpotato.myreview.dto.review.ReviewListResponseDto;
import whyzpotato.myreview.dto.review.SimpleReviewResponseDto;
import whyzpotato.myreview.repository.ItemRepository;
import whyzpotato.myreview.repository.ReviewRepository;
import whyzpotato.myreview.repository.UsersRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.min;
import static whyzpotato.myreview.CommonUtils.toLocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UsersRepository usersRepository;
    private final ItemRepository itemRepository;

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

    @Transactional(readOnly = true)
    public ReviewListResponseDto search(Long userId, String query, int start, int display) {
        Users users = usersRepository.findById(userId).get();
        List<Review> result = reviewRepository.findBookReviewByUserTitle(users, query, start, display);
        return ReviewListResponseDto.builder()
                .total(result.size())
                .start(start)
                .reviews(
                        result.stream()
                                .map(r -> new SimpleReviewResponseDto(r))
                                .collect(Collectors.toList()))
                .display(min(display, result.size()))
                .build();
    }


    public Long save(Long usersId, DetailBookDto bookDto, ReviewDto reviewDto) {
        Users users = usersRepository.findById(usersId).get();
        
        Book book = itemRepository.save(bookDto.toEntity());

        Review review = Review.builder()
                .users(users)
                .item(book)
                .content(reviewDto.getContent())
                .date(toLocalDate(reviewDto.getViewDate()))
                .rate(reviewDto.getRate())
                .build();
        reviewRepository.save(review);

        return review.getId();
    }
}
