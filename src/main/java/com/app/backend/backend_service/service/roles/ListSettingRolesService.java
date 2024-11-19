package com.app.backend.backend_service.service.roles;

import com.app.backend.backend_service.entities.Permission;
import com.app.backend.backend_service.model.ListSettingRolesResponse;
import com.app.backend.backend_service.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListSettingRolesService {

    private final PermissionRepository permissionRepository;

    public List<ListSettingRolesResponse> listSettingRolesResponse(){
        log.info("request setting roles");
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream()
                .filter(permission -> permission.getPermissionLevel() == 1
                        && Objects.isNull(permission.getParentId()))
                .map(permission -> ListSettingRolesResponse
                .builder()
                        .menuIdLevel1(permission.getId())
                        .menuName(permission.getName())
                        .level(permission.getPermissionLevel())
                        .children(permissions.stream()
                                .filter(permissionChild -> permissionChild.getPermissionLevel() == 2 &&
                                        permissionChild.getParentId().equals(permission.getId()))
                                .map(permissionChild -> ListSettingRolesResponse
                                .SettingRolesMenuChildResponse.builder()
                                        .menuIdLevel2(permissionChild.getId())
                                        .parentId(permissionChild.getParentId())
                                        .menuName(permissionChild.getName())
                                        .level(permissionChild.getPermissionLevel())
                                .build())
                                .toList())
                .build())
                .toList();
    }
}
