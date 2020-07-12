package com.worldnavigator.web.services;

import com.worldnavigator.web.dto.AccountDto;
import com.worldnavigator.web.entities.Account;
import com.worldnavigator.web.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public Account create(AccountDto data) {
        Account account = new Account(
                data.getName(),
                data.getUsername().trim().toLowerCase(),
                passwordEncoder.encode(data.getPassword())
        );

        return accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return accountRepository
                .findById(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("There is no account with %s as username!", username)));
    }
}
