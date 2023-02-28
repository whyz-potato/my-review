package whyzpotato.myreview.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import whyzpotato.myreview.service.UsersService;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/v1/signup")
    public ResponseEntity joinUsers(@RequestBody @Valid JoinRequestDto request) {
        usersService.join(request.getEmail(), request.getPassword(), request.getName());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Getter
    @Setter
    @ToString
    static class JoinRequestDto {
        @NotEmpty(message = "email은 필수 입력 사항입니다.")
        @Email
        private String email;

        @NotEmpty(message = "password은 필수 입력 사항입니다.")
        private String password;

        @NotEmpty(message = "name은 필수 입력 사항입니다.")
        private String name;
    }


}
