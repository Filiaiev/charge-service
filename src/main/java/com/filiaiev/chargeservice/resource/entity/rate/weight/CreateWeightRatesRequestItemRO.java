package com.filiaiev.chargeservice.resource.entity.rate.weight;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class CreateWeightRatesRequestItemRO {

    private Integer zoneRouteId;
    private Double weightLimitKg;
    private BigDecimal baseRate;
    private BigDecimal ratePerKg;
    private Instant effectiveDate;
}
