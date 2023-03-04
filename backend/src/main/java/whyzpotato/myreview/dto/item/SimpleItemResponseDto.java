package whyzpotato.myreview.dto.item;

import lombok.Getter;
import whyzpotato.myreview.domain.Item;

@Getter
public class SimpleItemResponseDto {
    private Long itemId;
    private String title;
    private String image;

    public SimpleItemResponseDto(Item item) {
        this.itemId = item.getId();
        this.title = item.getTitle();
        this.image = item.getImage();
    }
}