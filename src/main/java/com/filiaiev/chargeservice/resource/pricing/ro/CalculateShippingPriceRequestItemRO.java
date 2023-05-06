package com.filiaiev.chargeservice.resource.pricing.ro;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CalculateShippingPriceRequestItemRO {

    private BigDecimal quantity;
    private Double declaredWeight;
    private DimensionRO dimension;
    private BigDecimal declaredCargoPrice;
}
