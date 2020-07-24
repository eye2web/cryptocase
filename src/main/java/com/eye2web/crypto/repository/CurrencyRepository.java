package com.eye2web.crypto.repository;

import com.eye2web.crypto.domain.Currency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, UUID> {

    Page<Currency> findAll(final Pageable pagination);
    
    Optional<Currency> findOneById(final UUID id);
}
