package com.tree.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String processValidationError(BadRequestException ex) {
        String result = ex.getErrorMessage();
        System.out.println(result);
        return ex.getErrorMessage();
    }

    @ExceptionHandler(UnauthorizedRequestException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public String processValidationError(UnauthorizedRequestException ex) {
        String result = ex.getErrorMessage();
        System.out.println("###########"+result);
        return ex.getErrorMessage();
    }
}