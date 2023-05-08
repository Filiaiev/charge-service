package com.filiaiev.chargeservice.resource.entity.charge;

import com.filiaiev.chargeservice.resource.entity.DimensionRO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateChargeSummaryRequestItemRO {

    private BigDecimal quantity;
    private Double declaredWeight;
    private DimensionRO dimension;
    private BigDecimal declaredCargoPrice;
}
