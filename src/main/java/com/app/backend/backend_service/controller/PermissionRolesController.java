package com.app.backend.backend_service.controller;

import com.app.backend.backend_service.model.ApiResponse;
import com.app.backend.backend_service.model.SearchPermissionRequest;
import com.app.backend.backend_service.service.roles.ListSettingRolesService;
import com.app.backend.backend_service.service.roles.SearchPermissionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/permission")
public class PermissionRolesController {

    private final SearchPermissionsService searchPermissionsService;
    private final ListSettingRolesService settingRolesService;

    @GetMapping(value = "/search")
    public ResponseEntity<ApiResponse> searchPermissions(SearchPermissionRequest searchPermissionRequest, Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(searchPermissionsService.searchPermissions(searchPermissionRequest, pageable)));
    }

    @GetMapping(value = "/fetching-menu-by-level")
    public ResponseEntity<ApiResponse> fetchingMenuByLevel(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(settingRolesService.listSettingRolesResponse()));
    }

}
