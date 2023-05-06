package com.filiaiev.chargeservice.service.pricing;

import com.filiaiev.chargeservice.model.ChargeType;
import com.filiaiev.chargeservice.model.CreateChargeSummaryRequest;
import com.filiaiev.chargeservice.model.CreateChargeSummaryRequestItem;
import com.filiaiev.chargeservice.model.ServiceChargeRate;
import com.filiaiev.chargeservice.service.rate.ServiceRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ServiceChargeStrategy implements ChargeStrategy {

    private final ServiceRateService rateService;

    @Override
    public BigDecimal createCharge(CreateChargeSummaryRequest request) {
        ServiceChargeRate rates = rateService.getLatestRates();
        BigDecimal total = BigDecimal.ZERO;

        for (CreateChargeSummaryRequestItem item : request.getItems()) {
            total = total.add(calculateItemTotal(rates, item));
        }

        return total;
    }

    private BigDecimal calculateItemTotal(ServiceChargeRate rates, CreateChargeSummaryRequestItem item) {
        return calculateHandlingCharge(rates, item)
                .add(calculateCustomsClearance(rates, item));
    }

    private BigDecimal calculateCustomsClearance(ServiceChargeRate rates, CreateChargeSummaryRequestItem item) {
        BigDecimal customsClearance = rates.getCustomsClearance().multiply(item.getDeclaredCargoPrice());

        return item.getQuantity().multiply(customsClearance);
    }

    private BigDecimal calculateHandlingCharge(ServiceChargeRate rates, CreateChargeSummaryRequestItem item) {
        BigDecimal handlingCharge = rates.getHandlingChargeRatePerKg()
                .multiply(BigDecimal.valueOf(item.getShippingWeight()))
                .max(rates.getHandlingChargeMin());

        return item.getQuantity().multiply(handlingCharge);
    }

    @Override
    public ChargeType getChargeType() {
        return ChargeType.SERVICE;
    }
}
