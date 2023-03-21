package whyzpotato.myreview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whyzpotato.myreview.domain.Book;
import whyzpotato.myreview.domain.Movie;
import whyzpotato.myreview.domain.Review;
import whyzpotato.myreview.domain.Users;
import whyzpotato.myreview.dto.item.*;
import whyzpotato.myreview.repository.ItemRepository;
import whyzpotato.myreview.repository.ReviewRepository;
import whyzpotato.myreview.repository.UsersRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UsersRepository usersRepository;
    private final ReviewRepository reviewRepository;

    public Long save(BookSaveRequestDto dto) {
        return itemRepository.save(dto.toEntity()).getId();
    }

    //TODO MovieDto save

    public DetailBookDto findBookById(Long userId, Long itemId) {
        Book book = (Book) itemRepository.findById(itemId).get();
        return new DetailBookDto(book);
    }

    public DetailMovieDto findMovieById(Long userId, Long itemId) {
        Movie movie = (Movie) itemRepository.findById(itemId).get();
        return new DetailMovieDto(movie);
    }

    public ExploreResponseDto exploreBook(Long userId) {

        Users users = usersRepository.findById(userId).get();
        List<SimpleItemResponseDto> myContent = itemRepository.likeBooksByUser(users)
                .stream()
                .map(i -> new SimpleItemResponseDto(i))
                .collect(Collectors.toList());
        List<SimpleItemResponseDto> top10 = itemRepository.top10Books()
                .stream()
                .map(i -> new SimpleItemResponseDto(i))
                .collect(Collectors.toList());
        List<SimpleItemResponseDto> newBooks = itemRepository.newBooks()
                .stream()
                .map(i -> new SimpleItemResponseDto(i))
                .collect(Collectors.toList());

        return ExploreResponseDto.builder()
                .myContent(myContent)
                .top10(top10)
                .newContent(newBooks)
                .build();
    }

    public ExploreResponseDto exploreMovie(Long userId) {

        Users users = usersRepository.findById(userId).get();
        List<SimpleItemResponseDto> myContent = itemRepository.likeMoviesByUser(users)
                .stream()
                .map(i -> new SimpleItemResponseDto(i))
                .collect(Collectors.toList());
        List<SimpleItemResponseDto> top10 = itemRepository.top10Movies()
                .stream()
                .map(i -> new SimpleItemResponseDto(i))
                .collect(Collectors.toList());
        List<SimpleItemResponseDto> newMovies = itemRepository.newMovies()
                .stream()
                .map(i -> new SimpleItemResponseDto(i))
                .collect(Collectors.toList());

        return ExploreResponseDto.builder()
                .myContent(myContent)
                .top10(top10)
                .newContent(newMovies)
                .build();
    }

    public BookSearchResponseDto naverSearchBook(Long usersId, NaverBookResponseDto naverBookResponseDto) {
        Users users = usersRepository.findById(usersId).get();
        return BookSearchResponseDto.builder()
                .start(naverBookResponseDto.getStart())
                .display(naverBookResponseDto.getDisplay())
                .total(naverBookResponseDto.getTotal())
                .items(naverBookResponseDto.getItems()
                        .stream()
                        .map(naverBook -> toDetailBookDto(users, naverBook))
                        .collect(Collectors.toList()))
                .build();
    }

    public DetailBookDto toDetailBookDto(Users users, NaverBookDto naverBookDto) {
        Book book = itemRepository.findBookByIsbn(naverBookDto.getIsbn()).orElse(null);
        Review review = reviewRepository.findByUsersItem(users, book).orElse(null);

        return DetailBookDto.builder()
                .itemId(book != null ? book.getId() : null)
                .reviewId(review != null ? review.getId() : null)
                .title(naverBookDto.getTitle())
                .image(naverBookDto.getImage())
                .author(naverBookDto.getAuthor())
                .description(naverBookDto.getDescription())
                .isbn(naverBookDto.getIsbn())
                .releaseDate(naverBookDto.getPubdate())
                .build();
    }


    public MovieSearchResponseDto searchMovie(Long usersId, NaverMovieResponseDto naverMovieResponseDto) {
        Users users = usersRepository.findById(usersId).get();
        return MovieSearchResponseDto.builder()
                .start(naverMovieResponseDto.getStart())
                .display(naverMovieResponseDto.getDisplay())
                .total(naverMovieResponseDto.getTotal())
                .items(naverMovieResponseDto.getItems()
                        .stream()
                        .map(naverMovie -> toDetailMovieDto(users, naverMovie))
                        .collect(Collectors.toList()))
                .build();
    }

    public DetailMovieDto toDetailMovieDto(Users users, NaverMovieDto naverMovieDto) {
        Movie movie = itemRepository.findMovieByTitleDirector(naverMovieDto.getTitle(), naverMovieDto.getDirector()).orElse(null);
        Review review = reviewRepository.findByUsersItem(users, movie).orElse(null);

        return DetailMovieDto.builder()
                .itemId(movie != null ? movie.getId() : null)
                .reviewId(review != null ? review.getId() : null)
                .title(naverMovieDto.getTitle())
                .image(naverMovieDto.getImage())
                .director(naverMovieDto.getDirector())
                .actors(naverMovieDto.getActor())
                .releaseDate(naverMovieDto.getPubDate())
                .build();
    }

}
