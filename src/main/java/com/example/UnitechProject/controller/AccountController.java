package com.example.UnitechProject.controller;

import com.example.UnitechProject.dto.AccountDto;
import com.example.UnitechProject.entity.Account;
import com.example.UnitechProject.entity.Customer;
import com.example.UnitechProject.repository.AccountRepository;
import com.example.UnitechProject.repository.CustomerRepository;
import com.example.UnitechProject.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getUserAccounts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Account> accounts =  accountService.getUserAccounts(username);
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@RequestBody AccountDto accountRequest, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Account createdAccount = accountService.createAccount(username, accountRequest);
        return ResponseEntity.ok(createdAccount);
    }
}
