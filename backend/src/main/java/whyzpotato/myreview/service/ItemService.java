package whyzpotato.myreview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whyzpotato.myreview.domain.Book;
import whyzpotato.myreview.domain.Item;
import whyzpotato.myreview.domain.Movie;
import whyzpotato.myreview.repository.ItemRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Long save(Item item) {
        if (item instanceof Book) {
            if (itemRepository.findBookByIsbn(((Book) item).getIsbn()).isPresent())
                throw new IllegalStateException("이미 존재하는 책입니다.");
        } else {
            if (itemRepository.findMovieByTitleDirector(item.getTitle(), ((Movie) item).getDirector()).isPresent())
                throw new IllegalStateException("이미 존재하는 영화입니다.");
        }
        itemRepository.save(item);
        return item.getId();
    }

}
