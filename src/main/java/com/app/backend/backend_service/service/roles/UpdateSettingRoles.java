package com.app.backend.backend_service.service.roles;

import com.app.backend.backend_service.entities.PermissionRoles;
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
public class UpdateSettingRoles {

    private final PermissionRolesRepository permissionRolesRepository;
    private final RolesRepository rolesRepository;
    private final ValidationRolesService validationRolesService;

    @Transactional
    public SettingRolesResponse updateSettingRoles(SettingRolesRequest settingRolesRequest){

        List<String> validations = validationRolesService.validationRoles(settingRolesRequest);
        if(!validations.isEmpty()){
            throw new ResourceBadException(HttpStatus.BAD_REQUEST.value(),"Update setting roles failed !", validations);
        }

        List<PermissionRoles> permissionsRolesList = new ArrayList<>();
        rolesRepository.findById(settingRolesRequest.getRolesId())
                .ifPresent(roles -> {
                    roles.setName(settingRolesRequest.getRolesName());
                    roles.setModifiedAt(LocalDateTime.now());
                    //update roles
                    rolesRepository.save(roles);

                    //remove existing permission by roles id
                    permissionRolesRepository.deleteAll(permissionRolesRepository.getPermissionRolesBy(roles.getId()));

                    //update existing permission
                    settingRolesRequest.getPermissionRequestList().forEach(permissionRequest -> {
                        PermissionRoles permissionRoles = new PermissionRoles();
                        permissionRoles.setRolesId(roles.getId());
                        permissionRoles.setPermissionId(permissionRequest.getPermissionId());
                        var responsePermissionRoles = permissionRolesRepository.save(permissionRoles);
                        permissionsRolesList.add(responsePermissionRoles);
                    });
                });

            return SettingRolesResponse.builder()
                    .roles(RolesResponse.builder()
                            .id(settingRolesRequest.getRolesId())
                            .name(settingRolesRequest.getRolesName())
                            .build())
                    .permissions(permissionsRolesList.stream().map(permissionRoles -> PermissionRequest
                            .builder().permissionId(permissionRoles.getPermissionId()).build()).toList())
                    .build();
    }
}
