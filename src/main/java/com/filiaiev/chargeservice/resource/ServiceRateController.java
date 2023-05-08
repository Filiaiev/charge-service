package com.filiaiev.chargeservice.resource;

import com.filiaiev.chargeservice.model.rate.ServiceChargeRate;
import com.filiaiev.chargeservice.resource.entity.rate.service.CreateServiceRatesRequestRO;
import com.filiaiev.chargeservice.resource.entity.rate.service.ServiceChargeRateRO;
import com.filiaiev.chargeservice.resource.mapper.RateResourceMapper;
import com.filiaiev.chargeservice.service.rate.ServiceRateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service-rates")
@RequiredArgsConstructor
public class ServiceRateController {

    private final ServiceRateService serviceRateService;
    private final RateResourceMapper rateResourceMapper;

    @GetMapping("/effective")
    public ServiceChargeRateRO getLatestRates() {
        return rateResourceMapper.mapServiceChargeRateToServiceChargeRateRO(
                serviceRateService.getLatestRates()
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRates(@Valid @RequestBody CreateServiceRatesRequestRO requestRO) {
        ServiceChargeRate serviceChargeRate = rateResourceMapper.mapCreateServiceRateRequestROToServiceChargeRate(requestRO);
        serviceRateService.createRates(serviceChargeRate);
    }
}
