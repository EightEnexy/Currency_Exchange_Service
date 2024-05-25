package com.example.currency_exchange;

import com.example.currency_exchange.controller.ExchangeRateController;
import com.example.currency_exchange.dto.ConvertedAmountDto;
import com.example.currency_exchange.dto.CurrencyConversionDto;
import com.example.currency_exchange.exception.ExchangeRateNotFoundException;
import com.example.currency_exchange.exception.GlobalExceptionHandler;
import com.example.currency_exchange.service.ExchangeRateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExchangeRateController.class)
class ExchangeRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRateService exchangeRateService;

    @InjectMocks
    private ExchangeRateController exchangeRateController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new ExchangeRateController(exchangeRateService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testConvertCurrencyWithInvalidCurrencyCode() throws Exception {
        CurrencyConversionDto request = new CurrencyConversionDto("US", new BigDecimal("100.0"), "EUR");

        mockMvc.perform(post("/api/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":400,\"message\":\"Validation failed\",\"details\":{\"fromCurrency\":\"From currency must be a valid three-letter or three-digit currency code\"}}"));
    }

    @Test
    void testConvertCurrencyWithMissingFields() throws Exception {
        String request = "{}";

        mockMvc.perform(post("/api/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":400,\"message\":\"Validation failed\",\"details\":{\"fromCurrency\":\"From currency must not be blank\",\"amount\":\"the amount is required\",\"toCurrency\":\"To currency must not be blank\"}}"));
    }

    @Test
    void testConvertCurrencyWithInvalidJSON() throws Exception {
        String request = "invalid json";

        mockMvc.perform(post("/api/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":400,\"message\":\"Invalid JSON format\"}"));
    }

    @Test
    void testConvertCurrencyNotFound() throws Exception {
        when(exchangeRateService.convertCurrency("USD", new BigDecimal("100.0"), "XYZ"))
                .thenThrow(new ExchangeRateNotFoundException("Exchange rate not found for currency pair: USD to XYZ"));

        CurrencyConversionDto request = new CurrencyConversionDto("USD", new BigDecimal("100.0"), "XYZ");

        mockMvc.perform(post("/api/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":404,\"message\":\"Exchange rate not found for currency pair: USD to XYZ\"}"));
    }

    @Test
    void testConvertCurrencyWithZeroAmount() throws Exception {
        when(exchangeRateService.convertCurrency("USD", new BigDecimal("0.0"), "EUR"))
                .thenReturn(new ConvertedAmountDto(new BigDecimal("0.0")));

        CurrencyConversionDto request = new CurrencyConversionDto("USD", new BigDecimal("0.0"), "EUR");

        mockMvc.perform(post("/api/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"convertedAmount\":0.0}"));
    }

    @Test
    void testConvertCurrency() throws Exception {
        when(exchangeRateService.convertCurrency("USD", new BigDecimal("100.0"), "EUR"))
                .thenReturn(new ConvertedAmountDto(new BigDecimal("92.35")));

        CurrencyConversionDto request = new CurrencyConversionDto("USD", new BigDecimal("100.0"), "EUR");

        mockMvc.perform(post("/api/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"convertedAmount\":92.35}"));
    }
}

