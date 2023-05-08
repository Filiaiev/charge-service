package com.filiaiev.chargeservice.service.pricing.strategy;

import com.filiaiev.chargeservice.model.charge.ChargeType;
import com.filiaiev.chargeservice.model.charge.CreateChargeSummaryRequest;
import com.filiaiev.chargeservice.model.charge.CreateChargeSummaryRequestItem;
import com.filiaiev.chargeservice.model.charge.ItemCharge;
import com.filiaiev.chargeservice.model.rate.WeightRate;
import com.filiaiev.chargeservice.service.rate.WeightRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeightChargeStrategy implements ChargeStrategy {

    private final WeightRateService rateService;

    @Override
    public List<ItemCharge> createCharge(CreateChargeSummaryRequest request) {
        List<WeightRate> weightRates = rateService.getLatestWeightRates(request.getZoneRouteId());

        List<ItemCharge> chargeList = new ArrayList<>();

        for (CreateChargeSummaryRequestItem ci : request.getItems()) {
            BigDecimal itemTotal = calculateItemTotal(ci, weightRates);

            chargeList.add(
                    new ItemCharge(ci.getPosition(), ChargeType.WEIGHT, itemTotal)
            );
        }

        return chargeList;
    }

    private BigDecimal calculateItemTotal(CreateChargeSummaryRequestItem item, List<WeightRate> weightRates) {
        WeightRate rate = getWeightRate(weightRates, item.getShippingWeight());

        BigDecimal weightRatedCharge = getChargeByRate(item.getShippingWeight(), rate);

        return item.getQuantity().multiply(
                rate.getBaseRate().add(weightRatedCharge)
        );
    }

    private WeightRate getWeightRate(List<WeightRate> weightRates, Double weight) {
        // if list size is 0 -> throw exception to prevent not-wanted behavior
        WeightRate rate = weightRates.get(0);

        for (int i = 1; i < weightRates.size(); i++) {
            if(weight < weightRates.get(i).getWeightLimitKg()) {
                return rate;
            }
            rate = weightRates.get(i);
        }


        return rate;
    }

    private BigDecimal getChargeByRate(double weight, WeightRate shipmentWeightRate) {
        double weightToCharge = Double.max(weight, shipmentWeightRate.getWeightLimitKg());

        BigDecimal weightOverBaseLimit =
                BigDecimal.valueOf(Math.abs(shipmentWeightRate.getWeightLimitKg() - weightToCharge));

        return shipmentWeightRate.getRatePerKg().multiply(weightOverBaseLimit);
    }
}
