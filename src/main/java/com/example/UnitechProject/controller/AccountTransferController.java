package com.example.UnitechProject.controller;

import com.example.UnitechProject.dto.AccountTransferRequest;
import com.example.UnitechProject.entity.Account;
import com.example.UnitechProject.entity.Customer;
import com.example.UnitechProject.repository.AccountRepository;
import com.example.UnitechProject.repository.CustomerRepository;
import com.example.UnitechProject.service.AccountTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transfer")
public class AccountTransferController {
    @Autowired
    private AccountTransferService accountTransferService;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<String> makeTransfer(@RequestBody AccountTransferRequest transferRequest) {
        String fromAccountNumber = transferRequest.getFromAccountNumber();
        String toAccountNumber = transferRequest.getToAccountNumber();
        BigDecimal amount = transferRequest.getAmount();

        ResponseEntity<String> response = accountTransferService.makeTransfer(fromAccountNumber, toAccountNumber, amount);
        return response;
    }
}
