package com.example.currency_exchange.service;
import com.example.currency_exchange.model.ExchangeRate;
import com.example.currency_exchange.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeRateService {
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    public List<ExchangeRate> getAllRates() {
        return exchangeRateRepository.findAll();
    }
}
