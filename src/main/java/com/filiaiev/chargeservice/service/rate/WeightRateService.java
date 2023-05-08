package com.filiaiev.chargeservice.service.rate;

import com.filiaiev.chargeservice.model.rate.WeightRate;
import com.filiaiev.chargeservice.repository.WeightRateRepository;
import com.filiaiev.chargeservice.repository.entity.WeightRateDO;
import com.filiaiev.chargeservice.repository.specification.WeightRateSpecification;
import com.filiaiev.chargeservice.service.mapper.RateServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeightRateService {

    private final WeightRateRepository repository;
    private final WeightRateSpecification specification;
    private final RateServiceMapper mapper;

    public List<WeightRate> getLatestWeightRates(Integer zoneRouteId) {
        return mapper.mapWeightRateDOsToWeightRates(
                repository.findAll(specification.isLatestEffective(zoneRouteId))
        );
    }

    public List<WeightRate> getWeightRates(Integer zoneRouteId, Instant from, Instant to) {
        return mapper.mapWeightRateDOsToWeightRates(
                repository.findAll(specification.hasZoneRouteId(zoneRouteId)
                        .and(specification.isEffectiveDateInRange(from, to))
                )
        );
    }

    public void createWeightRates(List<WeightRate> weightRates) {
        List<WeightRateDO> weightRateDOs = mapper.mapWeightRatesToWeightRateDOs(weightRates);
        repository.saveAll(weightRateDOs);
    }
}