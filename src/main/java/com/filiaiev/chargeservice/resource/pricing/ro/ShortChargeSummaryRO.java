package com.filiaiev.chargeservice.resource.pricing.ro;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShortChargeSummaryRO {

    private BigDecimal weightTotal;
    private BigDecimal serviceTotal;
    private BigDecimal surchargeTotal;
    private BigDecimal total;
}
