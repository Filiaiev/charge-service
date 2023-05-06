package com.filiaiev.chargeservice.service.pricing;

import com.filiaiev.chargeservice.model.ChargeType;
import com.filiaiev.chargeservice.model.CreateChargeSummaryRequest;
import com.filiaiev.chargeservice.model.CreateChargeSummaryRequestItem;
import com.filiaiev.chargeservice.model.WeightRate;
import com.filiaiev.chargeservice.service.rate.WeightRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeightChargeStrategy implements ChargeStrategy {

    private final WeightRateService rateService;

    @Override
    public BigDecimal createCharge(CreateChargeSummaryRequest request) {
        List<WeightRate> weightRates = rateService.getLatestWeightRates(request.getZoneRouteId());
        BigDecimal total = BigDecimal.ZERO;

        for (CreateChargeSummaryRequestItem ci : request.getItems()) {
            BigDecimal itemTotal = calculateItemTotal(ci, weightRates);
            total = total.add(itemTotal);
        }

        return total;
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

    @Override
    public ChargeType getChargeType() {
        return ChargeType.WEIGHT;
    }
}
