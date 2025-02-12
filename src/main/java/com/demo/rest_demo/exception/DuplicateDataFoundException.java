package com.demo.rest_demo.exception;

import java.io.Serial;

public class DuplicateDataFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public DuplicateDataFoundException( String message) {
        super(message);
    }

}
