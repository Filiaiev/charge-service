package com.filiaiev.chargeservice.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Stream;

@Data
@Builder
public class DetailedChargeSummaryItem {

    // call item in RO (or shippingItem)
    private CreateChargeSummaryRequestItem cargoItem;
    private ServiceCharge serviceFinalCharge;
    private BigDecimal weightFinalCharge;
    private Map<SurchargeType, BigDecimal> surchargeFinalCharge;

    public BigDecimal getServiceTotal() {
        return serviceFinalCharge.getHandlingCharge()
                .add(serviceFinalCharge.getCustomsClearance());
    }

    public BigDecimal getSurchargeTotal() {
        return surchargeFinalCharge.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotal() {
        return Stream.of(getServiceTotal(), getSurchargeTotal(), weightFinalCharge)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
