package com.filiaiev.chargeservice.service.pricing.strategy;

import com.filiaiev.chargeservice.model.charge.ChargeType;
import com.filiaiev.chargeservice.model.charge.CreateChargeSummaryRequest;
import com.filiaiev.chargeservice.model.charge.CreateChargeSummaryRequestItem;
import com.filiaiev.chargeservice.model.charge.ItemCharge;
import com.filiaiev.chargeservice.model.rate.ServiceChargeRate;
import com.filiaiev.chargeservice.service.rate.ServiceRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceChargeStrategy implements ChargeStrategy {

    private final ServiceRateService rateService;

    @Override
    public List<ItemCharge> createCharge(CreateChargeSummaryRequest request) {
        ServiceChargeRate rates = rateService.getLatestRates();

        List<ItemCharge> chargeList = new ArrayList<>();

        for (CreateChargeSummaryRequestItem item : request.getItems()) {
            chargeList.add(new ItemCharge(item.getPosition(), ChargeType.SERVICE, calculateItemTotal(rates, item)));
        }

        return chargeList;
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
}
