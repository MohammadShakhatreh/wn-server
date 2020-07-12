package com.worldnavigator.web.controllers;

import com.worldnavigator.web.dto.AccountDto;
import com.worldnavigator.web.entities.Account;
import com.worldnavigator.web.exceptions.AccountNotFoundException;
import com.worldnavigator.web.repositories.AccountRepository;
import com.worldnavigator.web.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("accounts")
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @Autowired
    public AccountController(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account create(@Valid @RequestBody AccountDto account) {
        return accountService.create(account);
    }

    @GetMapping("@{username}")
    public Account retrieve(@PathVariable String username) {
        return accountRepository
                .findById(username)
                .orElseThrow(() ->
                        new AccountNotFoundException(String.format("There is no account for %s!", username)));
    }

    @DeleteMapping("@{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String username) {
        accountRepository.deleteById(username);
    }

    @GetMapping
    public List<Account> list() {
        return accountRepository.findAll();
    }
}
