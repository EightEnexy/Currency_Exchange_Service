package com.example.currency_exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ConvertedAmountDto {
    private BigDecimal convertedAmount;
}
