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
public class SettingRolesRequest {

    private Long rolesId;
    private String rolesName;
    private List<PermissionRequest> permissionRequestList;

}
