package whyzpotato.myreview.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
public class LoginResponseDto {
    @NotEmpty
    private Long id;

    @NotEmpty
    private String accessToken;

    private LocalDateTime expiresAt;

    private String refreshToken;

    private LocalDateTime refreshTokenExpiresAt;


    public LoginResponseDto(Long id, String jwt) {
        this.id = id;
        this.accessToken = jwt;
        this.expiresAt = LocalDateTime.of(2023,11,29,10,29,1,1);
        this.refreshToken = "default";
        this.refreshTokenExpiresAt = LocalDateTime.of(2023,12,30,23,59,12,12);
    }
}
