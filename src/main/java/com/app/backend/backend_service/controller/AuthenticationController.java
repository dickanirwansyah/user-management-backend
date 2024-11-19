package com.app.backend.backend_service.controller;

import com.app.backend.backend_service.model.AccountRequest;
import com.app.backend.backend_service.model.ApiResponse;
import com.app.backend.backend_service.model.AuthenticationRequest;
import com.app.backend.backend_service.service.account.SigninAccountService;
import com.app.backend.backend_service.service.account.SignupAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {

    private final SignupAccountService signupAccountService;
    private final SigninAccountService signinAccountService;

    @PostMapping(value = "/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody AccountRequest request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(signupAccountService.execute(request)));
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<ApiResponse> signin(@RequestBody AuthenticationRequest request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(signinAccountService.execute(request)));
    }
}
