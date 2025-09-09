package org.example.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class InvalidIdException extends RuntimeException {
    private final Long invalidId;

    public InvalidIdException(String message, Long invalidId) {
        super(message);
        this.invalidId = invalidId;
    }
}