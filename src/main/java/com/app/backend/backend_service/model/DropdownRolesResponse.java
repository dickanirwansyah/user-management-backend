package com.app.backend.backend_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DropdownRolesResponse {

    private List<RolesResponse> content;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RolesResponse {
        private Long id;
        private String name;
    }
}
