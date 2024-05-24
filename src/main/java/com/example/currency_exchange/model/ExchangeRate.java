package com.example.currency_exchange.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {

    public ExchangeRate(String baseCurrency, String targetCurrency, Double rate) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @Column(nullable = false)
    @NotBlank(message = "Base currency must not be blank")
    private String baseCurrency;

    @Column(nullable = false)
    @NotBlank(message = "Target currency must not be blank")
    private String targetCurrency;
    @Column(nullable = false)
    @NotNull
    private Double rate;
}
