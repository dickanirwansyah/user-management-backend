package com.app.backend.backend_service.service.roles;

import com.app.backend.backend_service.entities.Permission;
import com.app.backend.backend_service.exception.ResourceNotfoundException;
import com.app.backend.backend_service.model.ChildMenuResponse;
import com.app.backend.backend_service.model.JwtSubjectResponse;
import com.app.backend.backend_service.model.PermissionRolesResponse;
import com.app.backend.backend_service.model.SessionRequest;
import com.app.backend.backend_service.repository.PermissionRepository;
import com.app.backend.backend_service.repository.RolesRepository;
import com.app.backend.backend_service.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListPermissionByRolesService {

    private final RolesRepository rolesRepository;
    private final PermissionRepository permissionRepository;
    private final JwtService jwtService;

    public PermissionRolesResponse getPermissionsByRoles(SessionRequest sessionRequest){
        JwtSubjectResponse jwtSubjectResponse = jwtService.extractSubject(sessionRequest.getToken().substring(7));
        log.info("request permissions by roles id = [{}]",jwtSubjectResponse.getRolesID());

        if (rolesRepository.findById(jwtSubjectResponse.getRolesID()).isEmpty()){
            throw new ResourceNotfoundException("fetching roles failed because roles id not found !", HttpStatus.NOT_FOUND.value());
        }

        List<Permission> permissionRoles = permissionRepository.getPermissionByRoles(jwtSubjectResponse.getRolesID());
        if (!permissionRoles.isEmpty()){
            return PermissionRolesResponse.builder()
                    .menus(permissionRoles.stream().filter(permission -> permission.getPermissionLevel() == 1)
                            .map(permission -> PermissionRolesResponse
                            .Menu.builder()
                            .permissionId(permission.getId())
                            .name(permission.getName())
                            .level(permission.getPermissionLevel())
                            .endPoint(permission.getEndPoint())
                            .glyphicon(permission.getGlyphicon())
                                    .childMenuResponses(permissionRoles.stream()
                                            .filter(permissionChild ->
                                                    permissionChild.getPermissionLevel() == 2 &&
                                                    permissionChild.getParentId() != null &&
                                                    permissionChild.getParentId().equals(permission.getId()))
                                            .map(permissionChild -> ChildMenuResponse
                                                    .builder()
                                                    .permissionId(permissionChild.getId())
                                                    .name(permissionChild.getName())
                                                    .parentId(permissionChild.getParentId())
                                                    .level(permissionChild.getPermissionLevel())
                                                    .endPoint(permissionChild.getEndPoint())
                                                    .glyphicon(permissionChild.getGlyphicon())
                                                    .build()).toList())
                            .build())
                            .toList())
                    .build();
        }
        throw new ResourceNotfoundException("fetching roles failed because data permissions is empty !", HttpStatus.NOT_FOUND.value());
    }
}
