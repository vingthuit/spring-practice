package net.thumbtack.buscompany.security.exceptions;

public class DeleteException extends MyException {

    public DeleteException(ErrorCode errorCode) {
        super(errorCode);
    }

}
