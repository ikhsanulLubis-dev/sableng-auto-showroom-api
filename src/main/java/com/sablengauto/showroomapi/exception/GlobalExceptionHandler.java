package com.sablengauto.showroomapi.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

import com.sablengauto.showroomapi.dto.ErrorResponse;
import com.sablengauto.showroomapi.service.AppLoggerService;

@RestControllerAdvice
public class GlobalExceptionHandler {

        private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
        private final AppLoggerService appLoggerService;

        public GlobalExceptionHandler(AppLoggerService appLoggerService) {
                this.appLoggerService = appLoggerService;
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleNotFound(
                        ResourceNotFoundException ex) {

                ErrorResponse error = new ErrorResponse(ex.getMessage());
                appLoggerService.log("REQUEST",
                                "UNKNOWN",
                                ex.getMessage());

                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidation(
                        MethodArgumentNotValidException ex) {

                Map<String, String> errors = new HashMap<>();

                ex.getBindingResult()
                                .getFieldErrors()
                                .forEach(error -> {

                                        errors.put(
                                                        error.getField(),
                                                        error.getDefaultMessage());

                                });

                ErrorResponse errorResponse = new ErrorResponse(
                                "Submit Failed",
                                errors);
                log.warn("Validation failed: {}", errors);
                appLoggerService.log("POST", "/api/cars", "Validation failed: " + errors);
                return new ResponseEntity<>(
                                errorResponse,
                                HttpStatus.BAD_REQUEST);

        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGeneralException(
                        Exception ex) {

                ErrorResponse error = new ErrorResponse("Internal server error");
                appLoggerService.log(
                                "SYSTEM",
                                "UNKNOWN",
                                ex.getMessage());

                return new ResponseEntity<>(
                                error,
                                HttpStatus.INTERNAL_SERVER_ERROR);

        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
                        HttpMessageNotReadableException ex) {

                log.error("Malformed JSON request", ex);
                appLoggerService.log("BAD_REQUEST",
                                "/api/cars",
                                "Malformed JSON request" + ex.getMessage());

                ErrorResponse error = new ErrorResponse("Malformed JSON request");
                return new ResponseEntity<>(
                                error,
                                HttpStatus.BAD_REQUEST);

        }

        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<ErrorResponse> handleBadRequest(
                        BadRequestException ex) {

                log.warn("Business validation failed: {}",
                                ex.getMessage());

                appLoggerService.log(
                                "BUSINESS_VALIDATION",
                                "/api/cars",
                                ex.getMessage());

                ErrorResponse error = new ErrorResponse(ex.getMessage());

                return new ResponseEntity<>(
                                error,
                                HttpStatus.BAD_REQUEST);

        }
}
