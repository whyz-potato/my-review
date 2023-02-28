package whyzpotato.myreview.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //400


    //401 UNAUTHORIZED
    INVALID_ACCESS_TOKEN(UNAUTHORIZED, "Access Token이 유효하지 않습니다"),
    MISMATCH_ACCESS_TOKEN(UNAUTHORIZED, "Access Token의 사용자 정보와 일치하지 않습니다."),


    //409 CONFLICT
    DUPLICATE_EMAIL(CONFLICT, "이미 존재하는 이메일입니다."),
    DUPLICATE_CONTENT(CONFLICT, "이미 존재하는 컨텐츠입니다."),
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
