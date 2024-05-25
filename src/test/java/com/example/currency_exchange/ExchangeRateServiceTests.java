package com.example.currency_exchange;

import com.example.currency_exchange.dto.ConvertedAmountDto;
import com.example.currency_exchange.exception.ExchangeRateNotFoundException;
import com.example.currency_exchange.model.ExchangeRate;
import com.example.currency_exchange.repository.ExchangeRateRepository;
import com.example.currency_exchange.service.ExchangeRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ExchangeRateServiceTests {

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRates() {
        List<ExchangeRate> rates = Arrays.asList(
                new ExchangeRate(null, "USD", "EUR", new BigDecimal("0.9235")),
                new ExchangeRate(null, "EUR", "USD", new BigDecimal("1.0826"))
        );
        when(exchangeRateRepository.findAll()).thenReturn(rates);

        List<ExchangeRate> result = exchangeRateService.getAllRates();
        assertEquals(2, result.size());
    }

    @Test
    void testConvertCurrency() {
        when(exchangeRateRepository.findByBaseCurrencyAndTargetCurrency("USD", "EUR"))
                .thenReturn(Optional.of(new ExchangeRate(null, "USD", "EUR", new BigDecimal("0.9235"))));

        ConvertedAmountDto result = exchangeRateService.convertCurrency("USD", new BigDecimal("100.0"), "EUR");
        assertEquals(new BigDecimal("92.35000"), result.getConvertedAmount());
    }

    @Test
    void testConvertCurrencyNotFound() {
        when(exchangeRateRepository.findByBaseCurrencyAndTargetCurrency("USD", "EUR"))
                .thenReturn(Optional.empty());

        assertThrows(ExchangeRateNotFoundException.class, () -> {
            exchangeRateService.convertCurrency("USD", new BigDecimal("100.0"), "EUR");
        });
    }

    @Test
    void testConvertCurrencyWithZeroAmount() {
        when(exchangeRateRepository.findByBaseCurrencyAndTargetCurrency("USD", "EUR"))
                .thenReturn(Optional.of(new ExchangeRate(null, "USD", "EUR", new BigDecimal("0.9235"))));

        ConvertedAmountDto result = exchangeRateService.convertCurrency("USD", BigDecimal.ZERO, "EUR");
        assertEquals(BigDecimal.ZERO, result.getConvertedAmount().setScale(0));
    }

    @Test
    void testConvertCurrencyWithLargeAmount() {
        when(exchangeRateRepository.findByBaseCurrencyAndTargetCurrency("USD", "EUR"))
                .thenReturn(Optional.of(new ExchangeRate(null, "USD", "EUR", new BigDecimal("0.9235"))));

        ConvertedAmountDto result = exchangeRateService.convertCurrency("USD", new BigDecimal("1000000.0"), "EUR");
        assertEquals(new BigDecimal("923500.00000"), result.getConvertedAmount());
    }

    @Test
    void testConvertCurrencyRounding() {
        when(exchangeRateRepository.findByBaseCurrencyAndTargetCurrency("USD", "JPY"))
                .thenReturn(Optional.of(new ExchangeRate(null, "USD", "JPY", new BigDecimal("110.1234"))));

        ConvertedAmountDto result = exchangeRateService.convertCurrency("USD", new BigDecimal("100.0"), "JPY");
        assertEquals(new BigDecimal("11012.34000"), result.getConvertedAmount());
    }
}
