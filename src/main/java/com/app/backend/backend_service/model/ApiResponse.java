package com.app.backend.backend_service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    private int status;
    private String message;
    private Object data;
    private String error;
    private List<String> errors;

    public static ApiResponse success(Object data){
        return ApiResponse.builder()
                .message("OK")
                .status(HttpStatus.OK.value())
                .error(null)
                .errors(null)
                .data(data)
                .build();
    }

    public static ApiResponse failed(int status, String error){
        return ApiResponse.builder()
                .message("failed")
                .data(null)
                .status(status)
                .error(error)
                .build();
    }

    public static ApiResponse failed (int status, List<String> errors){
        return ApiResponse.builder()
                .message("failed")
                .data(null)
                .status(status)
                .errors(errors)
                .build();
    }
}
