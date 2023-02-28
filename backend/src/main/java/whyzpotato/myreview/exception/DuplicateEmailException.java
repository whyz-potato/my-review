package whyzpotato.myreview.exception;

import lombok.Getter;
import whyzpotato.myreview.controller.ErrorCode;

@Getter
public class DuplicateEmailException extends RuntimeException {
    private final ErrorCode errorCode;

    public DuplicateEmailException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
