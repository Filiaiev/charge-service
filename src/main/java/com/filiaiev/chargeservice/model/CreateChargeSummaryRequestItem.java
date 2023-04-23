package com.filiaiev.chargeservice.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
public class CreateChargeSummaryRequestItem {

    private BigDecimal quantity;
    // Call declaredWeight
    private Double actualWeight;
    // Call shippingWeight
    private Double finalShippingWeight;
    private Double height;
    private Double width;
    private Double length;
    private BigDecimal declaredCargoPrice;

    // base rate + (weight limit - shipment weight) * rate per kg
    public BigDecimal getWeightCharge(WeightRate shipmentWeightRate) {
        BigDecimal chargeableWeight = BigDecimal.valueOf(Math.abs(shipmentWeightRate.getWeightLimitKg() - finalShippingWeight));
        BigDecimal chargeableWeightShipmentPrice = shipmentWeightRate.getRatePerKg().multiply(chargeableWeight);

        return quantity.multiply(
                shipmentWeightRate.getBaseRate().add(chargeableWeightShipmentPrice)
        );
    }

    public Map<SurchargeType, BigDecimal> getSurchargeCharges(Map<SurchargeType, SurchargeRate> surchargeRates) {
        Map<SurchargeType, BigDecimal> surchargeCharges = new HashMap<>();

        surchargeRates.forEach((type, rate) -> surchargeCharges.put(type,
                quantity.multiply(
                        rate.getRatePerKg().multiply(BigDecimal.valueOf(finalShippingWeight))
                )
        ));

        return surchargeCharges;
    }

    public ServiceCharge getServiceCharge(ServiceChargeRate serviceChargeRate) {
        BigDecimal handlingCharge = serviceChargeRate.getHandlingChargeRatePerKg()
                .multiply(BigDecimal.valueOf(finalShippingWeight));

        return new ServiceCharge(
                quantity.multiply(serviceChargeRate.getHandlingChargeMin().max(handlingCharge)),
                quantity.multiply(serviceChargeRate.getCustomsClearance().multiply(declaredCargoPrice))
        );
    }
}
