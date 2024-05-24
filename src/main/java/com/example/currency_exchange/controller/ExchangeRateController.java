package com.example.currency_exchange.controller;

import com.example.currency_exchange.model.ExchangeRate;
import com.example.currency_exchange.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExchangeRateController {
    @Autowired
    private ExchangeRateService exchangeRateService;
    @GetMapping("/rates")
    public ResponseEntity<List<ExchangeRate>> getAllRates() {
        return ResponseEntity.ok(exchangeRateService.getAllRates());
    }
}
