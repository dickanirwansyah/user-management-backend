package com.app.backend.backend_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileAccountResponse {

    private Long id;
    private String fullName;
    private String email;
    private Date dob;
    private String phoneNumber;
    private String username;
    private Long rolesId;
    private String rolesName;
    private String pathProfileImage;
    private List<ProfileAccountAccessResponse> accessMenu;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProfileAccountAccessResponse {
        private Long permissionId;
        private String permissionName;
    }
}
