package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ValidationErrorResponseDto {
    private final String errorMessage;
    private final int errorCode;
    private final String invalidValue;
}