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
public class ExchangeRateInitializationDto {

    @NotBlank(message = "Base currency must not be blank")
    @Pattern(regexp = "^[A-Z]{3}$|^\\d{3}$", message = "Base currency must be a valid three-letter or three-digit currency code")
    private String baseCurrency;

    @NotBlank(message = "Target currency must not be blank")
    @Pattern(regexp = "^[A-Z]{3}$|^\\d{3}$", message = "Target currency must be a valid three-letter or three-digit currency code")
    private String targetCurrency;

    @DecimalMin(value = "0.0", inclusive = false, message = "Rate must be greater than zero")
    @NotNull(message = "Rate must not be null")
    private BigDecimal rate;
}
