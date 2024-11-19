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
public class PermissionRolesResponse {

    private List<Menu> menus;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Menu {
        private Long permissionId;
        private String endPoint;
        private Integer level;
        private String glyphicon;
        private String name;
        private List<ChildMenuResponse> childMenuResponses;
    }
}
