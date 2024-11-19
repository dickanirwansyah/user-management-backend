package com.app.backend.backend_service.service.account;

import com.app.backend.backend_service.entities.Account;
import com.app.backend.backend_service.exception.ResourceBadException;
import com.app.backend.backend_service.model.*;
import com.app.backend.backend_service.repository.AccountRepository;
import com.app.backend.backend_service.service.security.JwtService;
import com.app.backend.backend_service.service.systemlogs.WriteSystemLogsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SigninAccountService {

    private final AccountRepository accountRepository;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;
    private final WriteSystemLogsService writeSystemLogsService;

    public AuthenticationResponse execute(AuthenticationRequest request){
        List<String> errors = validation(request);
        if (!errors.isEmpty()){
            throw new ResourceBadException(HttpStatus.BAD_REQUEST.value(), "invalid request", errors);
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()));

        log.info("process save system logs..");
        writeSystemLogsService.write("login", String.format("Users : %s do action login time : %s", request.getUsernameOrEmail(), LocalDateTime.now().toString()));

        if (isEmail(request.getUsernameOrEmail())){
            //passing data to generate token
            log.info("process sign with email..");
            var account = findByEmail(request);

            var jwtSubject = JwtSubjectResponse.builder()
                    .id(account.getId())
                    .username(account.getUsername())
                    .rolesID(account.getRoles().getId())
                    .rolesName(account.getRoles().getName())
                    .fullName(account.getFullName())
                    .email(account.getEmail())
                    .build();
            var token = generateToken(jwtSubject);

            var accountResponse = AccountResponse.builder()
                    .id(account.getId())
                    .username(account.getUsername())
                    .dob(account.getDob())
                    .roles(RolesResponse.builder()
                            .id(account.getRoles().getId())
                            .name(account.getRoles().getName())
                            .build())
                    .phoneNumber(account.getPhoneNumber())
                    .fullName(account.getFullName())
                    .email(account.getEmail())
                    .build();

            var checkExpires = jwtService.extractExpiredToken(token);

            return AuthenticationResponse.builder()
                    .expiredToken(checkExpires)
                    .accountResponse(accountResponse)
                    .token(token)
                    .build();
        }

        //default find by username
        log.info("process sign with username..");
        var account = findByUsername(request);
        var jwtSubject = JwtSubjectResponse.builder()
                .id(account.getId())
                .username(account.getUsername())
                .rolesID(account.getRoles().getId())
                .rolesName(account.getRoles().getName())
                .fullName(account.getFullName())
                .email(account.getEmail())
                .build();
        var token = generateToken(jwtSubject);

        var accountResponse = AccountResponse.builder()
                .id(account.getId())
                .username(account.getUsername())
                .dob(account.getDob())
                .roles(RolesResponse.builder()
                        .id(account.getRoles().getId())
                        .name(account.getRoles().getName())
                        .build())
                .phoneNumber(account.getPhoneNumber())
                .fullName(account.getFullName())
                .email(account.getEmail())
                .build();

        var checkExpires = jwtService.extractExpiredToken(token);
        return AuthenticationResponse.builder()
                .accountResponse(accountResponse)
                .expiredToken(checkExpires)
                .token(token)
                .build();
    }

    private Account findByEmail(AuthenticationRequest request){
        return accountRepository.findAccountByEmail(request.getUsernameOrEmail())
                .orElseThrow(() -> new UsernameNotFoundException("username or email not found !"));
    }

    private Account findByUsername(AuthenticationRequest request){
        return accountRepository.findAccountByUsername(request.getUsernameOrEmail())
                .orElseThrow(() -> new UsernameNotFoundException("username or email not found !"));
    }

    private boolean isEmail(String input){
        return input.contains("@") && input.contains(".");
    }

    private String generateToken(JwtSubjectResponse subjectResponse){
        try {
            String jwtToken = jwtService.generateToken(objectMapper.writeValueAsString(subjectResponse));
            return jwtToken;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> validation(AuthenticationRequest request){
        List<String> errors = new ArrayList<>();
        if (Objects.isNull(request.getPassword()) || request.getPassword().isEmpty()) {
            errors.add("password cannot be empty !");
        }

        if (Objects.isNull(request.getUsernameOrEmail()) || request.getUsernameOrEmail().isEmpty()) {
            errors.add("username or email cannot be empty !");
        }
        return errors;
    }
}
