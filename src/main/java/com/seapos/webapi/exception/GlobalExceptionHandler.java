package com.seapos.webapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(
            Exception ex,
            HttpServletRequest request) {

        logger.error("UNHANDLED_EXCEPTION | uri={} | message={}",
                request.getRequestURI(), ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong. Please try again later.");
    }
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handleDataAccessException(
            DataAccessException ex, HttpServletRequest request) {

        logger.error("DATA_ACCESS_EXCEPTION | uri={} | message={}",
                request.getRequestURI(), ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Database error occurred. Please try again.");
    }

    @ExceptionHandler(DalException.class)
    public ResponseEntity<?> handleDalException(
            DalException ex, HttpServletRequest request) {

        logger.error("DAL_EXCEPTION | uri={} | message={}",
                request.getRequestURI(), ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Database error occurred. Please try again.");
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(
            ValidationException ex, HttpServletRequest request) {

        logger.warn("VALIDATION_ERROR | uri={} | message={}",
                request.getRequestURI(), ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    @ExceptionHandler(IOException.class)
    public void handleIOException(IOException ex) {
        logger.warn("CLIENT_ABORTED_CONNECTION | {}", ex.getMessage());
    }


}
