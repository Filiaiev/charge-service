package com.filiaiev.chargeservice.model.rate;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class SurchargeRate {

    private Integer id;
    private Integer zoneRouteId;
    private SurchargeType type;
    private BigDecimal ratePerKg;
    private BigDecimal minCharge;
    private Instant effectiveDate;
}
