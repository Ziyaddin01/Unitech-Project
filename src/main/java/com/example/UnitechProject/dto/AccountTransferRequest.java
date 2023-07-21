package com.example.UnitechProject.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountTransferRequest {
    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal amount;
}
