package whyzpotato.myreview.exception;

import lombok.Getter;
import whyzpotato.myreview.controller.ErrorCode;

@Getter
public class DuplicateResourceException extends RuntimeException {
    private final ErrorCode errorCode;

    public DuplicateResourceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
