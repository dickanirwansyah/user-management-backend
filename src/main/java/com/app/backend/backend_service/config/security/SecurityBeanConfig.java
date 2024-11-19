package com.app.backend.backend_service.config.security;

import com.app.backend.backend_service.constant.MessageConstant;
import com.app.backend.backend_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SecurityBeanConfig {

    private final AccountRepository accountRepository;

    @Bean
    public UserDetailsService userDetailsService(){
        return usernameOrEmail -> {
            if (isEmail(usernameOrEmail)){
                return accountRepository.findAccountByEmail(usernameOrEmail)
                        .orElseThrow(() -> new BadCredentialsException(MessageConstant.USERNAME_OR_EMAIL_NOT_FOUND));
            }
            return accountRepository.findAccountByUsername(usernameOrEmail)
                    .orElseThrow(() -> new BadCredentialsException(MessageConstant.USERNAME_OR_EMAIL_NOT_FOUND));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    private boolean isEmail(String input){
        return input.contains("@") && input.contains(".");
    }
}
