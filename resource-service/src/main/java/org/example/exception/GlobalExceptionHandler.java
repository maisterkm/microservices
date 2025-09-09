package org.example.exception;

import org.apache.tika.exception.TikaException;
import org.example.dto.GeneralErrorResponseDto;
import org.example.dto.ResourceNotFoundResponseDto;
import org.example.dto.ValidationErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ValidationErrorResponseDto> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String errorMessage = "Failed to convert value of type '" + ex.getValue().getClass().getSimpleName() +
                "' to required type '" + ex.getRequiredType().getSimpleName() +
                "' for parameter '" + ex.getName() + "'";
        String invalidValue = ex.getValue().toString();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ValidationErrorResponseDto(errorMessage, 400, invalidValue));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GeneralErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GeneralErrorResponseDto(e.getMessage(), 400));
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ValidationErrorResponseDto> handleInvalidIdException(InvalidIdException ex) {
        String errorMessage = ex.getMessage();
        String invalidValue = String.valueOf(ex.getInvalidId());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ValidationErrorResponseDto(errorMessage, 400, invalidValue));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResourceNotFoundResponseDto> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResourceNotFoundResponseDto(e.getMessage(), HttpStatus.NOT_FOUND.value(), e.getResourceId()));
    }

    @ExceptionHandler({IOException.class, TikaException.class})
    public ResponseEntity<GeneralErrorResponseDto> handleFileProcessingException(Exception e) {
        String errorMessage = "Failed to process resource data: " + e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GeneralErrorResponseDto(errorMessage, 400));
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<GeneralErrorResponseDto> handleNumberFormatException(NumberFormatException e) {
        String errorMessage = "Invalid ID format. Please provide valid numbers.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GeneralErrorResponseDto(errorMessage, 400));
    }

}
