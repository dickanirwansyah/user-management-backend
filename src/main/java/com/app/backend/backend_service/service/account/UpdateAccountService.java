package com.app.backend.backend_service.service.account;

import com.app.backend.backend_service.entities.Roles;
import com.app.backend.backend_service.exception.ResourceBadException;
import com.app.backend.backend_service.exception.ResourceNotfoundException;
import com.app.backend.backend_service.model.UpdateAccountRequest;
import com.app.backend.backend_service.model.UpdateAccountResponse;
import com.app.backend.backend_service.repository.AccountRepository;
import com.app.backend.backend_service.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateAccountService {

    private final AccountRepository accountRepository;
    private final RolesRepository rolesRepository;
    private final ValidationAccountService validationAccountService;

    public UpdateAccountResponse updateAccount(UpdateAccountRequest request){
        log.info("request process update account : {}",request);

        List<String> errors = validationAccountService.validationAccountUpdate(request);
        if (!errors.isEmpty()){
            throw new ResourceBadException(HttpStatus.BAD_REQUEST.value(), "request invalid !", errors);
        }
        accountRepository.findById(request.getId())
                .ifPresentOrElse(account -> {
                    account.setEmail(request.getEmail());
                    account.setRoles(checkRoles(request.getRolesId()));
                    account.setFullName(request.getFullName());
                    account.setUsername(request.getUsername());
                    account.setDob(request.getDob());
                    accountRepository.save(account);
                }, () -> {
                    throw new ResourceNotfoundException("Account id not found !", HttpStatus.NOT_FOUND.value());
                });

        return UpdateAccountResponse.builder()
                .id(request.getId())
                .value(true)
                .build();
    }

    private Roles checkRoles(Long rolesId){
        return rolesRepository.findById(rolesId)
                .orElseThrow(() -> new ResourceNotfoundException("Roles id not found !", HttpStatus.NOT_FOUND.value()));
    }
}
