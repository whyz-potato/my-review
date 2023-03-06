package whyzpotato.myreview.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import whyzpotato.myreview.dto.users.JoinRequestDto;
import whyzpotato.myreview.service.UsersService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/v1/signup")
    public ResponseEntity joinUsers(@RequestBody @Valid JoinRequestDto request) {
        usersService.join(request.getEmail(), request.getPassword(), request.getName());
        return new ResponseEntity(HttpStatus.CREATED);
    }


}
