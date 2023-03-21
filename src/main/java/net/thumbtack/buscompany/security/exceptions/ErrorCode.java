package net.thumbtack.buscompany.security.exceptions;

public enum ErrorCode {
    INSERT_FAILED("id", "Can't insert model"),
    UPDATE_FAILED("id", "Can't update model"),
    DELETE_FAILED("id", "Can't delete model"),
    GET_FAILED("id", "Can't get model"),
    GET_ALL_FAILED("id", "Can't get models"),
    GET_USER_FAILED("login", "Can't get user"),
    COUNT_FAILED("id", "Can't count models"),
    CLEAR_DATABASE_FAILED("id", "Can't delete all"),
    NOT_ACCEPTABLE("id", "Access is denied"),
    NOT_ADMIN("userType", "Only clients have access"),
    NOT_CLIENT("userType", "Only clients have access"),
    WRONG_PASSWORD("password", "The password is wrong"),
    DELETE_ADMIN_FAILED("id", "There must be at least one administrator on the server"),
    WRONG_BUS("busName", "Wrong bus name"),
    NONEXISTENT_ORDER_ID("id", "There is no order by this id"),
    WRONG_PASSENGER("passengers", "Wrong passenger"),
    PLACE_TAKEN("place", "This place is already taken"),
    NO_FREE_PLACES("places", "There are not enough free places"),
    NONEXISTENT_TRIP_ID("id", "There is no trip by this id"),
    NOT_APPROVED("approved", "This trip is not approved"),
    NONEXISTENT_DATE("date", "There is no date by this trip"),
    MISSING_COOKIE("cookie", "You need to login");

    private final String field;
    private final String message;

    ErrorCode(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

}
