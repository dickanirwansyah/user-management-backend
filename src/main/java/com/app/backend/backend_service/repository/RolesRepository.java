package com.app.backend.backend_service.repository;

import com.app.backend.backend_service.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Long>, JpaSpecificationExecutor<Roles> {
}
