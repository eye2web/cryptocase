package com.eye2web.crypto.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CurrencyResponse {

    private UUID id;

    private String ticker;
    private String name;
    private Long numberOfCoins;
    private Long marketCap;
}
