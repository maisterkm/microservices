package org.example.exception;

import org.example.dto.ConflictResponseDto;
import org.example.dto.ErrorResponseDto;
import org.example.dto.ValidationErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SongNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleSongNotFoundException(SongNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDto(e.getMessage(), 404));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponseDto(e.getMessage(), 400));
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorResponseDto> handleNumberFormatException(NumberFormatException e) {
        String message = e.getMessage();
        String invalidValue = message.replace("For input string: \"", "").replace("\"", "");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto("Invalid 'id' parameter length: " + invalidValue.length(), 400));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> details = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage
                ));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ValidationErrorResponseDto("Validation failed", 400, details));
    }

    @ExceptionHandler(DuplicateSongException.class)
    public ResponseEntity<ConflictResponseDto> handleDuplicateSongException(DuplicateSongException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ConflictResponseDto(e.getMessage(), HttpStatus.CONFLICT.value(), e.getResourceId()));
    }

}
