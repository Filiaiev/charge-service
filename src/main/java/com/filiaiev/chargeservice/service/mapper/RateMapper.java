package com.filiaiev.chargeservice.service.mapper;

import com.filiaiev.chargeservice.model.ServiceChargeRate;
import com.filiaiev.chargeservice.model.SurchargeRate;
import com.filiaiev.chargeservice.model.SurchargeType;
import com.filiaiev.chargeservice.model.WeightRate;
import com.filiaiev.chargeservice.repository.entity.ServiceChargeRateDO;
import com.filiaiev.chargeservice.repository.entity.SurchargeRateDO;
import com.filiaiev.chargeservice.repository.entity.WeightRateDO;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RateMapper {

    List<WeightRate> mapWeightRateDOsToWeightRates(List<WeightRateDO> weightRatesDOs);

    List<WeightRateDO> mapWeightRatesToWeightRateDOs(List<WeightRate> weightRates);

    SurchargeRate mapSurchargeRateDOToSurchargeRate(SurchargeRateDO surchargeRateDO);

    default Map<SurchargeType, SurchargeRate> mapSurchargeRateDOsToSurchargeRates(List<SurchargeRateDO> surchargeRateDO) {
        return surchargeRateDO.stream()
                .map(this::mapSurchargeRateDOToSurchargeRate)
                .collect(Collectors.toMap(SurchargeRate::getType, Function.identity()));
    };

    ServiceChargeRate mapServiceChargeRateDOToServiceChargeRate(ServiceChargeRateDO serviceChargeRateDO);
}
