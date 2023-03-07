package whyzpotato.myreview.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import whyzpotato.myreview.dto.LoginResponseDto;
import whyzpotato.myreview.dto.UsersResponseDto;
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
        usersService.join(request.getEmail(), request.getName(), request.getPassword());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/v1/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDto login(@RequestBody @Valid LoginRequestDto request) {
        LoginResponseDto loginResponseDto = usersService.login(request.getEmail(), request.getPassword());
        return loginResponseDto;
    }

    @GetMapping("/v1/users/{id}")
    public ResponseEntity<UsersResponseDto> getUsers(@PathVariable("id") Long id) {
        UsersResponseDto usersResponseDto = usersService.findUsersInfo(id);
        return new ResponseEntity<>(usersResponseDto,HttpStatus.OK);
    }

    @PutMapping("/v1/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long postUsers(@PathVariable("id") Long id, @RequestBody @Valid UsersRequestDto request) {
        return usersService.updateUsersInfo(id, request.getName(), request.getPassword());
    }

    @PostMapping("/v1/users/resign/{id}")
    public ResponseEntity resign(@PathVariable("id") Long id, @RequestBody @Valid String email) {
        usersService.deleteUsers(id);
        return new ResponseEntity(HttpStatus.OK);
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

    @Getter
    @Setter
    static class LoginRequestDto {
        @NotEmpty(message = "email은 필수 입력 사항입니다.")
        @Email
        private String email;

        @NotEmpty(message = "password은 필수 입력 사항입니다.")
        private String password;
    }

    @Getter
    @Setter
    static class UsersRequestDto {
        private String name;
        private String password;
    }


}
