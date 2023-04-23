package com.filiaiev.chargeservice.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class ServiceChargeRate {

    private Integer id;
    private BigDecimal handlingChargeRatePerKg;
    private BigDecimal handlingChargeMin;
    private BigDecimal customsClearance;
    private Instant effectiveDate;
}
