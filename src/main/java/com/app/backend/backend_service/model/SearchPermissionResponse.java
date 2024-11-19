package com.app.backend.backend_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchPermissionResponse {

    private Long id;
    private String name;
    private String permissionDescription;
    private String endPoint;
    private String icon;
    private String haveParent;
    private Long parentId;
    private int permissionLevel;

}
