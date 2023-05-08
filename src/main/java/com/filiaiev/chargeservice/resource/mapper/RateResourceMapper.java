package com.filiaiev.chargeservice.resource.mapper;

import com.filiaiev.chargeservice.model.rate.ServiceChargeRate;
import com.filiaiev.chargeservice.model.rate.SurchargeRate;
import com.filiaiev.chargeservice.model.rate.SurchargeType;
import com.filiaiev.chargeservice.model.rate.WeightRate;
import com.filiaiev.chargeservice.resource.entity.rate.service.CreateServiceRatesRequestRO;
import com.filiaiev.chargeservice.resource.entity.rate.service.ServiceChargeRateRO;
import com.filiaiev.chargeservice.resource.entity.rate.surcharge.CreateSurchargeRatesRequestRO;
import com.filiaiev.chargeservice.resource.entity.rate.surcharge.SurchargeRateRO;
import com.filiaiev.chargeservice.resource.entity.rate.weight.CreateWeightRatesRequestRO;
import com.filiaiev.chargeservice.resource.entity.rate.weight.WeightRateRO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RateResourceMapper {

    List<WeightRateRO> mapWeightRatesToWeightRateROs(List<WeightRate> weightRates);

    List<WeightRate> mapWeightRateROsToWeightRates(List<WeightRateRO> weightRateROs);

    default List<WeightRate> mapCreateWeightRatesRequestROToWeightRates(CreateWeightRatesRequestRO requestRO) {
        List<WeightRate> weightRates = mapWeightRateROsToWeightRates(requestRO.getRates());

        weightRates.forEach((rate) -> {
            rate.setZoneRouteId(requestRO.getZoneRouteId());
            rate.setEffectiveDate(requestRO.getEffectiveDate());
        });

        return weightRates;
    }

    SurchargeRateRO mapSurchargeRateToSurchargeRateRO(SurchargeRate surchargeRate);

    default List<SurchargeRateRO> mapSurchargeRatesMapToSurchargeRateROs(Map<SurchargeType, SurchargeRate> surchargeMap) {
        return surchargeMap.values().stream()
                .map(this::mapSurchargeRateToSurchargeRateRO)
                .collect(Collectors.toList());
    }

    ServiceChargeRateRO mapServiceChargeRateToServiceChargeRateRO(ServiceChargeRate serviceChargeRate);

    List<SurchargeRate> mapSurchargeRateROsToSurchargeRates(List<SurchargeRateRO> surchargeRateROs);

    default List<SurchargeRate> mapCreateSurchargeRatesRequestROToSurchargeRates(CreateSurchargeRatesRequestRO requestRO) {
        List<SurchargeRate> surchargeRates = mapSurchargeRateROsToSurchargeRates(requestRO.getRates());

        surchargeRates.forEach((rate) -> {
            rate.setEffectiveDate(requestRO.getEffectiveDate());
            rate.setZoneRouteId(requestRO.getZoneRouteId());
        });

        return surchargeRates;
    }

    @Mapping(target = "customsClearance", source = "customsClearanceMultiplier")
    ServiceChargeRate mapCreateServiceRateRequestROToServiceChargeRate(CreateServiceRatesRequestRO requestRO);
}
