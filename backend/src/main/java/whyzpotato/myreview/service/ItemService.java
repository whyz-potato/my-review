package whyzpotato.myreview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whyzpotato.myreview.controller.ErrorCode;
import whyzpotato.myreview.domain.Book;
import whyzpotato.myreview.domain.Item;
import whyzpotato.myreview.domain.Movie;
import whyzpotato.myreview.domain.Users;
import whyzpotato.myreview.exception.DuplicateResourceException;
import whyzpotato.myreview.repository.ItemRepository;
import whyzpotato.myreview.repository.ReviewRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ReviewRepository reviewRepository;

    public Long save(Book book){
        if (itemRepository.findBookByIsbn(book.getIsbn()).isPresent())
            throw new DuplicateResourceException(ErrorCode.DUPLICATE_CONTENT);
        itemRepository.save(book);
        return book.getId();
    }

    public Long save(Movie movie){
        if (itemRepository.findMovieByTitleDirector(movie.getTitle(), movie.getDirector()).isPresent())
            throw new DuplicateResourceException(ErrorCode.DUPLICATE_CONTENT);
        itemRepository.save(movie);
        return movie.getId();
    }



}
