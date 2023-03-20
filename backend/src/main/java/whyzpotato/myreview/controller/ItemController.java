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
import whyzpotato.myreview.dto.item.*;
import whyzpotato.myreview.service.ItemService;

import static whyzpotato.myreview.CommonUtils.displayToInt;
import static whyzpotato.myreview.CommonUtils.startToInt;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final WebClient searchWebClient;

    @GetMapping("/v1/content/book/{id}")
    public ResponseEntity<ExploreResponseDto> exploreBook(@PathVariable("id") Long userId) {
        return new ResponseEntity<>(itemService.exploreBook(userId), HttpStatus.OK);
    }

    @GetMapping("/v1/content/book/{userId}/{itemId}")
    public ResponseEntity<DetailBookDto> detailBook(@PathVariable("userId") Long userId,
                                                          @PathVariable("itemId") Long itemId) {
        return new ResponseEntity<>(itemService.findBookById(userId, itemId), HttpStatus.OK);
    }

    @GetMapping("/v1/content/book/search")
    public ResponseEntity<BookSearchResponseDto> searchBook(@RequestParam("id") Long usersId,
                                                            @RequestParam(value = "q", required = false) String query,
                                                            @RequestParam(value = "start", required = false) String start,
                                                            @RequestParam(value = "display", required = false) String display) {

        Mono<NaverBookResponseDto> naverResponse = searchWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/book.json")
                        .queryParam("query", query)
                        .queryParam("start", startToInt(start, 1))
                        .queryParam("display", displayToInt(display))
                        .build()).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(NaverBookResponseDto.class);
        NaverBookResponseDto naverSearchResultDto = naverResponse.block();

        BookSearchResponseDto bookSearchResponseDto = itemService.naverSearchBook(usersId, naverSearchResultDto);

        return new ResponseEntity<>(bookSearchResponseDto, HttpStatus.OK);

    }

    @GetMapping("/v1/content/movie/{id}")
    public ResponseEntity<ExploreResponseDto> exploreMovie(@PathVariable("id") Long userId) {
        return new ResponseEntity<>(itemService.exploreMovie(userId), HttpStatus.OK);
    }

    @GetMapping("/v1/content/movie/{userId}/{itemId}")
    public ResponseEntity<DetailMovieDto> detailMovie(@PathVariable("userId") Long userId,
                                                    @PathVariable("itemId") Long itemId) {
        return new ResponseEntity<>(itemService.findMovieById(userId, itemId), HttpStatus.OK);
    }

    @GetMapping("/v1/content/movie/search")
    public ResponseEntity<MovieSearchResponseDto> searchMovie(@RequestParam("id") Long usersId,
                                                              @RequestParam(value = "q", required = false) String query,
                                                              @RequestParam(value = "start", required = false) String start,
                                                              @RequestParam(value = "display", required = false) String display) {

        Mono<NaverMovieResponseDto> naverDto = searchWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie.json")
                        .queryParam("query", query)
                        .queryParam("start", startToInt(start, 1))
                        .queryParam("display", displayToInt(display))
                        .build()).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(NaverMovieResponseDto.class);
        NaverMovieResponseDto responseDto = naverDto.block();
        MovieSearchResponseDto movieSearchResponseDto = itemService.searchMovie(usersId, responseDto);

        return new ResponseEntity<>(movieSearchResponseDto, HttpStatus.OK);
    }


}
