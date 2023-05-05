package com.filiaiev.chargeservice.resource.pricing;

import com.filiaiev.chargeservice.model.CreateChargeSummaryRequest;
import com.filiaiev.chargeservice.resource.mapper.PricingResourceMapper;
import com.filiaiev.chargeservice.resource.pricing.ro.CalculateShippingPriceRequestRO;
import com.filiaiev.chargeservice.resource.pricing.ro.DetailedChargeSummaryRO;
import com.filiaiev.chargeservice.resource.pricing.ro.ShortChargeSummaryRO;
import com.filiaiev.chargeservice.service.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pricing")
@RequiredArgsConstructor
public class PricingController {

    private final PricingService pricingService;
    private final PricingResourceMapper pricingResourceMapper;

    @PostMapping(produces = "application/summary-short+json")
    public ShortChargeSummaryRO getShortChargeSummary(@RequestBody CalculateShippingPriceRequestRO requestRO) {
        CreateChargeSummaryRequest request = pricingResourceMapper.mapRoToModel(requestRO);

        return pricingResourceMapper.mapDetailedChargeSummaryToShortChargeSummaryRO(
                pricingService.createChargeSummary(request)
        );
    }

    @PostMapping(produces = "application/summary-detailed+json")
    public DetailedChargeSummaryRO getDetailedChargeSummary(@RequestBody CalculateShippingPriceRequestRO requestRO) {
        CreateChargeSummaryRequest request = pricingResourceMapper.mapRoToModel(requestRO);

        return pricingResourceMapper.mapDetailedChargeSummaryToDetailedChargeSummaryRO(
                pricingService.createChargeSummary(request)
        );
    }
}
