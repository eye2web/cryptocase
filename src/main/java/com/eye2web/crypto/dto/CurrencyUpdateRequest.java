package com.eye2web.crypto.dto;

import com.eye2web.crypto.validation.NullOrNotBlank;
import com.eye2web.crypto.validation.NullOrPositive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CurrencyUpdateRequest {

    @NullOrNotBlank(message = Constants.NOT_BLANK_MESSAGE)
    private String ticker;

    @NullOrNotBlank(message = Constants.NOT_BLANK_MESSAGE)
    private String name;

    @NullOrPositive(message = Constants.POSITIVE_NUMBER_MESSAGE)
    private Long numberOfCoins;

    @NullOrPositive(message = Constants.POSITIVE_NUMBER_MESSAGE)
    private Long marketCap;
}
