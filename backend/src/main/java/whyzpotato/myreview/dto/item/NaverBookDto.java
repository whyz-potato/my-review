package whyzpotato.myreview.dto.item;

import lombok.Data;

@Data
public class NaverBookDto {
    private String title;
    private String image;
    private String author;
    private String isbn;
    private String pubdate;
    private String description;
}