package com.app.backend.backend_service.service.roles;

import com.app.backend.backend_service.model.DropdownRolesResponse;
import com.app.backend.backend_service.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DropdownRolesService {

    private final RolesRepository rolesRepository;

    public DropdownRolesResponse listDropdownRoles(){
        return DropdownRolesResponse.builder()
                .content(rolesRepository.findAll().stream().map(role -> DropdownRolesResponse
                        .RolesResponse.builder()
                        .id(role.getId()).name(role.getName())
                        .build()).toList())
                .build();
    }
}
