package whyzpotato.myreview.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ExploreResponseDto {
    private ContentListDto myContent;
    private ContentListDto top10;
    private ContentListDto newContent;

    @Builder
    public ExploreResponseDto(List<SimpleItemResponseDto> myContent, List<SimpleItemResponseDto> top10, List<SimpleItemResponseDto> newContent) {
        this.myContent = new ContentListDto(
                myContent.size(),
                myContent
        );
        this.top10 = new ContentListDto(
                top10.size(),
                top10
        );
        this.newContent = new ContentListDto(
                newContent.size(),
                newContent
        );
    }

    @Getter
    @AllArgsConstructor
    static class ContentListDto {
        private int count;
        private List<SimpleItemResponseDto> items;
    }

}