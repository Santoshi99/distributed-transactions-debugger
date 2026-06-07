package org.distributeddebugger.orderservicev1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>  handleValidationFailedException(MethodArgumentNotValidException ex){

        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() +":"+ fieldError.getDefaultMessage()).collect(Collectors.joining(", "));

        ErrorResponse errorResponse = new ErrorResponse(message, "VALIDATION_FAILED", HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex){
        String message = "Something went wrong";
        ErrorResponse errorResponse = new ErrorResponse(message, "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(InvalidOrderAmountException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAmount(InvalidOrderAmountException ex){
        String message = ex.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(message, "INVALID_AMOUNT", HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
