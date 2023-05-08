package com.filiaiev.chargeservice.model.charge;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ItemCharge {

    private Integer position;
    private ChargeType type;
    private BigDecimal charge;

    public ItemCharge(ChargeType type, BigDecimal charge) {
        this(0, type, charge);
    }
}
