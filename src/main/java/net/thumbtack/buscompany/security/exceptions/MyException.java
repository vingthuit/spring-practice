package net.thumbtack.buscompany.security.exceptions;

public class MyException extends RuntimeException {
    private final ErrorCode errorCode;

    public MyException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
