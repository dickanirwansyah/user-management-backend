package com.app.backend.backend_service.service.account;

import com.app.backend.backend_service.entities.Account;
import com.app.backend.backend_service.exception.ResourceBadException;
import com.app.backend.backend_service.model.AccountRequest;
import com.app.backend.backend_service.model.AccountResponse;
import com.app.backend.backend_service.model.RolesResponse;
import com.app.backend.backend_service.repository.AccountRepository;
import com.app.backend.backend_service.service.roles.GetRolesByIdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignupAccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidationAccountService validationAccountService;
    private final GetRolesByIdService getRolesByIdService;

    public AccountResponse execute(AccountRequest accountRequest){

        List<String> errors = validationAccountService.validationAccount(accountRequest);

        if (!errors.isEmpty()){
            throw new ResourceBadException(HttpStatus.BAD_REQUEST.value(), "request invalid !", errors);
        }

        var roles = getRolesByIdService.findRolesById(accountRequest.getRolesId());
        Account account = Account.builder()
                .fullName(accountRequest.getFullName())
                .email(accountRequest.getEmail())
                .username(accountRequest.getUsername())
                .dob(accountRequest.getDob())
                .phoneNumber(accountRequest.getPhoneNumber())
                .password(passwordEncoder.encode(accountRequest.getPassword()))
                .roles(roles)
                .build();

        var accountResponse = accountRepository.save(account);

        return AccountResponse.builder()
                .id(accountResponse.getId())
                .fullName(accountRequest.getFullName())
                .phoneNumber(accountResponse.getPhoneNumber())
                .username(accountResponse.getUsername())
                .roles(RolesResponse.builder()
                        .id(roles.getId())
                        .name(roles.getName())
                        .build())
                .dob(accountResponse.getDob())
                .build();
    }

}
