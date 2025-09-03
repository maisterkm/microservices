package org.example.controller;

import java.util.List;

import org.example.dto.SongDto;
import org.example.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping
    public ResponseEntity<Long> createSong(@Valid @RequestBody SongDto songDto) {
        Long id = songService.createSong(songDto);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongDto> getSong(@PathVariable Long id) {
        SongDto song = songService.getSong(id);
        return ResponseEntity.ok(song);
    }

    @DeleteMapping
    public ResponseEntity<List<Long>> deleteSongs(@RequestParam List<Long> ids) {
        List<Long> deletedIds = songService.deleteSongs(ids);
        return ResponseEntity.ok(deletedIds);
    }
}
