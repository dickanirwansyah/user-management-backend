package com.app.backend.backend_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {

    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String password;
    private String confirmPassword;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    private Long rolesId;

}
