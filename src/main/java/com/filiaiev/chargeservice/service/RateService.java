package com.filiaiev.chargeservice.service;

import com.filiaiev.chargeservice.model.WeightRate;
import com.filiaiev.chargeservice.repository.WeightRateRepository;
import com.filiaiev.chargeservice.repository.entity.WeightRateDO;
import com.filiaiev.chargeservice.service.mapper.RateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RateService {

    private final WeightRateRepository weightRateRepository;

    private final RateMapper rateMapper;

    public List<WeightRate> getLatestWeightRates(Integer zoneRouteId) {
        return rateMapper.mapWeightRateDOsToWeightRates(
                weightRateRepository.findLatestEffectiveRates(zoneRouteId)
        );
    }

    public List<WeightRate> getWeightRates(Integer zoneRouteId, Instant from, Instant to) {
        return rateMapper.mapWeightRateDOsToWeightRates(
                weightRateRepository.findRatesFiltered(zoneRouteId, from, to)
        );
    }

    public void createWeightRates(List<WeightRate> weightRates) {
        List<WeightRateDO> weightRateDOs = rateMapper.mapWeightRatesToWeightRateDOs(weightRates);
        weightRateRepository.saveAll(weightRateDOs);
    }
}