package com.app.backend.backend_service.service.roles;

import com.app.backend.backend_service.entities.Roles;
import com.app.backend.backend_service.model.SearchRolesRequest;
import com.app.backend.backend_service.model.SearchRolesResponse;
import com.app.backend.backend_service.repository.RolesRepository;
import com.app.backend.backend_service.service.specification.RolesSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchRolesService {

    private final RolesRepository rolesRepository;

    public Page<SearchRolesResponse> searchRoles(SearchRolesRequest request, Pageable pageable){
        log.info("process search roles request : {}",request);
        Specification<Roles> rolesSpec = Specification.where(RolesSpecification.hasRolesName(request.getRolesName()));
        Page<Roles> rolesPage = rolesRepository.findAll(rolesSpec, pageable);
        return rolesPage.map(roles -> SearchRolesResponse.builder()
                .rolesId(roles.getId())
                .rolesName(roles.getName())
                .build());
    }
}
