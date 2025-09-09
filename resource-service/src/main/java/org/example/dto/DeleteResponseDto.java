package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class DeleteResponseDto {
    private List<Long> ids;

}