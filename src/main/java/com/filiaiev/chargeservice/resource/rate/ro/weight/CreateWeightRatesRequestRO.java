package com.filiaiev.chargeservice.resource.rate.ro.weight;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

import java.time.Instant;
import java.util.List;

@Data
public class CreateWeightRatesRequestRO {

    @NotNull
    private Integer zoneRouteId;

    @Valid
    private List<WeightRateRO> rates;

    @NonNull
    private Instant effectiveDate;
}
