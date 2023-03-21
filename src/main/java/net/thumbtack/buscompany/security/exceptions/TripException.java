package net.thumbtack.buscompany.security.exceptions;

public class TripException extends MyException {

    public TripException(ErrorCode errorCode) {
        super(errorCode);
    }

}
