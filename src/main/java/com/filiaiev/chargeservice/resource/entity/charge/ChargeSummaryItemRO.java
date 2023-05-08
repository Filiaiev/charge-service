package com.filiaiev.chargeservice.resource.entity.charge;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ChargeSummaryItemRO {

    private List<ItemChargeRO> itemBreakdown;
    private BigDecimal total;
}
