package com.eye2web.crypto.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CurrencyCreateRequest {

    @NotNull
    @NotBlank(message = Constants.NOT_BLANK_MESSAGE)
    private String ticker;

    @NotNull
    @NotBlank(message = Constants.NOT_BLANK_MESSAGE)
    private String name;

    @NotNull
    @Min(value = 0, message = Constants.POSITIVE_NUMBER_MESSAGE)
    private Long numberOfCoins;

    @NotNull
    @Min(value = 0, message = Constants.POSITIVE_NUMBER_MESSAGE)
    private Long marketCap;

}
