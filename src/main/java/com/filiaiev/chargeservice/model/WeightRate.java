package com.filiaiev.chargeservice.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class WeightRate {

    private Integer id;
    private Integer zoneRouteId;
    private Double weightLimitKg;
    private BigDecimal baseRate;
    private BigDecimal ratePerKg;
    private Instant effectiveDate;
}
