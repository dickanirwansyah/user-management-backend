package com.app.backend.backend_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtSubjectResponse {

    private Long id;
    private String username;
    private String fullName;
    private String email;
    private Long rolesID;
    private String rolesName;

}
