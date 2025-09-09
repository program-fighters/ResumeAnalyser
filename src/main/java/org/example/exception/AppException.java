package org.example.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {
    private String errorCode;

    private String errorMessage;

    public AppException() {
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AppException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public AppException(BaseErrorEnum sqlException) {
        this(sqlException.getCode(), sqlException.getMessage());
    }

    public static AppException badException(String errorMsg) {
        return new AppException(String.valueOf(HttpStatus.BAD_REQUEST.value()), errorMsg);
    }
}
