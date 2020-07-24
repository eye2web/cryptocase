package com.eye2web.crypto.dto;

import com.eye2web.crypto.validation.NullOrNotBlank;
import com.eye2web.crypto.validation.NullOrPositive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyUpdateRequest {

    @NullOrNotBlank(message = "Should not be blank")
    private String ticker;

    @NullOrNotBlank(message = "Should not be blank")
    private String name;

    @NullOrPositive(message = "Should be a positive number")
    private Long numberOfCoins;

    @NullOrPositive(message = "Should be a positive number")
    private Long marketCap;
}
