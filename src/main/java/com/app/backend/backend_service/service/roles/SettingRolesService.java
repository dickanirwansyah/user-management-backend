package com.app.backend.backend_service.service.roles;

import com.app.backend.backend_service.entities.PermissionRoles;
import com.app.backend.backend_service.entities.Roles;
import com.app.backend.backend_service.exception.ResourceBadException;
import com.app.backend.backend_service.model.PermissionRequest;
import com.app.backend.backend_service.model.RolesResponse;
import com.app.backend.backend_service.model.SettingRolesRequest;
import com.app.backend.backend_service.model.SettingRolesResponse;
import com.app.backend.backend_service.repository.PermissionRolesRepository;
import com.app.backend.backend_service.repository.RolesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SettingRolesService {

    private final PermissionRolesRepository permissionRolesRepository;
    private final RolesRepository rolesRepository;
    private final ValidationRolesService validationRolesService;

    @Transactional
    public SettingRolesResponse settingRoles(SettingRolesRequest request) {
        log.info("process create new roles.. = [{}]",request);

        List<String> validationRoles = validationRolesService.validationRoles(request);
        if (!validationRoles.isEmpty()){
            throw new ResourceBadException(HttpStatus.BAD_REQUEST.value(), "Error setting roles !", validationRoles);
        }

        var newRoles = Roles.builder()
                .name(request.getRolesName())
                .createdAt(LocalDateTime.now())
                .build();

        var dataRoles = rolesRepository.save(newRoles);

        List<PermissionRoles> permissionRolesList = new ArrayList<>();
        request.getPermissionRequestList().forEach(permission -> {
            PermissionRoles permissionRoles = new PermissionRoles();
            permissionRoles.setRolesId(dataRoles.getId());
            permissionRoles.setPermissionId(permission.getPermissionId());
            permissionRoles.setCreatedAt(LocalDateTime.now());
            var responsePermissions = permissionRolesRepository.save(permissionRoles);
            permissionRolesList.add(responsePermissions);
        });

        return SettingRolesResponse.builder()
                .roles(RolesResponse.builder()
                        .id(dataRoles.getId())
                        .name(dataRoles.getName())
                        .build())
                .permissions(permissionRolesList.stream().map(dataPermission -> PermissionRequest
                        .builder().permissionId(dataPermission.getPermissionId())
                                .build())
                        .toList())
                .build();
    }

}
