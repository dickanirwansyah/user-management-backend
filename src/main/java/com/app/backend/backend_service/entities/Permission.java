package com.app.backend.backend_service.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permissions")
@EntityListeners(AuditingEntityListener.class)
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_api", nullable = false)
    private int isApi;

    @Column(name = "end_point", nullable = false)
    private String endPoint;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "permission_level", nullable = false)
    private int permissionLevel;

    @Column(name = "glyphicon")
    private String glyphicon;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
}
