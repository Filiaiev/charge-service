package com.filiaiev.chargeservice.resource.pricing.ro;

import lombok.Data;

import java.util.List;

@Data
public class CalculateShippingPriceRequestRO {

    private Integer zoneRouteId;
    private List<CalculateShippingPriceRequestItemRO> items;
}
