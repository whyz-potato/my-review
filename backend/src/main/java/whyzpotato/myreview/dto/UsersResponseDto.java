package whyzpotato.myreview.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
public class UsersResponseDto {

    @NotEmpty
    private Long id;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String name;

    public UsersResponseDto(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
}
