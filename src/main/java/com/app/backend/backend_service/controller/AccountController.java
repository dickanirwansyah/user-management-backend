package com.app.backend.backend_service.controller;

import com.app.backend.backend_service.model.ApiResponse;
import com.app.backend.backend_service.model.SearchAccountRequest;
import com.app.backend.backend_service.model.UpdateAccountRequest;
import com.app.backend.backend_service.service.account.DeleteAccountService;
import com.app.backend.backend_service.service.account.ListAccountService;
import com.app.backend.backend_service.service.account.UpdateAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/account")
public class AccountController {

    private final ListAccountService accountService;
    private final UpdateAccountService updateAccountService;
    private final DeleteAccountService deleteAccountService;

    @GetMapping(value = "/search")
    public ResponseEntity<ApiResponse> searchAccount(SearchAccountRequest request, Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(accountService.searchAccount(request, pageable)));
    }

    @PutMapping(value = "/update-account")
    public ResponseEntity<ApiResponse> updateAccount(@RequestBody UpdateAccountRequest request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(updateAccountService.updateAccount(request)));
    }

    @DeleteMapping(value = "/delete-account/{id}")
    public ResponseEntity<ApiResponse> deleteAccount(@PathVariable("id")Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(deleteAccountService.deleteAccount(id)));
    }
}
