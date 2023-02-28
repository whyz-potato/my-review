package whyzpotato.myreview.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import whyzpotato.myreview.domain.Book;
import whyzpotato.myreview.domain.Item;
import whyzpotato.myreview.domain.Users;
import whyzpotato.myreview.repository.ItemRepository;
import whyzpotato.myreview.repository.UsersRepository;
import whyzpotato.myreview.service.ItemService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final UsersRepository usersRepository;

    @GetMapping("/v1/content/book/{id}")
    public ResponseEntity<ExploreResponseDto> exploreBook(@PathVariable("id") Long userId) {
        //TODO accesstoken확인


        Users users = usersRepository.findById(userId).get();
        List<Item> likeBooks = itemRepository.likeBooksByUser(users)
                .stream()
                .map(b -> (Item)b)
                .collect(Collectors.toList());
        List<Item> top10Books = itemRepository.top10Books()
                .stream()
                .map(b -> (Item)b)
                .collect(Collectors.toList());
        List<Item> newBooks = itemRepository.newBooks(10)
                .stream()
                .map(b -> (Item)b)
                .collect(Collectors.toList());
        return new ResponseEntity<>(ExploreResponseDto.builder()
                .myContent(likeBooks)
                .top10(top10Books)
                .newContent(newBooks)
                .build(), HttpStatus.OK);
    }

    @Getter
    static class SimpleItemDto {
        private Long itemId;
        private String title;
        private String image;

        public SimpleItemDto(Item item) {
            this.itemId = item.getId();
            this.title = item.getTitle();
            this.image = item.getImage();
        }
    }

    @Getter
    static class ExploreResponseDto {
        private ContentListDto myContent;
        private ContentListDto top10;
        private ContentListDto newContent;

        @Builder
        public ExploreResponseDto(List<Item> myContent, List<Item> top10, List<Item> newContent) {
            this.myContent = new ContentListDto(
                    myContent.size(),
                    myContent.stream()
                            .map(i -> new SimpleItemDto(i))
                            .collect(Collectors.toList())
            );
            this.top10 = new ContentListDto(
                    top10.size(),
                    top10.stream()
                            .map(i -> new SimpleItemDto(i))
                            .collect(Collectors.toList())
            );
            this.newContent = new ContentListDto(
                    newContent.size(),
                    newContent.stream()
                            .map(i -> new SimpleItemDto(i))
                            .collect(Collectors.toList())
            );
        }

        @Getter
        @AllArgsConstructor
        static class ContentListDto {
            private int count;
            private List<SimpleItemDto> items;
        }

    }
}
