package com.app.backend.backend_service.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceUsernameEmailNotFoundException extends RuntimeException{

    private Integer status;

    public ResourceUsernameEmailNotFoundException(String message, Integer status){
        super(message);
        this.status = status;
    }
}
