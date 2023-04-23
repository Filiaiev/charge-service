package com.filiaiev.chargeservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ServiceCharge {

    private BigDecimal handlingCharge;
    private BigDecimal customsClearance;
}
