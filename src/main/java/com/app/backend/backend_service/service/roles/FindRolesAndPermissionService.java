package com.app.backend.backend_service.service.roles;

import com.app.backend.backend_service.entities.Permission;
import com.app.backend.backend_service.entities.Roles;
import com.app.backend.backend_service.exception.ResourceBadException;
import com.app.backend.backend_service.exception.ResourceNotfoundException;
import com.app.backend.backend_service.model.FindRolesAndPermissionRequest;
import com.app.backend.backend_service.model.FindRolesAndPermissionResponse;
import com.app.backend.backend_service.repository.PermissionRepository;
import com.app.backend.backend_service.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindRolesAndPermissionService {

    private final PermissionRepository permissionRepository;
    private final RolesRepository rolesRepository;

    public FindRolesAndPermissionResponse findRolesAndPermission(FindRolesAndPermissionRequest request){
        log.info("request find roles and permission : {}",request.getRolesId());
        Optional<Roles> findRoles = rolesRepository.findById(request.getRolesId());
        if (findRoles.isEmpty()){
            throw new ResourceNotfoundException("Roles id not found !", HttpStatus.NOT_FOUND.value());
        }
        List<Permission> permissions = permissionRepository.getPermissionByRoles(findRoles.get().getId());
        if (permissions.isEmpty()){
            throw new ResourceBadException(HttpStatus.BAD_REQUEST.value(), "Error !", List.of("check the configuration or call administration !"));
        }
        return FindRolesAndPermissionResponse.builder()
                .rolesId(findRoles.get().getId())
                .rolesName(findRoles.get().getName())
                .permissionRequestList(permissions.stream().map(Permission::getId).toList())
                .build();
    }
}
