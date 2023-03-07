package whyzpotato.myreview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class GoalResponseDto {
    @NotEmpty
    private int year;
    private int target;
    private Long cnt;
    private double attainmentRate;

    public GoalResponseDto(int year, int target, Long cnt, double attainmentRate) {
        this.year = year;
        this.target = target;
        this.cnt = cnt;
        this.attainmentRate = attainmentRate;
    }

    public GoalResponseDto(int year, Long cnt) {
        this.year = year;
        this.cnt = cnt;
    }
}
