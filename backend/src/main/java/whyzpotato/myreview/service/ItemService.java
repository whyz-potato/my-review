package whyzpotato.myreview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whyzpotato.myreview.domain.Book;
import whyzpotato.myreview.domain.Item;
import whyzpotato.myreview.domain.Movie;
import whyzpotato.myreview.repository.ItemRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Long saveItem(Item item) {
        itemRepository.save(item);
        if (item instanceof Book) {
            Book findBook = itemRepository.findBookByIsbn(((Book) item).getIsbn());
            if (findBook != null)
                return findBook.getId();
        } else {
            Movie findMovie = itemRepository.findMovieByTitleDirector(item.getTitle(), ((Movie) item).getDirector());
            if (findMovie != null)
                return findMovie.getId();
        }
        itemRepository.save(item);
        return item.getId();
    }

}
