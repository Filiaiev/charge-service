package com.filiaiev.chargeservice.resource.entity.rate.surcharge;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum SurchargeTypeRO {

    FUEL("fuel"),
    SECURITY("security");

    public final String id;

    private static final Map<String, SurchargeTypeRO> IDENTITY_MAP = Arrays.stream(SurchargeTypeRO.values())
            .collect(Collectors.toMap(e -> e.id, Function.identity()));

    @JsonValue
    private String forValue() {
        return id;
    }

    @JsonCreator
    private SurchargeTypeRO fromValue(String id) {
        return IDENTITY_MAP.get(id);
    }
}
