package com.app.backend.backend_service.service.account;

import com.app.backend.backend_service.model.AccountRequest;
import com.app.backend.backend_service.model.UpdateAccountRequest;
import com.app.backend.backend_service.repository.AccountRepository;
import com.app.backend.backend_service.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationAccountService {

    private final AccountRepository accountRepository;
    private final RolesRepository rolesRepository;

    public List<String> validationAccount(AccountRequest accountRequest){
        List<String> errors = new ArrayList<>();
        if (Objects.isNull(accountRequest.getFullName()) || accountRequest.getFullName().isEmpty()){
            errors.add("fullname is required !");
        }

        if (Objects.isNull(accountRequest.getEmail()) || accountRequest.getEmail().isEmpty()){
            errors.add("email is required !");
        }

        if (Objects.isNull(accountRequest.getUsername()) || accountRequest.getUsername().isEmpty()){
            errors.add("username is required !");
        }

        if (Objects.isNull(accountRequest.getDob())){
            errors.add("dob is required !");
        }

        if (Objects.isNull(accountRequest.getPhoneNumber()) || accountRequest.getPhoneNumber().isEmpty()) {
            errors.add("phone number is required !");
        }

        if (Objects.isNull(accountRequest.getRolesId())){
            errors.add("roles id is required !");
        }

        if (!Objects.isNull(accountRequest.getEmail())){
            if (accountRepository.countByEmail(accountRequest.getEmail()) > 0){
                errors.add("email already in use !");
            }
        }

        if (!Objects.isNull(accountRequest.getUsername())){
            if (accountRepository.countByUsername(accountRequest.getUsername()) > 0){
                errors.add("username already in use !");
            }
        }

        if (!Objects.isNull(accountRequest.getPhoneNumber())){
            if (accountRepository.countByPhoneNumber(accountRequest.getPhoneNumber()) > 0){
                errors.add("phone number already in use !");
            }
        }

        if (!Objects.isNull(accountRequest.getRolesId())){
            if (!rolesRepository.findById(accountRequest.getRolesId()).isPresent()){
                errors.add("role id not found !");
            }
        }

        if (Objects.isNull(accountRequest.getPassword()) || accountRequest.getPassword().isEmpty()){
            errors.add("password is required !");
        }

        if (!Objects.isNull(accountRequest.getPassword())){
            if (!accountRequest.getPassword().equals(accountRequest.getConfirmPassword())){
                errors.add("password and confirm password do not match !");
            }
        }

        return errors;
    }

    public List<String> validationAccountUpdate(UpdateAccountRequest accountRequest){
        List<String> errors = new ArrayList<>();
        if (Objects.isNull(accountRequest.getFullName()) || accountRequest.getFullName().isEmpty()){
            errors.add("fullname is required !");
        }

        if (Objects.isNull(accountRequest.getEmail()) || accountRequest.getEmail().isEmpty()){
            errors.add("email is required !");
        }

        if (Objects.isNull(accountRequest.getUsername()) || accountRequest.getUsername().isEmpty()){
            errors.add("username is required !");
        }

        if (Objects.isNull(accountRequest.getDob())){
            errors.add("dob is required !");
        }

        if (Objects.isNull(accountRequest.getPhoneNumber()) || accountRequest.getPhoneNumber().isEmpty()) {
            errors.add("phone number is required !");
        }

        if (Objects.isNull(accountRequest.getRolesId())){
            errors.add("roles id is required !");
        }

        if (!Objects.isNull(accountRequest.getEmail())){
            if (accountRepository.countByEmail(accountRequest.getEmail()) > 0){
                errors.add("email already in use !");
            }
        }

        if (!Objects.isNull(accountRequest.getUsername())){
            if (accountRepository.countByUsername(accountRequest.getUsername()) > 0){
                errors.add("username already in use !");
            }
        }

        if (!Objects.isNull(accountRequest.getPhoneNumber())){
            if (accountRepository.countByPhoneNumber(accountRequest.getPhoneNumber()) > 0){
                errors.add("phone number already in use !");
            }
        }

        if (!Objects.isNull(accountRequest.getRolesId())){
            if (!rolesRepository.findById(accountRequest.getRolesId()).isPresent()){
                errors.add("role id not found !");
            }
        }

        return errors;
    }
}
