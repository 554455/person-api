package com.umaraliev.personapi.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(NotCouldSaveIndividualException.class)
    public ResponseEntity<ErrorResponse> notCouldSaveUser(NotCouldSaveIndividualException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "USER_SAVE_ERROR",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundIndividualID.class)
    public ResponseEntity<ErrorResponse> noIndividualFoundID(NotFoundIndividualID ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "INDIVIDUAL_NOT_FOUND_ID",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotAccessDatabaseException.class)
    public ResponseEntity<ErrorResponse> notAccessDatabase(NotAccessDatabaseException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "DATABASE_NOT_ACCESS",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
