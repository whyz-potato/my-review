package whyzpotato.myreview.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import whyzpotato.myreview.dto.item.BookSearchResponseDto;
import whyzpotato.myreview.dto.item.ExploreResponseDto;
import whyzpotato.myreview.dto.item.NaverBookResponseDto;
import whyzpotato.myreview.service.ItemService;

import static java.lang.Math.min;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final WebClient searchWebClient;

    static int startToInt(String start) {
        try {
            Integer number = Integer.valueOf(start);
            return min(1000, number);
        } catch (NumberFormatException ex) {
            return 1;
        }
    }

    static int displayToInt(String display) {
        try {
            Integer number = Integer.valueOf(display);
            return min(100, number);
        } catch (NumberFormatException ex) {
            return 10;
        }
    }

    @GetMapping("/v1/content/book/{id}")
    public ResponseEntity<ExploreResponseDto> exploreBook(@PathVariable("id") Long userId) {
        return new ResponseEntity<>(itemService.exploreBook(userId), HttpStatus.OK);
    }

    @GetMapping("/v1/content/movie/{id}")
    public ResponseEntity<ExploreResponseDto> exploreMovie(@PathVariable("id") Long userId) {
        return new ResponseEntity<>(itemService.exploreMovie(userId), HttpStatus.OK);
    }

    @GetMapping("/v1/content/book/search")
    public ResponseEntity<BookSearchResponseDto> searchBook(@RequestParam("id") Long usersId,
                                                            @RequestParam("q") String query,
                                                            @RequestParam("start") String start,
                                                            @RequestParam("display") String display) {

        Mono<NaverBookResponseDto> naverResponse = searchWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/book.json")
                        .queryParam("query", query)
                        .queryParam("start", startToInt(start))
                        .queryParam("display", displayToInt(display))
                        .build()).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(NaverBookResponseDto.class);
        NaverBookResponseDto responseDto = naverResponse.block();
        BookSearchResponseDto bookSearchResponseDto = itemService.searchBook(usersId, responseDto);

        return new ResponseEntity<>(bookSearchResponseDto, HttpStatus.OK);

    }

}
