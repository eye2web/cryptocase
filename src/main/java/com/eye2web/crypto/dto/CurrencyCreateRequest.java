package com.eye2web.crypto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyCreateRequest {

    @NotNull
    @NotBlank(message = "Field ticker should not be blank!")
    private String ticker;

    @NotNull
    @NotBlank(message = "Field name should not be blank!")
    private String name;

    @NotNull
    @Min(value = 0, message = "numberOfCoins should be a positive number!")
    private Long numberOfCoins;

    @NotNull
    @Min(value = 0, message = "marketCap should be a positive number!")
    private Long marketCap;

}
