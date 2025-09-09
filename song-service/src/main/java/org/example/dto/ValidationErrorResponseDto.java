package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ValidationErrorResponseDto {
    private final String errorMessage;
    private final int errorCode;
    private final Map<String, String> details;
}
