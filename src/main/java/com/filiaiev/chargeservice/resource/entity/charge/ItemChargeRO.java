package com.filiaiev.chargeservice.resource.entity.charge;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ItemChargeRO {

    private ChargeTypeRO type;
    private BigDecimal charge;
}
