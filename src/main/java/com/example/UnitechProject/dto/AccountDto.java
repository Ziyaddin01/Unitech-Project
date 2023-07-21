package com.example.UnitechProject.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {
    private String accountNumber;
    private BigDecimal balance;
//    private Long customerId;

}
