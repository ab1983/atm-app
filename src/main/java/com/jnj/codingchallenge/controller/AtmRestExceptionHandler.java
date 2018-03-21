package com.jnj.codingchallenge.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jnj.codingchallenge.exception.AtmException;

@ControllerAdvice
public class AtmRestExceptionHandler extends ResponseEntityExceptionHandler {
 
    @ExceptionHandler({ AtmException.class })
    public ResponseEntity<Object> handleAtmException(Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
          "Error: "+ ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
          "Unknown Error: "+ ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }    
     
}
