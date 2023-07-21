package com.example.UnitechProject.service;

import com.example.UnitechProject.entity.Account;
import com.example.UnitechProject.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@Service
public class AccountTransferService {

    private AccountRepository accountRepository;

    public AccountTransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<String> makeTransfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber);
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber);

        if (fromAccount == null || toAccount == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid account number.");
        }

        if (amount.compareTo(fromAccount.getBalance()) > 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance.");
        }

        if (fromAccountNumber.equals(toAccountNumber)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot transfer to the same account.");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        return ResponseEntity.ok("Transfer successful.");
    }
}
