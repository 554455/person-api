package com.umaraliev.personapi.exception;


public class NotFoundIndividualID extends RuntimeException{

    public NotFoundIndividualID(String message) {
        super(message);
    }

    public NotFoundIndividualID(String message, Throwable cause) {
        super(message, cause);
    }
}
