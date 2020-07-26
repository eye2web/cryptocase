package com.eye2web.crypto.service;

import com.eye2web.crypto.domain.Currency;
import com.eye2web.crypto.repository.CurrencyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CurrencyServiceTest {

    private CurrencyRepository currencyRepository;
    private CurrencyService currencyService;

    final ArrayList<Currency> currencies = new ArrayList<>();

    @BeforeEach
    public void setup() {
        currencyRepository = Mockito.mock(CurrencyRepository.class);
        currencyService = new CurrencyService(currencyRepository);

        currencies.add(Currency.builder()
                .id(UUID.randomUUID())
                .ticker("BTC")
                .name("Bitcoin")
                .marketCap(1000L)
                .numberOfCoins(1000L)
                .build());

        currencies.add(Currency.builder()
                .id(UUID.randomUUID())
                .ticker("ETH")
                .name("Etherium")
                .marketCap(2000L)
                .numberOfCoins(2000L)
                .build());
    }

    @Test
    public void shouldFetchDatabaseEntityList() {
        final var page = new PageImpl<>(currencies);

        given(currencyRepository.findAll(any(Pageable.class))).willReturn(page);
        final var result = currencyService.getAllCryptoCurrencies(Pageable.unpaged());

        Assertions.assertEquals(2, result.getTotalElements());
    }


    @Test
    public void shouldGetSingleCurrency() {
        final var currency = currencies.get(0);

        given(currencyRepository.findOneById(currency.getId())).willReturn(Optional.of(currency));
        final var result = currencyService.getCurrency(currency.getId());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(currency, result.get());
    }

    @Test
    public void shouldCreateOrUpdateCurrency() {
        final var currency = currencies.get(0);

        currencyService.createOrUpdateCurrency(currency);

        verify(currencyRepository, times(1)).save(currency);
    }
    
    @Test
    public void shouldDeleteCurrency() {
        final var currency = currencies.get(0);

        currencyService.deleteCurrency(currency);
        verify(currencyRepository, times(1)).delete(currency);

    }

}
