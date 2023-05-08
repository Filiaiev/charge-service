package com.filiaiev.chargeservice.resource.entity.charge;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ChargeSummaryRO {

    private List<ChargeSummaryItemRO> itemsBreakdown;
    private List<ItemChargeRO> totalBreakdown;
    private BigDecimal total;
}
