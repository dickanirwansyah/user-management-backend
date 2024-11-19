package com.app.backend.backend_service.repository;

import com.app.backend.backend_service.entities.SystemLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemLogsRepository extends JpaRepository<SystemLogs,Long> {
}
