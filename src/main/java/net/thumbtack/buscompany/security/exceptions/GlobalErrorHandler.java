package net.thumbtack.buscompany.security.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@AllArgsConstructor
@Data
public class GlobalErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsList handleValidation(MethodArgumentNotValidException exc) {
        final ErrorsList error = new ErrorsList();
        exc.getBindingResult().getFieldErrors().forEach(fieldError ->
                error.errors.add(new MyError("BAD_REQUEST", fieldError.getField(), fieldError.getDefaultMessage())));
        exc.getBindingResult().getGlobalErrors().forEach(err ->
                error.errors.add(new MyError("BAD_REQUEST", "global: ", err.getDefaultMessage())));
        return error;
    }

    @ExceptionHandler(MissingRequestCookieException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MyError handle() {
        return new MyError(ErrorCode.MISSING_COOKIE);
    }

    @ExceptionHandler({OrderException.class, TripException.class, DeleteException.class, DatabaseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MyError handle(MyException exc) {
        return new MyError(exc.getErrorCode());
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MyError handle(ResponseStatusException exc) {
        return new MyError(exc.getStatus().name(), "login", exc.getReason());
    }

    public static class ErrorsList {
        private final List<MyError> errors = new ArrayList<>();

        public List<MyError> getErrors() {
            return errors;
        }

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class MyError {
        private String errorCode;
        private String field;
        private String message;

        public MyError(ErrorCode errorCode) {
            this.errorCode = errorCode.name();
            this.field = errorCode.getField();
            this.message = errorCode.getMessage();
        }
    }

}

