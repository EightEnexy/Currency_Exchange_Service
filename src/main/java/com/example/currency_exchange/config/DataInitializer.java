package com.example.currency_exchange.config;
import com.example.currency_exchange.dto.ExchangeRateInitializationDto;
import com.example.currency_exchange.model.ExchangeRate;
import com.example.currency_exchange.repository.ExchangeRateRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class DataInitializer {
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private final ExchangeRateRepository exchangeRateRepository;
    private final Validator validator;
    @Autowired
    public DataInitializer(ExchangeRateRepository exchangeRateRepository, Validator validator) {
        this.validator = validator;
        this.exchangeRateRepository = exchangeRateRepository;
    }
    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            logger.info("Starting database initialization.");

            List<ExchangeRateInitializationDto> exchangeRates = Arrays.asList(
                    new ExchangeRateInitializationDto("USD", "EUR", new BigDecimal("0.9235")),
                    new ExchangeRateInitializationDto("EUR", "USD", new BigDecimal("1.0826")),
                    new ExchangeRateInitializationDto("USD", "GBP", new BigDecimal("0.7863")),
                    new ExchangeRateInitializationDto("GBP", "USD", new BigDecimal("1.2715")),
                    new ExchangeRateInitializationDto("USD", "JPY", new BigDecimal("156.819")),
                    new ExchangeRateInitializationDto("JPY", "USD", new BigDecimal("0.0064")),
                    new ExchangeRateInitializationDto("EUR", "GBP", new BigDecimal("0.85142")),
                    new ExchangeRateInitializationDto("GBP", "EUR", new BigDecimal("1.1743")),
                    new ExchangeRateInitializationDto("EUR", "JPY", new BigDecimal("130.0")),
                    new ExchangeRateInitializationDto("JPY", "EUR", new BigDecimal("0.0077")),
                    new ExchangeRateInitializationDto("GBP", "JPY", new BigDecimal("169.795")),
                    new ExchangeRateInitializationDto("JPY", "GBP", new BigDecimal("0.00501")),
                    new ExchangeRateInitializationDto("AUD", "USD", new BigDecimal("0.66214")),
                    new ExchangeRateInitializationDto("USD", "AUD", new BigDecimal("1.50997")),
                    new ExchangeRateInitializationDto("CAD", "USD", new BigDecimal("0.73008")),
                    new ExchangeRateInitializationDto("USD", "CAD", new BigDecimal("1.36953")),
                    new ExchangeRateInitializationDto("CHF", "USD", new BigDecimal("1.09353")),
                    new ExchangeRateInitializationDto("USD", "CHF", new BigDecimal("0.9143")),
                    new ExchangeRateInitializationDto("NZD", "USD", new BigDecimal("0.61106")),
                    new ExchangeRateInitializationDto("USD", "NZD", new BigDecimal("1.63611")),
                    new ExchangeRateInitializationDto("CNY", "USD", new BigDecimal("0.13805")),
                    new ExchangeRateInitializationDto("USD", "CNY", new BigDecimal("7.23905"))
            );

            validateAndSaveExchangeRates(exchangeRates);

            logger.info("Database has been initialized with sample data.");
        };
    }

    private void validateAndSaveExchangeRates(List<ExchangeRateInitializationDto> exchangeRates) {
        for (ExchangeRateInitializationDto dto : exchangeRates) {
            Set<ConstraintViolation<ExchangeRateInitializationDto>> violations = validator.validate(dto);
            if (!violations.isEmpty()) {
                String errorMessage = violations.stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", "));
                logger.error("Validation failed for {}: {}", dto, errorMessage);
            } else {
                exchangeRateRepository.save(new ExchangeRate(dto.getBaseCurrency(), dto.getTargetCurrency(), dto.getRate()));
            }
        }
    }
}
