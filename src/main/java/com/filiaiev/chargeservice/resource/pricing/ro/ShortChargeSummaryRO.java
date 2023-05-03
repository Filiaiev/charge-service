package com.filiaiev.chargeservice.resource.pricing.ro;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ShortChargeSummaryRO {

    private List<BigDecimal> itemsPriceList;
    private BigDecimal total;
}
