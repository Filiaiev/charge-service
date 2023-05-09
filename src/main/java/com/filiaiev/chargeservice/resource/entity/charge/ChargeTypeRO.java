package com.filiaiev.chargeservice.resource.entity.charge;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ChargeTypeRO {

    WEIGHT("Weight charge"),
    SERVICE("Service charge"),
    SURCHARGE_FUEL("Surcharge - fuel"),
    SURCHARGE_SECURITY("Surcharge - security");

    @JsonValue
    public final String id;
}
