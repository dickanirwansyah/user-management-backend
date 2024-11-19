package com.app.backend.backend_service.controller;

import com.app.backend.backend_service.model.*;
import com.app.backend.backend_service.service.roles.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/roles")
public class SettingRolesController {

    private final SettingRolesService settingRolesService;
    private final UpdateSettingRoles updateSettingRoles;
    private final ListPermissionByRolesService listPermissionByRolesService;
    private final DropdownRolesService dropdownRolesService;
    private final FindRolesAndPermissionService findRolesAndPermissionService;
    private final SearchRolesService searchRolesService;

    @GetMapping(value = "/search-roles")
    public ResponseEntity<ApiResponse> searchRoles(SearchRolesRequest searchRolesRequest, Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(searchRolesService.searchRoles(searchRolesRequest, pageable)));
    }

    @GetMapping(value = "/dropdown-roles")
    public ResponseEntity<ApiResponse> dropdownRoles(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(dropdownRolesService.listDropdownRoles()));
    }

    @PostMapping(value = "/setting-roles")
    public ResponseEntity<ApiResponse> settingRoles(@RequestBody SettingRolesRequest request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(settingRolesService.settingRoles(request)));
    }

    @PutMapping(value = "/setting-roles-update")
    public ResponseEntity<ApiResponse> settingRolesUpdate(@RequestBody SettingRolesRequest request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(updateSettingRoles.updateSettingRoles(request)));
    }

    @GetMapping(value = "/get-roles")
    public ResponseEntity<ApiResponse> listMenuByRoles(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        SessionRequest sessionRequest = new SessionRequest();
        sessionRequest.setToken(authHeader);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(listPermissionByRolesService.getPermissionsByRoles(sessionRequest)));
    }

    @GetMapping(value = "/find-permissions")
    public ResponseEntity<ApiResponse> findRolesAndPermissions(@RequestParam("roles")Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(findRolesAndPermissionService.findRolesAndPermission(FindRolesAndPermissionRequest
                        .builder()
                        .rolesId(id)
                        .build())));
    }
}
