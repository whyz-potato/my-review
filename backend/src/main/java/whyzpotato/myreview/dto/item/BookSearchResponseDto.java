package whyzpotato.myreview.dto.item;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BookSearchResponseDto {
    private int total;
    private int start;
    private int display;
    private List<DetailBookDto> items;

    @Builder
    public BookSearchResponseDto(int total, int start, int display, List<DetailBookDto> items) {
        this.total = total;
        this.start = start;
        this.display = display;
        this.items = items;
    }

}
