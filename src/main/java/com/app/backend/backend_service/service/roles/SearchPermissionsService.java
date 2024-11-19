package com.app.backend.backend_service.service.roles;

import com.app.backend.backend_service.entities.Permission;
import com.app.backend.backend_service.model.SearchPermissionRequest;
import com.app.backend.backend_service.model.SearchPermissionResponse;
import com.app.backend.backend_service.repository.PermissionRepository;
import com.app.backend.backend_service.service.specification.PermissionSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchPermissionsService {

    private final PermissionRepository permissionRepository;

    public Page<SearchPermissionResponse> searchPermissions(SearchPermissionRequest searchPermissionRequest, Pageable pageable){
        log.info("process search permissions : {}",searchPermissionRequest);
        Specification<Permission> permissionSpecification = Specification
                .where(PermissionSpecification.hasName(searchPermissionRequest.getName()))
                .and(PermissionSpecification.hasPermissionDescription(searchPermissionRequest.getPermissionDescription()));
        Page<Permission> permissionPage = permissionRepository.findAll(permissionSpecification, pageable);
        Page<SearchPermissionResponse> permissionsResponse = permissionPage
                .map(permission -> SearchPermissionResponse.builder()
                        .id(permission.getId())
                        .icon(permission.getGlyphicon())
                        .name(permission.getName())
                        .permissionLevel(permission.getPermissionLevel())
                        .endPoint(permission.getEndPoint())
                        .parentId(Objects.isNull(permission.getParentId()) ? 0 : permission.getParentId())
                        .haveParent(Objects.isNull(permission.getParentId()) ? "Y" : "N")
                        .permissionDescription(permission.getDescription())
                        .build());
        return permissionsResponse;
    }
}
