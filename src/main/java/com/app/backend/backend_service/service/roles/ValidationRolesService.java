package com.app.backend.backend_service.service.roles;

import com.app.backend.backend_service.model.SettingRolesRequest;
import com.app.backend.backend_service.repository.PermissionRepository;
import com.app.backend.backend_service.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationRolesService {

    private final PermissionRepository permissionRepository;
    private final RolesRepository rolesRepository;

    public List<String> validationRoles(SettingRolesRequest request){
        List<String> errors = new ArrayList<>();

        if (Objects.isNull(request.getRolesName()) || request.getRolesName().isEmpty()){
            errors.add("roles name is required !");
        }

        if (Objects.isNull(request.getPermissionRequestList()) || request.getPermissionRequestList().isEmpty()){
            errors.add("permissions request list is required !");
        }

        if (!request.getPermissionRequestList().isEmpty()){
            request.getPermissionRequestList().forEach(permissionRequest -> {
                if (permissionRepository.findById(permissionRequest.getPermissionId()).isEmpty()){
                    errors.add(String.format("permission with id %s not found",permissionRequest.getPermissionId()));
                }
            });
        }

        if (!Objects.isNull(request.getRolesId())){
            if (rolesRepository.findById(request.getRolesId()).isEmpty()){
                errors.add(String.format("roles with id %s not found",request.getRolesId()));
            }
        }

        return errors;
    }
}
