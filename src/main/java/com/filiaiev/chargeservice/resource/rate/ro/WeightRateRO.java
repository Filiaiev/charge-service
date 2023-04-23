package com.filiaiev.chargeservice.resource.rate.ro;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class WeightRateRO {

    @Null
    private Integer id;
    // add zone Id if fetching all rates (if there will be such endpoint)
    @NonNull
    private Double weightLimitKg;

    @NonNull
    private BigDecimal baseRate;

    @NonNull
    private BigDecimal ratePerKg;

    // maybe validate to not allow previous dates
    @NonNull
    private Instant effectiveDate;
}
