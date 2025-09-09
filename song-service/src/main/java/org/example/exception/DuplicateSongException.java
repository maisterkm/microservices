package org.example.exception;

public class DuplicateSongException extends RuntimeException {
    private final Long resourceId;

    public DuplicateSongException(String message, Long resourceId) {
        super(message);
        this.resourceId = resourceId;
    }

    public Long getResourceId() {
        return resourceId;
    }
}
