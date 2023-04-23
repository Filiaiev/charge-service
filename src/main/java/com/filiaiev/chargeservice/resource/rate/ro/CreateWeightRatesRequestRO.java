package com.filiaiev.chargeservice.resource.rate.ro;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateWeightRatesRequestRO {

    @NotNull
    private Integer zoneRouteId;

    @Valid
    private List<WeightRateRO> rates;
}
