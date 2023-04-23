package com.filiaiev.chargeservice.resource.rate;

import com.filiaiev.chargeservice.model.WeightRate;
import com.filiaiev.chargeservice.resource.mapper.RateResourceMapper;
import com.filiaiev.chargeservice.resource.rate.ro.CreateWeightRatesRequestRO;
import com.filiaiev.chargeservice.resource.rate.ro.WeightRateRO;
import com.filiaiev.chargeservice.service.RateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("rates")
@RequiredArgsConstructor
public class RateController {

    private final RateService rateService;

    private final RateResourceMapper rateResourceMapper;

    @GetMapping("/effective")
    public List<WeightRateRO> getLatestWeightRates(@RequestParam("route") Integer zoneRouteId) {
        return rateResourceMapper.mapWeightRatesToWeightRateROs(
                rateService.getLatestWeightRates(zoneRouteId)
        );
    }

    @GetMapping
    public List<WeightRateRO> getWeightRates(@RequestParam(value = "route", required = false) Integer zoneRouteId,
                                             @RequestParam(required = false) Instant from,
                                             @RequestParam(required = false) Instant to) {
        return rateResourceMapper.mapWeightRatesToWeightRateROs(
                rateService.getWeightRates(zoneRouteId, from, to)
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createWeightRates(@Valid @RequestBody CreateWeightRatesRequestRO requestRO) {
        List<WeightRate> weightRates = rateResourceMapper.mapCreateWeightRatesRequestROToWeightRates(requestRO);
        rateService.createWeightRates(weightRates);
    }
}
