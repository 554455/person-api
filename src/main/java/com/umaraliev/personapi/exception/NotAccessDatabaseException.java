package com.umaraliev.personapi.exception;

import org.springframework.dao.DataAccessException;

public class NotAccessDatabaseException extends DataAccessException {
    public NotAccessDatabaseException(String msg) {
        super(msg);
    }

    public NotAccessDatabaseException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
