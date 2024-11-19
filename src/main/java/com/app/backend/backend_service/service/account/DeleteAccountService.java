package com.app.backend.backend_service.service.account;

import com.app.backend.backend_service.constant.ApplicationConstant;
import com.app.backend.backend_service.exception.ResourceNotfoundException;
import com.app.backend.backend_service.model.ValidationResponse;
import com.app.backend.backend_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteAccountService {

    private final AccountRepository accountRepository;

    public ValidationResponse deleteAccount(Long accountId){
        log.info("process delete account : {}",accountId);
        accountRepository.findById(accountId)
                .ifPresentOrElse(account -> {
                    account.setDeleted(ApplicationConstant.DEACTIVE);
                    account.setModifiedAt(LocalDateTime.now());
                    accountRepository.save(account);
                },() -> {
                    throw new ResourceNotfoundException("account id not found !", HttpStatus.NOT_FOUND.value());
                });
        return ValidationResponse.builder()
                .valid(true)
                .build();
    }
}
