package com.filiaiev.chargeservice.service;

import com.filiaiev.chargeservice.model.*;
import com.filiaiev.chargeservice.repository.ServiceChargeRateRepository;
import com.filiaiev.chargeservice.repository.SurchargeRateRepository;
import com.filiaiev.chargeservice.repository.WeightRateRepository;
import com.filiaiev.chargeservice.service.mapper.RateMapper;
import jakarta.transaction.Transactional;
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

    private final WeightRateRepository weightRateRepository;
    private final ServiceChargeRateRepository serviceChargeRateRepository;
    private final SurchargeRateRepository surchargeRateRepository;

    private final RateMapper rateMapper;

    @Transactional
    public DetailedChargeSummary createChargeSummary(CreateChargeSummaryRequest request) {
        List<WeightRate> weightRates = rateMapper.mapWeightRateDOsToWeightRates(
                weightRateRepository.findLatestEffectiveRatesByZoneRouteId(request.getZoneRouteId())
        );

        Map<SurchargeType, SurchargeRate> surchargeRates = rateMapper.mapSurchargeRateDOsToSurchargeRates(
                surchargeRateRepository.findLatestEffectiveSurchargesRateByZoneRouteId(request.getZoneRouteId())
        );

        ServiceChargeRate serviceChargeRate = rateMapper.mapServiceChargeRateDOToServiceChargeRate(
                serviceChargeRateRepository.findLatestEffectiveServiceCharge()
        );

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
