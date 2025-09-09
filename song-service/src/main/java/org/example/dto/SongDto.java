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

    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9]|60):([0-5][0-9])$",
            message = "Duration must be in mm:ss format with minutes up to 60")
    private String duration;

    @Pattern(regexp = "^(19\\d{2}|20\\d{2})$", message = "Year must be between 1900 and 2099")
    private String year;

}