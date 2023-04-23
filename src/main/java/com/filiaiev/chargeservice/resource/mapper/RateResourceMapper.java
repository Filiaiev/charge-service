package com.filiaiev.chargeservice.resource.mapper;

import com.filiaiev.chargeservice.model.WeightRate;
import com.filiaiev.chargeservice.resource.rate.ro.CreateWeightRatesRequestRO;
import com.filiaiev.chargeservice.resource.rate.ro.WeightRateRO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RateResourceMapper {

    List<WeightRateRO> mapWeightRatesToWeightRateROs(List<WeightRate> weightRates);

    List<WeightRate> mapWeightRateROsToWeightRates(List<WeightRateRO> weightRateROs);

    default List<WeightRate> mapCreateWeightRatesRequestROToWeightRates(CreateWeightRatesRequestRO requestRO) {
        List<WeightRate> weightRates = mapWeightRateROsToWeightRates(requestRO.getRates());
        weightRates.forEach((rate) -> rate.setZoneRouteId(requestRO.getZoneRouteId()));

        return weightRates;
    }
}
