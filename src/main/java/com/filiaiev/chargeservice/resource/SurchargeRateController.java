package com.filiaiev.chargeservice.resource;

import com.filiaiev.chargeservice.model.rate.SurchargeRate;
import com.filiaiev.chargeservice.resource.entity.rate.surcharge.CreateSurchargeRatesRequestRO;
import com.filiaiev.chargeservice.resource.entity.rate.surcharge.SurchargeRateRO;
import com.filiaiev.chargeservice.resource.mapper.RateResourceMapper;
import com.filiaiev.chargeservice.service.rate.SurchargeRateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/surcharge-rates")
@RequiredArgsConstructor
public class SurchargeRateController {

    private final SurchargeRateService surchargeRateService;
    private final RateResourceMapper rateResourceMapper;

    @GetMapping("/effective")
    public List<SurchargeRateRO> getLatestRates(@RequestParam("route") Integer zoneRouteId) {
        return rateResourceMapper.mapSurchargeRatesMapToSurchargeRateROs(
                surchargeRateService.getLatestSurchargeRates(zoneRouteId)
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRates(@Valid @RequestBody CreateSurchargeRatesRequestRO requestRO) {
        List<SurchargeRate> surchargeRates = rateResourceMapper.mapCreateSurchargeRatesRequestROToSurchargeRates(requestRO);
        surchargeRateService.createSurchargeRates(surchargeRates);
    }
}
