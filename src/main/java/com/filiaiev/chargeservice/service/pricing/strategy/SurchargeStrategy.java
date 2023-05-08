package com.filiaiev.chargeservice.service.pricing.strategy;

import com.filiaiev.chargeservice.model.charge.CreateChargeSummaryRequest;
import com.filiaiev.chargeservice.model.charge.CreateChargeSummaryRequestItem;
import com.filiaiev.chargeservice.model.charge.ItemCharge;
import com.filiaiev.chargeservice.model.rate.SurchargeRate;
import com.filiaiev.chargeservice.model.rate.SurchargeType;
import com.filiaiev.chargeservice.service.mapper.RateServiceMapper;
import com.filiaiev.chargeservice.service.rate.SurchargeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SurchargeStrategy implements ChargeStrategy {

    private final SurchargeRateService rateService;
    private final RateServiceMapper rateServiceMapper;

    @Override
    public List<ItemCharge> createCharge(CreateChargeSummaryRequest request) {
        Map<SurchargeType, SurchargeRate> rates = rateService.getLatestSurchargeRates(request.getZoneRouteId());

        List<ItemCharge> chargeList = new ArrayList<>();

        for (CreateChargeSummaryRequestItem item : request.getItems()) {
            rates.forEach((type, rate) ->
                    chargeList.add(
                            new ItemCharge(item.getPosition(),
                                    rateServiceMapper.mapSurchargeTypeToChargeType(type),
                                    calculateItemTotal(item, rate)
                            )
                    )
            );
        }

        return chargeList;
    }

    private BigDecimal calculateItemTotal(CreateChargeSummaryRequestItem item, SurchargeRate rate) {
        BigDecimal itemSurcharge = rate.getRatePerKg().multiply(BigDecimal.valueOf(item.getShippingWeight()));

        return item.getQuantity().multiply(itemSurcharge);
    }
}
