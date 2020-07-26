package com.eye2web.crypto.controller;

import com.eye2web.crypto.configuration.DefaultConfiguration;
import com.eye2web.crypto.domain.Currency;
import com.eye2web.crypto.service.CurrencyService;
import org.hamcrest.collection.IsCollectionWithSize;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest({CurrencyController.class, DefaultConfiguration.class})
public class CurrencyControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CurrencyService currencyService;

    private final ArrayList<Currency> currencies = new ArrayList<>();

    @BeforeEach
    public void setup() {
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
    public void shouldFetchCurrencies() throws Exception {

        final var page = new PageImpl<>(currencies);

        given(currencyService.getAllCryptoCurrencies(any())).willReturn(page);

        mvc.perform(get("/api/currencies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", IsCollectionWithSize.hasSize(2)))
                .andExpect(jsonPath("$.content[0].ticker", is("BTC")))
                .andExpect(jsonPath("$.content[0].name", is("Bitcoin")))
                .andExpect(jsonPath("$.content[0].marketCap", is(1000)))
                .andExpect(jsonPath("$.content[0].numberOfCoins", is(1000)))
                .andExpect(jsonPath("$.content[1].ticker", is("ETH")))
                .andExpect(jsonPath("$.content[1].name", is("Etherium")))
                .andExpect(jsonPath("$.content[1].marketCap", is(2000)))
                .andExpect(jsonPath("$.content[1].numberOfCoins", is(2000)));
    }

    @Test
    public void shouldFetchCurrency() throws Exception {

        final var currency = currencies.get(0);

        given(currencyService.getCurrency(currency.getId())).willReturn(Optional.of(currency));

        mvc.perform(get("/api/currencies/" + currency.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticker", is("BTC")))
                .andExpect(jsonPath("$.name", is("Bitcoin")))
                .andExpect(jsonPath("$.marketCap", is(1000)))
                .andExpect(jsonPath("$.numberOfCoins", is(1000)));
    }

    @Test
    public void shouldNotFindCurrency() throws Exception {

        final var id = UUID.randomUUID();

        given(currencyService.getCurrency(id)).willReturn(Optional.empty());

        mvc.perform(get("/api/currencies/" + id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateCurrency() throws Exception {

        final var currency = currencies.get(0);
        given(currencyService.createOrUpdateCurrency(any(Currency.class))).willReturn(currency);

        mvc.perform(post("/api/currencies").content(
                "{" +
                        "\"ticker\": \"BTC\", " +
                        "\"name\": \"123\", " +
                        "\"numberOfCoins\": 16670000, " +
                        "\"marketCap\": 69020000000 " +
                        "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/api/currencies/" + currency.getId()));
    }

    @Test
    public void shouldNotCreateCurrency() throws Exception {

        given(currencyService.createOrUpdateCurrency(any(Currency.class))).willThrow(ConstraintViolationException.class);

        mvc.perform(post("/api/currencies").content(
                "{" +
                        "\"ticker\": \"BTC\", " +
                        "\"name\": \"123\", " +
                        "\"numberOfCoins\": 16670000, " +
                        "\"marketCap\": 69020000000 " +
                        "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldUpdateCurrency() throws Exception {

        final var currency = currencies.get(0);

        given(currencyService.getCurrency(currency.getId())).willReturn(Optional.of(currency));
        given(currencyService.createOrUpdateCurrency(any(Currency.class))).willReturn(currency);

        mvc.perform(put("/api/currencies/" + currency.getId()).content(
                "{" +
                        "\"ticker\": \"BTC\", " +
                        "\"name\": \"123\", " +
                        "\"numberOfCoins\": 16670000, " +
                        "\"marketCap\": 69020000000 " +
                        "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void shouldPartiallyUpdateCurrency() throws Exception {

        final var currency = currencies.get(0);

        given(currencyService.getCurrency(currency.getId())).willReturn(Optional.of(currency));
        given(currencyService.createOrUpdateCurrency(any(Currency.class))).willReturn(currency);

        mvc.perform(put("/api/currencies/" + currency.getId()).content(
                "{" +
                        "\"ticker\": \"BTC\", " +
                        "\"marketCap\": 69020000000 " +
                        "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void shouldNotFindUpdateCurrency() throws Exception {

        final var currency = currencies.get(0);
        given(currencyService.createOrUpdateCurrency(any(Currency.class))).willReturn(currency);

        mvc.perform(put("/api/currencies/" + currency.getId()).content(
                "{" +
                        "\"ticker\": \"BTC\", " +
                        "\"marketCap\": 69020000000 " +
                        "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldConflictUpdateCurrency() throws Exception {

        final var currency = currencies.get(0);
        given(currencyService.getCurrency(currency.getId())).willReturn(Optional.of(currency));
        given(currencyService.createOrUpdateCurrency(any(Currency.class))).willThrow(ConstraintViolationException.class);

        mvc.perform(put("/api/currencies/" + currency.getId()).content(
                "{" +
                        "\"ticker\": \"BTC\", " +
                        "\"marketCap\": 69020000000 " +
                        "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }


    @Test
    public void shouldDeleteCurrency() throws Exception {

        final var currency = currencies.get(0);

        given(currencyService.getCurrency(currency.getId())).willReturn(Optional.of(currency));
        mvc.perform(delete("/api/currencies/" + currency.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void shouldNotFindDeleteCurrency() throws Exception {

        final var currency = currencies.get(0);
        mvc.perform(delete("/api/currencies/" + currency.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
