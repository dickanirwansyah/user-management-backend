package com.app.backend.backend_service.repository;

import com.app.backend.backend_service.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long>, JpaSpecificationExecutor<Permission> {

    @Query(value = """
        select a.id, a.created_at, a.is_api, a.modified_at, a.parent_id, a.description, a.end_point, a.glyphicon, a.name, a.permission_level from permissions as a 
        join permission_roles as b on a.id = b.permission_id
        where b.roles_id = :rolesId
        ORDER by a.id asc
    """, nativeQuery = true)
    List<Permission> getPermissionByRoles(@Param("rolesId")Long rolesId);
}
