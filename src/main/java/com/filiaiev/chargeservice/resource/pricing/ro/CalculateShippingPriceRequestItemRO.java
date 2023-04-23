package com.filiaiev.chargeservice.resource.pricing.ro;

import jakarta.validation.constraints.Null;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CalculateShippingPriceRequestItemRO {

    private BigDecimal quantity;
    private Double actualWeight;
    @Null
    private Double finalShippingWeight;
    private Double height;
    private Double width;
    private Double length;
    private BigDecimal declaredCargoPrice;
}
