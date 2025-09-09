package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GeneralErrorResponseDto {
    private final String errorMessage;
    private final int errorCode;
}
