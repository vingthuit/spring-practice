package net.thumbtack.buscompany.security.exceptions;

public class OrderException extends MyException {

    public OrderException(ErrorCode errorCode) {
        super(errorCode);
    }

}
