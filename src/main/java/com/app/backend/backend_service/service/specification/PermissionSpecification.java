package com.app.backend.backend_service.service.specification;

import com.app.backend.backend_service.entities.Permission;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class PermissionSpecification {

    public static Specification<Permission> hasName(String permissionName){
        return (root, query, criteriaBuilder) -> {
            if (!Objects.isNull(permissionName)){
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%"+permissionName.toLowerCase()+"%");
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Permission> hasPermissionDescription(String description){
        return (root, query, criteriaBuilder) -> {
            if (!Objects.isNull(description)){
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%"+description.toLowerCase()+"%");
            }
            return criteriaBuilder.conjunction();
        };
    }
}
