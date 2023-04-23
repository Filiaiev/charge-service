package com.filiaiev.chargeservice.resource.pricing.ro;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class DetailedChargeSummaryRO {

    private List<DetailedChargeSummaryItemRO> chargesBreakdown;
    private BigDecimal weightTotal;
    private BigDecimal serviceTotal;
    private BigDecimal surchargeTotal;
    private BigDecimal total;
}
