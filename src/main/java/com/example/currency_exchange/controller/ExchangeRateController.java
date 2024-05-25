package com.example.currency_exchange.controller;

import com.example.currency_exchange.dto.ConvertedAmountDto;
import com.example.currency_exchange.dto.CurrencyConversionDto;
import com.example.currency_exchange.model.ExchangeRate;
import com.example.currency_exchange.service.ExchangeRateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class ExchangeRateController {
    private ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/rates")
    public ResponseEntity<List<ExchangeRate>> getAllRates() {
        return ResponseEntity.ok(exchangeRateService.getAllRates());
    }

    @PostMapping("/convert")
    public ResponseEntity<ConvertedAmountDto> convertCurrency(
            @Valid @RequestBody CurrencyConversionDto request) {
        return ResponseEntity.ok(exchangeRateService.convertCurrency(
                request.getFromCurrency(), request.getAmount(), request.getToCurrency()));
    }
}
