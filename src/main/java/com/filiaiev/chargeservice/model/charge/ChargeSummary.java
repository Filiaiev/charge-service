package com.filiaiev.chargeservice.model.charge;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ChargeSummary {

    private List<ChargeSummaryItem> itemsBreakdown;
    private List<ItemCharge> totalBreakdown;

    public BigDecimal getTotal() {
        return totalBreakdown.stream()
                .map(ItemCharge::getCharge)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
