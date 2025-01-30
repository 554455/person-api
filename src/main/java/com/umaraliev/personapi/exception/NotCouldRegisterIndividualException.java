package com.umaraliev.personapi.exception;

public class NotCouldRegisterIndividualException extends RuntimeException {

    public NotCouldRegisterIndividualException(String message) {
        super(message);
    }

    public NotCouldRegisterIndividualException(String message, Throwable cause) {
        super(message, cause);
    }
}
