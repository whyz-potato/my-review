package whyzpotato.myreview.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import whyzpotato.myreview.exception.DuplicateEmailException;

import java.time.LocalDateTime;

import static whyzpotato.myreview.controller.ErrorCode.DUPLICATE_EMAIL;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {DuplicateEmailException.class})
    protected ResponseEntity handleDupEmailException() {
        return ErrorResponse.createErrorResponseEntity(DUPLICATE_EMAIL);
    }


    @Getter
    @Builder
    static class ErrorResponse {
        private final LocalDateTime timestamp = LocalDateTime.now();
        private final int status;
        private final String error;
        private final String message;

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
