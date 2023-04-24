package com.filiaiev.chargeservice.service.rate;

import com.filiaiev.chargeservice.model.ServiceChargeRate;
import com.filiaiev.chargeservice.repository.ServiceChargeRateRepository;
import com.filiaiev.chargeservice.repository.entity.ServiceChargeRateDO;
import com.filiaiev.chargeservice.repository.specification.ServiceRateSpecification;
import com.filiaiev.chargeservice.service.mapper.RateMapper;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceRateService {

    private final ServiceChargeRateRepository repository;
    private final ServiceRateSpecification specification;
    private final RateMapper mapper;

    public ServiceChargeRate getLatestRates() {
        return mapper.mapServiceChargeRateDOToServiceChargeRate(
                repository.findOne(specification.isLatestEffective()).orElseThrow(EntityExistsException::new)
        );
    }

    public void createRates(ServiceChargeRate serviceChargeRate) {
        ServiceChargeRateDO serviceChargeRateDO = mapper.mapServiceChargeRateToServiceChargeRateDO(serviceChargeRate);
        repository.save(serviceChargeRateDO);
    }
}
