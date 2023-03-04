package whyzpotato.myreview.dto.users;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import whyzpotato.myreview.domain.Users;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class JoinRequestDto {
    @NotEmpty(message = "email은 필수 입력 사항입니다.")
    @Email
    private String email;

    @NotEmpty(message = "password은 필수 입력 사항입니다.")
    private String password;

    @NotEmpty(message = "name은 필수 입력 사항입니다.")
    private String name;

//    public Users toEntity(){
//        return Users.builder()
//                .email(this.email)
//                .pw(this.password)
//                .name(this.name)
//                .build();
//    }
}
