package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "songs")
public class Song {

    @Id
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Artist is required")
    @Size(min = 1, max = 100, message = "Artist name must be between 1 and 100 characters")
    private String artist;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Album is required")
    @Size(min = 1, max = 100, message = "Album name must be between 1 and 100 characters")
    private String album;

    @Column(nullable = false)
    @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "Duration must be in mm:ss format with leading zeros")
    private String duration;

    @Column(nullable = false)
    @Pattern(regexp = "^(19\\d{2}|20\\d{2})$", message = "Year must be between 1900 and 2099")
    private String year;

}
