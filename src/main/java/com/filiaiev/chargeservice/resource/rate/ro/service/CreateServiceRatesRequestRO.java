package com.filiaiev.chargeservice.resource.rate.ro.service;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class CreateServiceRatesRequestRO {

    @NotNull
    private BigDecimal handlingChargeRatePerKg;

    @NotNull
    private BigDecimal handlingChargeMin;

    @NotNull
    private BigDecimal customsClearanceMultiplier;

    @NotNull
    private Instant effectiveDate;
}
