package com.app.backend.backend_service.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceNotfoundException extends RuntimeException{

    private Integer status;

    public ResourceNotfoundException(String message, Integer status){
        super(message);
        this.status = status;
    }
}
