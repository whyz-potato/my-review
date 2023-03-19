package whyzpotato.myreview.dto.item;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class MovieSearchResponseDto {
    private int total;
    private int start;
    private int display;
    private List<DetailMovieDto> items;

    @Builder
    public MovieSearchResponseDto(int total, int start, int display, List<DetailMovieDto> items) {
        this.total = total;
        this.start = start;
        this.display = display;
        this.items = items;
    }
}
