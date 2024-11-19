package com.app.backend.backend_service.service.account;


import com.app.backend.backend_service.constant.ApplicationConstant;
import com.app.backend.backend_service.entities.Account;
import com.app.backend.backend_service.model.SearchAccountRequest;
import com.app.backend.backend_service.model.SearchAccountResponse;
import com.app.backend.backend_service.repository.AccountRepository;
import com.app.backend.backend_service.service.specification.AccountSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class ListAccountService {

    private final AccountRepository accountRepository;

    public Page<SearchAccountResponse> searchAccount(SearchAccountRequest searchAccountRequest, Pageable pageable){
        log.info("process search account request : {}", searchAccountRequest);
        Specification<Account> accountSpec = Specification
                .where(AccountSpecification.hasUsername(searchAccountRequest.getUsername()))
                .and(AccountSpecification.hasEmail(searchAccountRequest.getEmail()
                ))
                .and(AccountSpecification.hasFullname(searchAccountRequest.getFullName()))
                .and(AccountSpecification.hasPhoneNumber(searchAccountRequest.getPhoneNumber()))
                .and(AccountSpecification.hasDob(searchAccountRequest.getDob()))
                .and(AccountSpecification.hasDeleted(ApplicationConstant.ACTIVE));
        Page<Account> accountPage = accountRepository.findAll(accountSpec, pageable);
        Page<SearchAccountResponse> responsePage = accountPage.map(account -> SearchAccountResponse
                .builder()
                .id(account.getId())
                .username(account.getUsername())
                .fullName(account.getFullName())
                .dob(account.getDob())
                .email(account.getEmail())
                .rolesId(account.getRoles().getId())
                .rolesName(account.getRoles().getName())
                .phoneNumber(account.getPhoneNumber())
                .build());
        return responsePage;
    }
}
