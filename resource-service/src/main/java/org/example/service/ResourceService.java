package org.example.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.example.exception.InvalidIdException;
import org.example.exception.ResourceNotFoundException;
import org.example.model.Resource;
import org.example.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    private Tika tika = new Tika();

    public Long saveResource(byte[] mp3Data) throws IOException, TikaException {
        Tika tika = new Tika();
        String detectedType = tika.detect(new ByteArrayInputStream(mp3Data));

        if (!"audio/mpeg".equals(detectedType)) {
            throw new IllegalArgumentException("Invalid file type. Only audio/mpeg is supported.");
        }

        Resource resource = new Resource();
        resource.setFileData(mp3Data);
        resource = resourceRepository.save(resource);

        extractMetadata(mp3Data, resource.getId());

        return resource.getId();
    }

    public byte[] getResource(Long id) {
        if (id < 1) {
            throw new InvalidIdException("Resource ID cannot be a negative number", id);
        }
        Resource resource = resourceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Resource not found", id));
        return resource.getFileData();
    }

    public List<Long> deleteResourcesByIds(Long[] ids) {
        List<Long> deletedIds = new ArrayList<>();
        for (Long id : ids) {
            if(resourceRepository.existsById(id)) {
                resourceRepository.deleteById(id);
                deletedIds.add(id);
            }
        }
        return deletedIds;
    }

    private void extractMetadata(byte[] mp3Data, Long resourceId) throws IOException, TikaException {
        Metadata metadata = new Metadata();
        tika.parse(new ByteArrayInputStream(mp3Data), metadata);
    }
}
