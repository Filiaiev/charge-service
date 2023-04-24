package com.filiaiev.chargeservice.service;

import com.filiaiev.chargeservice.model.*;
import com.filiaiev.chargeservice.service.rate.ServiceRateService;
import com.filiaiev.chargeservice.service.rate.SurchargeRateService;
import com.filiaiev.chargeservice.service.rate.WeightRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// TODO: Add decorator

@Service
@RequiredArgsConstructor
public class PricingService {

    private final WeightRateService weightRateService;
    private final ServiceRateService serviceRateService;
    private final SurchargeRateService surchargeRateService;

    public DetailedChargeSummary createChargeSummary(CreateChargeSummaryRequest request) {
        List<WeightRate> weightRates = weightRateService.getLatestWeightRates(request.getZoneRouteId());

        Map<SurchargeType, SurchargeRate> surchargeRates =
                surchargeRateService.getLatestSurchargeRates(request.getZoneRouteId());

        ServiceChargeRate serviceChargeRate = serviceRateService.getLatestRates();

        List<DetailedChargeSummaryItem> chargesSummary = new ArrayList<>();

        for (CreateChargeSummaryRequestItem ci : request.getItems()) {
            double volumetricWeight = ci.getHeight() * ci.getWidth() * ci.getLength() * 167;
            double shipmentWeight = Math.max(volumetricWeight, ci.getActualWeight());

            WeightRate shipmentWeightRate = getShipmentWeightRate(weightRates, shipmentWeight);
            ci.setFinalShippingWeight(Double.max(shipmentWeight, shipmentWeightRate.getWeightLimitKg()));

            // If I use decorator, I MAY, not 100%, move logic to decorator
            BigDecimal weightBasedCharge = ci.getWeightCharge(shipmentWeightRate);
            Map<SurchargeType, BigDecimal> surchargeCharges = ci.getSurchargeCharges(surchargeRates);
            ServiceCharge serviceCharge = ci.getServiceCharge(serviceChargeRate);

            chargesSummary.add(DetailedChargeSummaryItem.builder()
                    .cargoItem(ci)
                    .serviceFinalCharge(serviceCharge)
                    .surchargeFinalCharge(surchargeCharges)
                    .weightFinalCharge(weightBasedCharge)
                    .build()
            );
        }

        return new DetailedChargeSummary(chargesSummary);
    }

    private WeightRate getShipmentWeightRate(List<WeightRate> weightRates, Double shipmentWeight) {
        // if list size is 0 -> throw exception to prevent not-wanted behavior
        WeightRate rate = weightRates.get(0);

        for (int i = 1; i < weightRates.size(); i++) {
            if(shipmentWeight < weightRates.get(i).getWeightLimitKg()) {
                return rate;
            }
            rate = weightRates.get(i);
        }

        return rate;
    }
}
