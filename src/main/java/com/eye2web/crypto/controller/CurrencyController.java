package com.eye2web.crypto.controller;


import com.eye2web.crypto.domain.Currency;
import com.eye2web.crypto.dto.CurrencyCreateRequest;
import com.eye2web.crypto.dto.CurrencyResponse;
import com.eye2web.crypto.dto.CurrencyUpdateRequest;
import com.eye2web.crypto.service.CurrencyService;
import eye2web.modelmapper.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;
    private final ModelMapper modelMapper;

    @GetMapping
    public Page<CurrencyResponse> retrieveAllCurrencies(@PageableDefault(direction = Sort.Direction.ASC, sort = "ticker") final Pageable pagination) {
        final var pageableResults = currencyService.getAllCryptoCurrencies(pagination);

        return pageableResults.map(c -> modelMapper.map(c, CurrencyResponse.class));
    }

    @PostMapping
    public ResponseEntity<CurrencyResponse> createCurrency(@RequestBody @Valid final CurrencyCreateRequest currencyCreateRequest) {
        final var newCurrency = currencyService.createOrUpdateCurrency(modelMapper.map(currencyCreateRequest, Currency.class));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(newCurrency, CurrencyResponse.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyResponse> retrieveCurrency(@PathVariable("id") final UUID id) {
        final var currencyOpt = currencyService.getCurrency(id);

        return currencyOpt.map(currency ->
                ResponseEntity.ok(modelMapper.map(currency, CurrencyResponse.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyResponse> updateCurrency(@PathVariable("id") final UUID id, @RequestBody @Valid final CurrencyUpdateRequest currencyUpdateRequest) {
        final var currencyOpt = currencyService.getCurrency(id);

        return currencyOpt.map(currency -> {
            modelMapper.map(currencyUpdateRequest, currency);
            final var updatedCurrency = currencyService.createOrUpdateCurrency(currency);
            return ResponseEntity.ok(modelMapper.map(updatedCurrency, CurrencyResponse.class));
        }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCurrency(@PathVariable("id") final UUID id) {
        final var currencyOpt = currencyService.getCurrency(id);

        return currencyOpt.map(currency -> {
            currencyService.deleteCurrency(currency);
            return ResponseEntity.accepted().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
