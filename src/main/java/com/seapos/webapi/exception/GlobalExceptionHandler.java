package com.seapos.webapi.exception;

import com.seapos.webapi.models.response.ApiResponseModel;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponseModel<Void>> handleValidationException(
            ValidationException ex,
            HttpServletRequest request) {

        logger.warn("VALIDATION_ERROR | uri={} | message={}",
                request.getRequestURI(), ex.getMessage());

        ApiResponseModel<Void> response = new ApiResponseModel<>();
        response.setStatus(false);
        response.setChangeRequest(false);
        response.setErrorMessage(ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(DalException.class)
    public ResponseEntity<ApiResponseModel<Void>> handleDalException(
            DalException ex,
            HttpServletRequest request) {

        logger.error("DAL_EXCEPTION | uri={} | message={}",
                request.getRequestURI(), ex.getMessage(), ex);

        ApiResponseModel<Void> response = new ApiResponseModel<>();
        response.setStatus(false);
        response.setChangeRequest(false);
        response.setErrorMessage("Database error occurred. Please try again.");

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseModel<Void>> handleAllExceptions(
            Exception ex,
            HttpServletRequest request) {

        logger.error("UNHANDLED_EXCEPTION | uri={} | message={}",
                request.getRequestURI(), ex.getMessage(), ex);

        ApiResponseModel<Void> response = new ApiResponseModel<>();
        response.setStatus(false);
        response.setChangeRequest(false);
        response.setErrorMessage("Something went wrong. Please try again later.");

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseModel<Void>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("Validation failed");

        ApiResponseModel<Void> response = new ApiResponseModel<>();
        response.setStatus(false);
        response.setChangeRequest(false);
        response.setErrorMessage(errorMessage);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
