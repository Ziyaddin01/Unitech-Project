package com.example.UnitechProject.service;

import com.example.UnitechProject.dto.AccountDto;
import com.example.UnitechProject.entity.Account;
import com.example.UnitechProject.entity.Customer;
import com.example.UnitechProject.repository.AccountRepository;
import com.example.UnitechProject.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Account> getUserAccounts(String username) {
        Customer customer = customerRepository.findByPin(username);
        return accountRepository.findByCustomerId(customer.getId());
    }


    public Account createAccount(String username, AccountDto accountRequest) {

        Customer user = customerRepository.findByPin(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        Account account = modelMapper.map(accountRequest, Account.class);


        return accountRepository.save(account);
    }
}
