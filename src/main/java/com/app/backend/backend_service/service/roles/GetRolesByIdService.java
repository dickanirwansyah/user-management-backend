package com.app.backend.backend_service.service.roles;

import com.app.backend.backend_service.entities.Roles;
import com.app.backend.backend_service.exception.ResourceNotfoundException;
import com.app.backend.backend_service.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetRolesByIdService {

    private final RolesRepository rolesRepository;

    public Roles findRolesById(Long id){
        return rolesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotfoundException("roles id not found !", HttpStatus.NOT_FOUND.value()));
    }
}
