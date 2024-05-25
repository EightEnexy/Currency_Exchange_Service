package com.example.currency_exchange.service;
import com.example.currency_exchange.dto.ConvertedAmountDto;
import com.example.currency_exchange.exception.ExchangeRateNotFoundException;
import com.example.currency_exchange.model.ExchangeRate;
import com.example.currency_exchange.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExchangeRateService {
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public List<ExchangeRate> getAllRates() {
        return exchangeRateRepository.findAll();
    }

    public ConvertedAmountDto convertCurrency(String fromCurrency, BigDecimal amount, String toCurrency) {
        ExchangeRate exchangeRate = exchangeRateRepository.findByBaseCurrencyAndTargetCurrency(fromCurrency, toCurrency)
                .orElseThrow(() -> new ExchangeRateNotFoundException("Exchange rate not found for currency pair: "+ fromCurrency  + " to " + toCurrency));
        return new ConvertedAmountDto(amount.multiply(exchangeRate.getRate()));
    }
}
