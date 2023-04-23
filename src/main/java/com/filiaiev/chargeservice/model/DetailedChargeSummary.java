package com.filiaiev.chargeservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class DetailedChargeSummary {

    // call 'summary' in RO
    // add total by weigth/service/surcharge
    private List<DetailedChargeSummaryItem> items;

    public BigDecimal getTotal() {
        return items.stream()
                .map(DetailedChargeSummaryItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getServiceTotal() {
        return items.stream()
                .map(DetailedChargeSummaryItem::getServiceTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getWeightTotal() {
        return items.stream()
                .map(DetailedChargeSummaryItem::getWeightFinalCharge)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getSurchargeTotal() {
        return items.stream()
                .map(DetailedChargeSummaryItem::getSurchargeTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
