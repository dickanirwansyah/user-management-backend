package com.app.backend.backend_service.service.account;

import com.app.backend.backend_service.repository.AccountRepository;
import com.app.backend.backend_service.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileAccountService {

    private final AccountRepository accountRepository;
    private final JwtService jwtService;


}
