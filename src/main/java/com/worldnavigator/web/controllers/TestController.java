package com.worldnavigator.web.controllers;

import com.worldnavigator.web.entities.Account;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(Account account) {
        return "The account is " + account.getName();
    }
}
