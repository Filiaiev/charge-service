package com.filiaiev.chargeservice.resource.entity.rate.surcharge;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class CreateSurchargeRatesRequestRO {

    @NotNull
    private Integer zoneRouteId;

    // TODO: Add validation to allow only 2(current N) types to be added
    @Valid
    private List<SurchargeRateRO> rates;

    @NotNull
    private Instant effectiveDate;
}
