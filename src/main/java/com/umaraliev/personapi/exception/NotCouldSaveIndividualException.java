package com.umaraliev.personapi.exception;

public class NotCouldSaveIndividualException extends RuntimeException{
    public NotCouldSaveIndividualException(String message) {
        super(message);
    }

    public NotCouldSaveIndividualException(String message, Throwable cause) {
        super(message, cause);
    }
}
