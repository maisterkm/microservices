package org.example.service;

import java.util.ArrayList;
import java.util.List;

import org.example.dto.SongDto;
import org.example.exception.DuplicateSongException;
import org.example.exception.SongNotFoundException;
import org.example.model.Song;
import org.example.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    public Long createSong(SongDto songDto) {
        if (songRepository.existsById(songDto.getId())) {
            throw new DuplicateSongException("Metadata for this ID already exists", songDto.getId());
        }

        Song song = new Song();
        song.setId(songDto.getId());
        song.setName(songDto.getName());
        song.setArtist(songDto.getArtist());
        song.setAlbum(songDto.getAlbum());
        song.setDuration(songDto.getDuration());
        song.setYear(songDto.getYear());

        song = songRepository.save(song);
        return song.getId();
    }

    public SongDto getSong(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new SongNotFoundException("Song metadata with ID=" + id + " not found"));
        SongDto songDto = new SongDto();
        songDto.setId(song.getId());
        songDto.setName(song.getName());
        songDto.setArtist(song.getArtist());
        songDto.setAlbum(song.getAlbum());
        songDto.setDuration(song.getDuration());
        songDto.setYear(song.getYear());

        return songDto;
    }

    public List<Long> deleteSongs(List<Long> ids, String idParam) {
        List<Long> allIds = new ArrayList<>();

        if (ids != null) {
            allIds.addAll(ids);
        }

        if (idParam != null) {
            validateIdParam(idParam);
            allIds.add(Long.valueOf(idParam));
        }

        if (allIds.isEmpty()) {
            throw new IllegalArgumentException("At least one id must be provided");
        }

        return performDelete(allIds);
    }

    private void validateIdParam(String idParam) {
        if (idParam.length() > 200) {
            throw new IllegalArgumentException(
                    "Invalid 'id' parameter length: " + idParam.length()
            );
        }
    }

    private List<Long> performDelete(List<Long> ids) {
        List<Long> deletedIds = new ArrayList<>();
        for (Long id : ids) {
            if (songRepository.existsById(id)) {
                songRepository.deleteById(id);
                deletedIds.add(id);
            }
        }
        return deletedIds;
    }

}
