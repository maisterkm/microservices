package org.example.controller;

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

@RestController
@RequestMapping("/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping
    public ResponseEntity<Object> uploadResource(@RequestBody byte[] mp3Data) {
        try {
            Long resourceId = resourceService.saveResource(mp3Data);
            return ResponseEntity.ok().body(new ResourceResponse(resourceId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getResource(@PathVariable Long id) {
        try {
            byte[] resourceData = resourceService.getResource(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("audio/mpeg"));

            return new ResponseEntity<>(resourceData, headers, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteResources(@RequestParam String id) {
        try {
            String[] idStrings = id.split(",");
            Long[] ids = new Long[idStrings.length];
            for (int i = 0; i < idStrings.length; i++) {
                ids[i] = Long.parseLong(idStrings[i]);
            }
            resourceService.deleteResourcesByIds(ids);
            return ResponseEntity.ok().body(new DeleteResponse(ids));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Вспомогательные классы для ответов
    private static class ResourceResponse {
        private Long id;

        public ResourceResponse(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }
    }

    private static class DeleteResponse {
        private Long[] ids;

        public DeleteResponse(Long[] ids) {
            this.ids = ids;
        }

        public Long[] getIds() {
            return ids;
        }
    }
}
