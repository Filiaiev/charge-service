package com.filiaiev.chargeservice.service.pricing;

import com.filiaiev.chargeservice.model.*;
import com.filiaiev.chargeservice.service.rate.SurchargeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SurchargeStrategy implements ChargeStrategy {

    private final SurchargeRateService rateService;

    @Override
    public BigDecimal createCharge(CreateChargeSummaryRequest request) {
        Map<SurchargeType, SurchargeRate> rates = rateService.getLatestSurchargeRates(request.getZoneRouteId());
        Map<SurchargeType, BigDecimal> surchargeCharges = new HashMap<>();

        for (CreateChargeSummaryRequestItem item : request.getItems()) {
            rates.forEach((type, rate) -> surchargeCharges.put(type, calculateItemTotal(item, rate)));
        }

        return surchargeCharges.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateItemTotal(CreateChargeSummaryRequestItem item, SurchargeRate rate) {
        BigDecimal itemSurcharge = rate.getRatePerKg().multiply(BigDecimal.valueOf(item.getShippingWeight()));

        return item.getQuantity().multiply(itemSurcharge);
    }

    @Override
    public ChargeType getChargeType() {
        return ChargeType.SURCHARGE;
    }
}
