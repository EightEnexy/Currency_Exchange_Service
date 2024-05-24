package com.example.currency_exchange.config;
import com.example.currency_exchange.model.ExchangeRate;
import com.example.currency_exchange.repository.ExchangeRateRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initDatabase(ExchangeRateRepository repository) {
        return args -> {
            repository.save(new ExchangeRate("USD", "EUR", 0.9235));
            repository.save(new ExchangeRate("EUR", "USD", 1.0826));
            repository.save(new ExchangeRate("USD", "GBP", 0.7863));
            repository.save(new ExchangeRate("GBP", "USD", 1.2715));
            repository.save(new ExchangeRate("USD", "JPY", 156.819));
            repository.save(new ExchangeRate("JPY", "USD", 0.0064));
            repository.save(new ExchangeRate("EUR", "GBP", 0.85142));
            repository.save(new ExchangeRate("GBP", "EUR", 1.1743));
            repository.save(new ExchangeRate("EUR", "JPY", 130.0));
            repository.save(new ExchangeRate("JPY", "EUR", 0.0077));
            repository.save(new ExchangeRate("GBP", "JPY", 169.795));
            repository.save(new ExchangeRate("JPY", "GBP", 0.00501));
            repository.save(new ExchangeRate("AUD", "USD", 0.66214));
            repository.save(new ExchangeRate("USD", "AUD", 1.50997));
            repository.save(new ExchangeRate("CAD", "USD", 0.73008));
            repository.save(new ExchangeRate("USD", "CAD", 1.36953));
            repository.save(new ExchangeRate("CHF", "USD", 1.09353));
            repository.save(new ExchangeRate("USD", "CHF", 0.9143));
            repository.save(new ExchangeRate("NZD", "USD", 0.61106));
            repository.save(new ExchangeRate("USD", "NZD", 1.63611));
            repository.save(new ExchangeRate("CNY", "USD", 0.13805));
            repository.save(new ExchangeRate("USD", "CNY", 7.23905));
        };
    }
}
