package whyzpotato.myreview.dto.item;

import lombok.Data;

import java.util.List;

@Data
public class NaverMovieResponseDto {
    private int total;
    private int start;
    private int display;
    private List<NaverMovieDto> items;

}
