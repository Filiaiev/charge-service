package com.filiaiev.chargeservice.resource.pricing.ro;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetailedChargeSummaryItemRO {

    private CalculateShippingPriceRequestItemRO cargoItem;
    private BigDecimal serviceTotal;
    private BigDecimal weightTotal;
    private BigDecimal surchargeTotal;
    private BigDecimal total;
}
