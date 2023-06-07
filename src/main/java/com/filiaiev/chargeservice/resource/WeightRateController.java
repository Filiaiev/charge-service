package com.filiaiev.chargeservice.resource;

import com.filiaiev.chargeservice.model.rate.WeightRate;
import com.filiaiev.chargeservice.resource.entity.rate.weight.CreateWeightRatesRequestRO;
import com.filiaiev.chargeservice.resource.entity.rate.weight.WeightRateRO;
import com.filiaiev.chargeservice.resource.mapper.RateResourceMapper;
import com.filiaiev.chargeservice.service.rate.WeightRateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/weight-rates")
@RequiredArgsConstructor
public class WeightRateController {

    private final WeightRateService weightRateService;
    private final RateResourceMapper rateResourceMapper;

    @GetMapping("/effective")
    public List<WeightRateRO> getLatestRates(@RequestParam("route") Integer zoneRouteId) {
        return rateResourceMapper.mapWeightRatesToWeightRateROs(
                weightRateService.getLatestWeightRates(zoneRouteId)
        );
    }

    @GetMapping
    public List<WeightRateRO> getRates(@RequestParam(value = "route", required = false) Integer zoneRouteId,
                                       @RequestParam(required = false) Instant from,
                                       @RequestParam(required = false) Instant to) {
        return rateResourceMapper.mapWeightRatesToWeightRateROs(
                weightRateService.getWeightRates(zoneRouteId, from, to)
        );
    }

    // Permit for admin only
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRates(@Valid @RequestBody CreateWeightRatesRequestRO requestRO) {
        List<WeightRate> weightRates = rateResourceMapper.mapCreateWeightRatesRequestROToWeightRates(requestRO);
        weightRateService.createWeightRates(weightRates);
    }
}
