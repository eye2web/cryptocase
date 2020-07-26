package com.eye2web.crypto.controller;


import com.eye2web.crypto.domain.Currency;
import com.eye2web.crypto.dto.CurrencyCreateRequest;
import com.eye2web.crypto.dto.CurrencyResponse;
import com.eye2web.crypto.dto.CurrencyUpdateRequest;
import com.eye2web.crypto.service.CurrencyService;
import eye2web.modelmapper.ModelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.UUID;

@Slf4j
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
    public ResponseEntity<Void> createCurrency(@RequestBody @Valid final CurrencyCreateRequest currencyCreateRequest) {
        final var newCurrency = currencyService.createOrUpdateCurrency(modelMapper.map(currencyCreateRequest, Currency.class));
        log.info("Currency with id '{}' has been created", newCurrency.getId());

        return ResponseEntity.created(generateCurrencyResourceURI(newCurrency.getId())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyResponse> retrieveCurrency(@PathVariable("id") final UUID id) {
        final var currencyOpt = currencyService.getCurrency(id);

        return currencyOpt.map(currency ->
                ResponseEntity.ok(modelMapper.map(currency, CurrencyResponse.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCurrency(@PathVariable("id") final UUID id, @RequestBody @Valid final CurrencyUpdateRequest currencyUpdateRequest) {
        final var currencyOpt = currencyService.getCurrency(id);

        return currencyOpt.map(currency -> {
            modelMapper.map(currencyUpdateRequest, currency);
            final var updatedCurrency = currencyService.createOrUpdateCurrency(currency);
            log.info("Currency with id '{}' has been changed", updatedCurrency.getId());
            return ResponseEntity.accepted().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCurrency(@PathVariable("id") final UUID id) {
        final var currencyOpt = currencyService.getCurrency(id);

        return currencyOpt.map(currency -> {
            currencyService.deleteCurrency(currency);
            log.info("Currency with id '{}' has been deleted", id);
            return ResponseEntity.accepted().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private URI generateCurrencyResourceURI(final UUID id) {
        final var template = "/api/currencies/{id:.*}";
        final var uriComponents = UriComponentsBuilder.fromUriString(template)
                .build();
        return uriComponents.expand(Collections.singletonMap("id", id)).toUri();
    }

}
