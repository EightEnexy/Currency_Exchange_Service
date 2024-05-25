package com.example.currency_exchange.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyConversionDto {

    @NotBlank(message = "From currency must not be blank")
    @Pattern(regexp = "^[A-Z]{3}$|^\\d{3}$", message = "From currency must be a valid three-letter or three-digit currency code")
    private String fromCurrency;

    @DecimalMin(value = "0.0", message = "Amount cannot be negative")
    @NotNull(message = "the amount is required")
    private BigDecimal amount;

    @NotBlank(message = "To currency must not be blank")
    @Pattern(regexp = "^[A-Z]{3}$|^\\d{3}$", message = "To currency must be a valid three-letter or three-digit currency code")
    private String toCurrency;
}