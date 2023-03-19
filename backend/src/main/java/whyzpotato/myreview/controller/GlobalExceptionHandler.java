package whyzpotato.myreview.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import whyzpotato.myreview.exception.DuplicateResourceException;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static whyzpotato.myreview.controller.ErrorCode.BAD_REQUEST;
import static whyzpotato.myreview.controller.ErrorCode.DUPLICATE_EMAIL;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {DuplicateResourceException.class})
    protected ResponseEntity handleDupEmailException() {
        return ErrorResponse.createErrorResponseEntity(DUPLICATE_EMAIL);
    }

    @ExceptionHandler(value = {IllegalStateException.class})
    protected ResponseEntity handleBadRequestException() {
        return ErrorResponse.createErrorResponseEntity(BAD_REQUEST);
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    protected ResponseEntity handleNoPresentException() {
        return ErrorResponse.createErrorResponseEntity(HttpStatus.NOT_FOUND.value());
    }


    @Getter
    @Builder
    static class ErrorResponse {
        private final LocalDateTime timestamp = LocalDateTime.now();
        private final int status;
        private String error;
        private String message;

        public static ResponseEntity createErrorResponseEntity(int status) {
            return ResponseEntity
                    .status(status)
                    .build();
        }

        public static ResponseEntity createErrorResponseEntity(ErrorCode errorCode) {
            return ResponseEntity
                    .status(errorCode.getHttpStatus())
                    .body(ErrorResponse.builder()
                            .status(errorCode.getHttpStatus().value())
                            .error(errorCode.getHttpStatus().name())
                            .message(errorCode.getMessage())
                            .build()
                    );
        }


    }


}
