package com.app.backend.backend_service.service.specification;

import com.app.backend.backend_service.entities.Roles;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class RolesSpecification {

    public static Specification<Roles> hasRolesName(String rolesName){
        return (root, query, criteriaBuilder) -> {
            if (!Objects.isNull(rolesName)){
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%"+rolesName.toLowerCase()+"%");
            }
            return criteriaBuilder.conjunction();
        };
    }
}
