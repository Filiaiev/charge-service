package com.filiaiev.chargeservice.resource.pricing.ro;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SurchargeTypeRO {

    FUEL("Fuel"),
    SECURITY("Security");

    public final String id;

    SurchargeTypeRO(String id) {
        this.id = id;
    }

    @JsonValue
    public String getValue() {
        return id;
    }
}
