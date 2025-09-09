package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConflictResponseDto {
    private final String errorMessage;
    private final int errorCode;
    private final Long resourceId;
}