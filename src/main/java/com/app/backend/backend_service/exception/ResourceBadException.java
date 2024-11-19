package com.app.backend.backend_service.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceBadException extends RuntimeException{

    private int status;
    private List<String> messageError;

    public ResourceBadException(int status, String message, List<String> messageError){
        super(message);
        this.status = status;
        this.messageError = messageError;
    }
}
