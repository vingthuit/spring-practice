package net.thumbtack.buscompany.security.exceptions;

public class DatabaseException extends MyException {

    public DatabaseException(ErrorCode errorCode) {
        super(errorCode);
    }

}
