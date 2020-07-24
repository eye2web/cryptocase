package com.eye2web.crypto.domain;

import eye2web.modelmapper.annotations.MapValue;
import eye2web.modelmapper.model.FieldProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @MapValue(fieldName = "ticker", properties = FieldProperties.IGNORE_NULL_VALUES)
    private String ticker;

    @MapValue(fieldName = "name", properties = FieldProperties.IGNORE_NULL_VALUES)
    private String name;

    @MapValue(fieldName = "numberOfCoins", properties = FieldProperties.IGNORE_NULL_VALUES)
    private Long numberOfCoins;

    @MapValue(fieldName = "marketCap", properties = FieldProperties.IGNORE_NULL_VALUES)
    private Long marketCap;
}
