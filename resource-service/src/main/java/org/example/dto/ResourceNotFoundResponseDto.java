package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResourceNotFoundResponseDto {
    private final String errorMessage;
    private final int errorCode;
    private final Long resourceId;
}
