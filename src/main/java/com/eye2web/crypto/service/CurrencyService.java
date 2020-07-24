package com.eye2web.crypto.service;

import com.eye2web.crypto.domain.Currency;
import com.eye2web.crypto.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public Page<Currency> getAllCryptoCurrencies(final Pageable pagination) {
        return currencyRepository.findAll(pagination);
    }

    public Optional<Currency> getCurrency(final UUID id) {
        return currencyRepository.findOneById(id);
    }

    public Currency createOrUpdateCurrency(final Currency currency) {
        return currencyRepository.save(currency);
    }
    
    public void deleteCurrency(final Currency currency) {
        currencyRepository.delete(currency);
    }

}
