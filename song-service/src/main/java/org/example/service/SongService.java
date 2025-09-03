package org.example.service;

import java.util.List;

import org.example.dto.SongDto;
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
            throw new IllegalArgumentException("Metadata for this ID already exists");
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

    public List<Long> deleteSongs(List<Long> ids) {
        List<Song> songs = songRepository.findAllById(ids);
        songRepository.deleteAll(songs);
        return ids;
    }
}
