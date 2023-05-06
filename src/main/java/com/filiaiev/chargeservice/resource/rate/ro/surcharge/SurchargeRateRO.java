package com.filiaiev.chargeservice.resource.rate.ro.surcharge;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class SurchargeRateRO {

    @Null
    private Integer id;

    @NotNull
    private SurchargeTypeRO type;

    @NotNull
    private BigDecimal ratePerKg;

    private BigDecimal minCharge;

    @Null
    private Instant effectiveDate;
}
