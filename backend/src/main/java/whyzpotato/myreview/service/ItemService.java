package whyzpotato.myreview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whyzpotato.myreview.domain.Users;
import whyzpotato.myreview.dto.item.BookSaveRequestDto;
import whyzpotato.myreview.dto.item.ExploreResponseDto;
import whyzpotato.myreview.dto.item.SimpleItemResponseDto;
import whyzpotato.myreview.repository.ItemRepository;
import whyzpotato.myreview.repository.UsersRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UsersRepository usersRepository;

    public Long save(BookSaveRequestDto dto){
        return itemRepository.save(dto.toEntity()).getId();
    }

    //TODO Movie save



    public ExploreResponseDto exploreBook(Long userId){

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

    public ExploreResponseDto exploreMovie(Long userId){

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


}
