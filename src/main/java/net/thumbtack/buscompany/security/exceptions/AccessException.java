package net.thumbtack.buscompany.security.exceptions;

public class AccessException extends MyException {

    public AccessException(ErrorCode errorCode) {
        super(errorCode);
    }

}
