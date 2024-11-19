package com.app.backend.backend_service.repository;

import com.app.backend.backend_service.entities.PermissionRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRolesRepository extends JpaRepository<PermissionRoles,Long> {

    @Query(value = "select * from permission_roles where roles_id=:rolesId", nativeQuery = true)
    List<PermissionRoles> getPermissionRolesBy(@Param("rolesId")Long rolesId);

}
