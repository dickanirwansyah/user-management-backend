package com.app.backend.backend_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChildMenuResponse {

    private Long permissionId;
    private Long parentId;
    private String endPoint;
    private Integer level;
    private String glyphicon;
    private String name;

}
