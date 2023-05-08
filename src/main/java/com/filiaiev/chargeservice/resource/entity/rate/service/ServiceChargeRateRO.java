package com.filiaiev.chargeservice.resource.entity.rate.service;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class ServiceChargeRateRO {

    private Integer id;
    private BigDecimal handlingChargeRatePerKg;
    private BigDecimal handlingChargeMin;
    private BigDecimal customsClearance;
    private Instant effectiveDate;
}
