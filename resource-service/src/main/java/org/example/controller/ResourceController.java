package org.example.controller;

import org.apache.tika.exception.TikaException;
import org.example.dto.DeleteResponseDto;
import org.example.dto.ResourceResponseDto;
import org.example.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping
    public ResponseEntity<Object> uploadResource(@RequestBody byte[] mp3Data) throws IOException, TikaException {
            Long resourceId = resourceService.saveResource(mp3Data);
            return ResponseEntity.ok().body(new ResourceResponseDto(resourceId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getResource(@PathVariable Long id) {
            byte[] resourceData = resourceService.getResource(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("audio/mpeg"));

            return new ResponseEntity<>(resourceData, headers, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteResources(@RequestParam String id) {
        int characterCount = id.length();

        final int MAX_CHARS_LIMIT = 150;
        if (characterCount > MAX_CHARS_LIMIT) {
            throw new IllegalArgumentException("Request parameter 'id' exceeds the maximum allowed length of " + MAX_CHARS_LIMIT + " characters, current is " + characterCount);
        }

        String[] idStrings = id.split(",");
        Long[] ids = new Long[idStrings.length];
        for (int i = 0; i < idStrings.length; i++) {
            ids[i] = Long.parseLong(idStrings[i]);
        }

        List<Long> deletedIds = resourceService.deleteResourcesByIds(ids);
        return ResponseEntity.ok().body(new DeleteResponseDto(deletedIds));
    }

}
