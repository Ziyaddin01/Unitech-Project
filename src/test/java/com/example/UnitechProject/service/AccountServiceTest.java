package com.example.UnitechProject.service;

import com.example.UnitechProject.dto.AccountDto;
import com.example.UnitechProject.entity.Account;
import com.example.UnitechProject.entity.Customer;
import com.example.UnitechProject.repository.AccountRepository;
import com.example.UnitechProject.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Customer testCustomer;
    private String testUsername = "testUser";
    private AccountDto testAccountDto;

    @BeforeEach
    public void setup() {
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setPin(testUsername);

        testAccountDto = new AccountDto();
        testAccountDto.setAccountNumber("123456789");
        testAccountDto.setBalance(BigDecimal.valueOf(1000));
    }

    @Test
    public void testGetUserAccounts() {
        when(customerRepository.findByPin(testUsername)).thenReturn(testCustomer);

        when(accountRepository.findByCustomerId(testCustomer.getId())).thenReturn(Collections.singletonList(new Account()));

        List<Account> accounts = accountService.getUserAccounts(testUsername);

        assertEquals(1, accounts.size());
    }

    @Test
    public void testCreateAccount() {
        when(customerRepository.findByPin(testUsername)).thenReturn(testCustomer);
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> {
                    Account createdAccount = invocation.getArgument(0);
                    createdAccount.setId(2L); // Simüle edilmiş bir veritabanı ekleme işlemi
                    return createdAccount;
                });
        Account createdAccount = accountService.createAccount(testUsername, testAccountDto);

        assertEquals(testAccountDto.getAccountNumber(), createdAccount.getAccountNumber());
        assertEquals(testAccountDto.getBalance(), createdAccount.getBalance());
    }
    @Test
    public void testCreateAccount_UserNotFound() {
        when(customerRepository.findByPin(testUsername)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> accountService.createAccount(testUsername, testAccountDto));
    }

}
