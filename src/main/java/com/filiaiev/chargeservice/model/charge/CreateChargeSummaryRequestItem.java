package com.filiaiev.chargeservice.model.charge;

import com.filiaiev.chargeservice.model.Dimension;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateChargeSummaryRequestItem {

    private Integer position;
    private BigDecimal quantity;
    // Call declaredWeight
    private Double declaredWeight;
    // Call shippingWeight
    private Double shippingWeight;
    private Dimension dimension;
    private BigDecimal declaredCargoPrice;
}
