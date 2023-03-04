package whyzpotato.myreview.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import whyzpotato.myreview.dto.item.ExploreResponseDto;
import whyzpotato.myreview.repository.ItemRepository;
import whyzpotato.myreview.repository.UsersRepository;
import whyzpotato.myreview.service.ItemService;

import static java.lang.Math.min;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final UsersRepository usersRepository;

    @GetMapping("/v1/content/book/{id}")
    public ResponseEntity<ExploreResponseDto> exploreBook(@PathVariable("id") Long userId) {
        //TODO accesstoken확인

        return new ResponseEntity<>(itemService.exploreBook(userId), HttpStatus.OK);
    }

    @GetMapping("/v1/content/movie/{id}")
    public ResponseEntity<ExploreResponseDto> exploreMovie(@PathVariable("id") Long userId) {
        //TODO accesstoken확인

        return new ResponseEntity<>(itemService.exploreMovie(userId), HttpStatus.OK);
    }
    

    static int startToInt(String start){
        try{
            Integer number = Integer.valueOf(start);
            return min(1000, number);
        }
        catch (NumberFormatException ex){
            return 1;
        }
    }

    static int displayToInt(String display){
        try{
            Integer number = Integer.valueOf(display);
            return min(100, number);
        }
        catch (NumberFormatException ex){
            return 10;
        }
    }

}
