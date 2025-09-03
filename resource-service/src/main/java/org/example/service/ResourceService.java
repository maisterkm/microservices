package org.example.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
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
        Resource resource = new Resource();
        resource.setFileData(mp3Data);
        resource = resourceRepository.save(resource);

        extractMetadata(mp3Data, resource.getId());

        return resource.getId();
    }

    public byte[] getResource(Long id) {
        Resource resource = resourceRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Resource not found"));
        return resource.getFileData();
    }

    public void deleteResourcesByIds(Long[] ids) {
        for (Long id : ids) {
            resourceRepository.deleteById(id);
        }
    }

    private void extractMetadata(byte[] mp3Data, Long resourceId) throws IOException, TikaException {
        Metadata metadata = new Metadata();
        tika.parse(new ByteArrayInputStream(mp3Data), metadata);
    }
}
