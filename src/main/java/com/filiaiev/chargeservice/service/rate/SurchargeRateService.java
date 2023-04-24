package com.filiaiev.chargeservice.service.rate;

import com.filiaiev.chargeservice.model.SurchargeRate;
import com.filiaiev.chargeservice.model.SurchargeType;
import com.filiaiev.chargeservice.repository.SurchargeRateRepository;
import com.filiaiev.chargeservice.repository.entity.SurchargeRateDO;
import com.filiaiev.chargeservice.repository.specification.SurchargeRateSpecification;
import com.filiaiev.chargeservice.service.mapper.RateMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class SurchargeRateService {

    private final SurchargeRateRepository repository;
    private final SurchargeRateSpecification specification;
    private final RateMapper mapper;

    public Map<SurchargeType, SurchargeRate> getLatestSurchargeRates(Integer zoneRouteId) {
        return mapper.mapSurchargeRateDOsToSurchargeRates(
                repository.findAll(specification.isLatestEffective(zoneRouteId))
        );
    }

    public void createSurchargeRates(List<SurchargeRate> surchargeRates) {
        List<SurchargeRateDO> surchargeRateDOs = mapper.mapSurchargeRatesToSurchargeRateDOs(surchargeRates);
        repository.saveAll(surchargeRateDOs);
    }
}
