package com.filiaiev.chargeservice.service.pricing.strategy;

import com.filiaiev.chargeservice.model.charge.CreateChargeSummaryRequest;
import com.filiaiev.chargeservice.model.charge.ItemCharge;

import java.util.List;

public interface ChargeStrategy {

    List<ItemCharge> createCharge(CreateChargeSummaryRequest request);
}
