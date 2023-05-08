package com.filiaiev.chargeservice.resource;

import com.filiaiev.chargeservice.model.charge.CreateChargeSummaryRequest;
import com.filiaiev.chargeservice.resource.entity.charge.ChargeSummaryRO;
import com.filiaiev.chargeservice.resource.entity.charge.CreateChargeSummaryRequestRO;
import com.filiaiev.chargeservice.resource.mapper.PricingResourceMapper;
import com.filiaiev.chargeservice.service.pricing.PricingService;
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

    @PostMapping
    public ChargeSummaryRO createChargeSummary(@RequestBody CreateChargeSummaryRequestRO requestRO) {
        CreateChargeSummaryRequest request = pricingResourceMapper.mapRoToModel(requestRO);

        return pricingResourceMapper.mapChargeSummaryToChargeSummaryRO(
                pricingService.createChargeSummary(request)
        );
    }
}
