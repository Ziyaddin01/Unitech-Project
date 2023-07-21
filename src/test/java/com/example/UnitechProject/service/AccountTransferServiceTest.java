package com.example.UnitechProject.service;


import com.example.UnitechProject.dto.AccountTransferRequest;
import com.example.UnitechProject.entity.Account;
import com.example.UnitechProject.entity.Customer;
import com.example.UnitechProject.repository.AccountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class AccountTransferServiceTest {
    private AccountTransferService accountTransferService;

    private AccountRepository accountRepository;

    @Before
    public void setup()throws Exception {
        accountRepository = Mockito.mock(AccountRepository.class);
        accountTransferService = new AccountTransferService(accountRepository);
    }

    @Test
    public void maketransfer(){
        Customer customer = new Customer();
        customer.setId(2l);
        customer.setPin("muradz");
        customer.setPassword("12345");


        Account fromAccount = new Account();
        fromAccount.setAccountNumber("ABC123XYZ");
        fromAccount.setBalance(BigDecimal.valueOf(4000));
        fromAccount.setId(5L);
        fromAccount.setCustomer(customer);

        Account toAccount = Account.builder()
                .accountNumber("ABC123XYZz")
                .balance(BigDecimal.valueOf(4200))
                .id(4L)
                .customer(customer)
                .build();


        AccountTransferRequest accountTransferRequest = new AccountTransferRequest();
        accountTransferRequest.setFromAccountNumber("ABC123XYZ");
        accountTransferRequest.setToAccountNumber("ABC123XYZ2q");
        accountTransferRequest.setAmount(BigDecimal.valueOf(200));

        Mockito.when(accountRepository.findByAccountNumber(accountTransferRequest.getFromAccountNumber())).thenReturn(fromAccount);
        Mockito.when(accountRepository.findByAccountNumber(accountTransferRequest.getToAccountNumber())).thenReturn(toAccount);

        ResponseEntity<String> response = accountTransferService.makeTransfer(fromAccount.getAccountNumber(), toAccount.getAccountNumber(), BigDecimal.valueOf(200));

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Transfer successful.", response.getBody());

        assertEquals(BigDecimal.valueOf(3800), fromAccount.getBalance());
        assertEquals(BigDecimal.valueOf(4400), toAccount.getBalance());

        Mockito.verify(accountRepository, Mockito.times(1)).save(fromAccount);
        Mockito.verify(accountRepository, Mockito.times(1)).save(toAccount);


    }

}
