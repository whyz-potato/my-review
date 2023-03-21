package whyzpotato.myreview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whyzpotato.myreview.domain.Book;
import whyzpotato.myreview.domain.Movie;
import whyzpotato.myreview.domain.Review;
import whyzpotato.myreview.domain.Users;
import whyzpotato.myreview.dto.item.DetailBookDto;
import whyzpotato.myreview.dto.item.DetailMovieDto;
import whyzpotato.myreview.dto.review.*;
import whyzpotato.myreview.repository.ItemRepository;
import whyzpotato.myreview.repository.ReviewRepository;
import whyzpotato.myreview.repository.UsersRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static java.lang.Math.min;
import static whyzpotato.myreview.CommonUtils.toLocalDate;
import static whyzpotato.myreview.CommonUtils.toReviewStatus;

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

        if (reviewDto.getStatus().equals("LIKE")) {
            Review review = Review.builder()
                    .users(users)
                    .item(book)
                    .status(toReviewStatus(reviewDto.getStatus()))
                    .build();
            return reviewRepository.save(review).getId();
        }

        Review review = Review.builder()
                .users(users)
                .item(book)
                .status(toReviewStatus(reviewDto.getStatus()))
                .date(toLocalDate(reviewDto.getViewDate()))
                .content(reviewDto.getContent())
                .rate(reviewDto.getRate())
                .build();
        return reviewRepository.save(review).getId();
    }

    @Transactional(readOnly = true)
    public BookReviewDto findBookReview(Long userId, Long reviewId) {
        Review findReview = reviewRepository.findById(reviewId).get();
        Users users = usersRepository.findById(userId).get();

        if (findReview.getUsers().equals(users)) {
            return new BookReviewDto(findReview);
        }
        throw new NoSuchElementException();
    }

    public Long save(Long usersId, DetailMovieDto movieDto, ReviewDto reviewDto) {
        Users users = usersRepository.findById(usersId).get();

        Movie movie = itemRepository.save(movieDto.toEntity());

        if (reviewDto.getStatus().equals("LIKE")) {
            Review review = Review.builder()
                    .users(users)
                    .item(movie)
                    .status(toReviewStatus(reviewDto.getStatus()))
                    .build();
            return reviewRepository.save(review).getId();
        }

        Review review = Review.builder()
                .users(users)
                .item(movie)
                .status(toReviewStatus(reviewDto.getStatus()))
                .date(toLocalDate(reviewDto.getViewDate()))
                .content(reviewDto.getContent())
                .rate(reviewDto.getRate())
                .build();
        return reviewRepository.save(review).getId();
    }

    @Transactional(readOnly = true)
    public MovieReviewDto findMovieReview(Long userId, Long reviewId) {
        Review findReview = reviewRepository.findById(reviewId).get();
        Users users = usersRepository.findById(userId).get();

        if (findReview.getUsers().equals(users)) {
            return new MovieReviewDto(findReview);
        }
        throw new NoSuchElementException();
    }


    public BookReviewDto updateBookReview(Long userId, Long reviewId, ReviewDto requestDto) {
        Review findReview = reviewRepository.findById(reviewId).get();

        Users users = usersRepository.findById(userId).get();
        if (findReview.getUsers().equals(users)) {
            return new BookReviewDto(findReview.update(requestDto.toEntity()));
        }
        throw new NoSuchElementException();


    }

    public MovieReviewDto updateMovieReview(Long userId, Long reviewId, ReviewDto requestDto) {
        Review findReview = reviewRepository.findById(reviewId).get();

        Users users = usersRepository.findById(userId).get();
        if (findReview.getUsers().equals(users)) {
            return new MovieReviewDto(findReview.update(requestDto.toEntity()));
        }
        throw new NoSuchElementException();
    }

    public void remove(Long userId, Long reviewId) {
        Users users = usersRepository.findById(userId).get();
        Review review = reviewRepository.findById(reviewId).get();
        if (review.getUsers().equals(users))
            reviewRepository.delete(review);
        else
            throw new NoSuchElementException();
    }
}
