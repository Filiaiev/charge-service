package com.filiaiev.chargeservice.service.pricing;

import com.filiaiev.chargeservice.model.charge.*;
import com.filiaiev.chargeservice.service.pricing.strategy.ChargeStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PricingService {

    private final List<ChargeStrategy> chargeStrategies;

    public ChargeSummary createChargeSummary(CreateChargeSummaryRequest request) {
        Map<Integer, ChargeSummaryItem> itemChargeSummaryByPosition = chargeStrategies.stream()
                .map(strategy -> strategy.createChargeSummary(request))
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(ItemCharge::getPosition,
                        Collectors.collectingAndThen(Collectors.toList(), ChargeSummaryItem::new))
                );

        return buildChargeSummary(itemChargeSummaryByPosition);
    }

    private ChargeSummary buildChargeSummary(Map<Integer, ChargeSummaryItem> chargeSummaryByPosition) {
        List<ChargeSummaryItem> itemsChargeBreakdown = getItemsChargeBreakdown(chargeSummaryByPosition);
        Map<ChargeType, BigDecimal> totalChargeBreakdown = getTotalChargeBreakdownByType(itemsChargeBreakdown);

        return ChargeSummary.builder()
                .itemsBreakdown(itemsChargeBreakdown)
                .totalBreakdownByType(totalChargeBreakdown)
                .build();
    }

    private List<ChargeSummaryItem> getItemsChargeBreakdown(Map<Integer, ChargeSummaryItem> chargeSummaryByPosition) {
        return chargeSummaryByPosition.keySet().stream()
                .sorted()
                .map(chargeSummaryByPosition::get)
                .collect(Collectors.toList());
    }

    private Map<ChargeType, BigDecimal> getTotalChargeBreakdownByType(List<ChargeSummaryItem> summaryItems) {
        return summaryItems.stream()
                .map(ChargeSummaryItem::getItemBreakdown)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(ItemCharge::getType,
                        Collectors.mapping(ItemCharge::getCharge,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));
    }
}
