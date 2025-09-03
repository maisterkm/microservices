package org.example.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SongDto {

    @NotNull(message = "ID is required")
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @NotBlank(message = "Artist is required")
    @Size(min = 1, max = 100, message = "Artist name must be between 1 and 100 characters")
    private String artist;

    @NotBlank(message = "Album is required")
    @Size(min = 1, max = 100, message = "Album name must be between 1 and 100 characters")
    private String album;

    @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "Duration must be in mm:ss format with leading zeros")
    private String duration;

    @Pattern(regexp = "^(19\\d{2}|20\\d{2})$", message = "Year must be between 1900 and 2099")
    private String year;

}