package com.filiaiev.chargeservice.resource.entity.charge;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemChargeRO {

    private ChargeTypeRO type;
    private BigDecimal charge;
}
