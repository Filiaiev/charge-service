package com.filiaiev.chargeservice.service.pricing;

import com.filiaiev.chargeservice.model.ChargeType;
import com.filiaiev.chargeservice.model.CreateChargeSummaryRequest;

import java.math.BigDecimal;

public interface ChargeStrategy {

    BigDecimal createCharge(CreateChargeSummaryRequest request);

    ChargeType getChargeType();
}
