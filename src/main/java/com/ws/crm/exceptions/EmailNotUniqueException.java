package com.ws.crm.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class EmailNotUniqueException extends RuntimeException{

    public EmailNotUniqueException(String message) {
        super(message);
    }

    public EmailNotUniqueException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailNotUniqueException(Throwable cause) {
        super(cause);
    }

    public EmailNotUniqueException() {
    }
}
