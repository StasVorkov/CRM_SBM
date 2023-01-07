package com.ws.crm.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ProjectNotAssignedException extends RuntimeException{

    public ProjectNotAssignedException(String message) {
        super(message);
    }

    public ProjectNotAssignedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectNotAssignedException(Throwable cause) {
        super(cause);
    }

    public ProjectNotAssignedException() {
    }
}
