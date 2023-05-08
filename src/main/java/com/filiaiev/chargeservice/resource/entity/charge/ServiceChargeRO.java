package com.filiaiev.chargeservice.resource.entity.charge;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceChargeRO {

    private BigDecimal handlingCharge;
    private BigDecimal customsClearance;
}
