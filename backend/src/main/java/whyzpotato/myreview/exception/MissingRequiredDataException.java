package whyzpotato.myreview.exception;

import lombok.Getter;
import whyzpotato.myreview.controller.ErrorCode;

@Getter
public class MissingRequiredDataException extends RuntimeException {
    private final ErrorCode errorCode;

    public MissingRequiredDataException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
