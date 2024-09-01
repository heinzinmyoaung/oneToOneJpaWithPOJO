package com.example.oneToOneJpaWithPOJO.controller;
import com.example.oneToOneJpaWithPOJO.response.Basic;
import com.example.oneToOneJpaWithPOJO.response.ResponseFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @Autowired
    ResponseFactory responseFactory;

    // for @Validated Annotation
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Basic> handleConstraintViolationException(ConstraintViolationException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getConstraintViolations().forEach(violation -> {
//            String propertyPath = violation.getPropertyPath().toString();
//            String errorMessage = violation.getMessage();
//            errors.put(propertyPath, errorMessage);
//        });
        String errorMessage = ex.getConstraintViolations().stream()
                .map(error -> error.getMessage()).collect(Collectors.joining(", "));

        log.error("Validation errors: {}", ex.getConstraintViolations());
        return responseFactory.buildError(HttpStatus.BAD_REQUEST,"FAIL",errorMessage);
    }

    // for @Valid Annotation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Basic> handleValidationExceptions(MethodArgumentNotValidException ex) {

//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });

        String errorMessage = ex.getAllErrors().stream()
                .map(error -> error.getDefaultMessage()).collect(Collectors.joining(", "));

        log.error(String.format("( Class '%s' ) Errors %s :  %s", ex.getObjectName(), ex.getErrorCount() ,errorMessage));
        return responseFactory.buildError(HttpStatus.BAD_REQUEST,"FAIL",errorMessage);
    }
}