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
public class ListSettingRolesResponse {

    private Long menuIdLevel1;
    private String menuName;
    private Integer level;
    private List<SettingRolesMenuChildResponse> children;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SettingRolesMenuChildResponse {
        private Long menuIdLevel2;
        private Long parentId;
        private String menuName;
        private Integer level;
    }
}
