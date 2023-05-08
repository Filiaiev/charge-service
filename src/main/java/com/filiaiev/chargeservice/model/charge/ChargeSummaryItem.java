package com.filiaiev.chargeservice.model.charge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ChargeSummaryItem {

    private List<ItemCharge> itemBreakdown;

    public BigDecimal getTotal() {
        return itemBreakdown.stream()
                .map(ItemCharge::getCharge)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
