package com.filiaiev.chargeservice.model.charge;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class ChargeSummary {

    private List<ChargeSummaryItem> itemsBreakdown;
    private Map<ChargeType, BigDecimal> totalBreakdownByType;

    public BigDecimal getTotal() {
        return totalBreakdownByType.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
