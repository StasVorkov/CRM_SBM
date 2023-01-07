package com.ws.crm.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j
public class ErrorCrmController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                log.error("Bad request");
                return "errors/400";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                log.error("Access denied");
                return "errors/403";
            } else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                log.error("Resource not found");
                return "errors/404";
            } else if (statusCode == HttpStatus.CONFLICT.value()) {
                log.error("Resource conflict");
                return "errors/409";
            }
        }
        log.error("General error");
        return "errors/error";
    }
}
