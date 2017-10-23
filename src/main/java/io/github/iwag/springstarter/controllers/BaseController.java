package io.github.iwag.springstarter.controllers;

import io.github.iwag.springstarter.models.ErrorResponseModel;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

public class BaseController {

    protected final Logger logger = LogManager.getLogger(getClass());

    @ExceptionHandler({ OurApplicationException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            OurApplicationException ex, WebRequest request) {
        return new ResponseEntity<Object>(new ErrorResponseModel(ex.getStatus().toString(), ex.getMessage()), new HttpHeaders(), ex.getStatus());
    }
}
