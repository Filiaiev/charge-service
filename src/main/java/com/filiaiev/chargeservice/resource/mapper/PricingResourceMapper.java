package com.filiaiev.chargeservice.resource.mapper;

import com.filiaiev.chargeservice.model.CreateChargeSummaryRequest;
import com.filiaiev.chargeservice.model.CreateChargeSummaryRequestItem;
import com.filiaiev.chargeservice.model.DetailedChargeSummary;
import com.filiaiev.chargeservice.model.DetailedChargeSummaryItem;
import com.filiaiev.chargeservice.resource.pricing.ro.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PricingResourceMapper {

    CreateChargeSummaryRequest mapRoToModel(CalculateShippingPriceRequestRO objectRO);

    @Mapping(target = "chargesBreakdown", source = "items")
    DetailedChargeSummaryRO mapDetailedChargeSummaryToDetailedChargeSummaryRO(DetailedChargeSummary summary);

    CalculateShippingPriceRequestItemRO mapCalculateShippingPriceRequestItemToRo(CreateChargeSummaryRequestItem requestItem);

    default DetailedChargeSummaryItemRO mapDetailedChargeSummaryItemToRo(DetailedChargeSummaryItem item) {
        DetailedChargeSummaryItemRO ro = new DetailedChargeSummaryItemRO();

        ro.setCargoItem(mapCalculateShippingPriceRequestItemToRo(item.getCargoItem()));
        ro.setWeightTotal(item.getWeightFinalCharge());
        ro.setServiceTotal(item.getServiceTotal());
        ro.setSurchargeTotal(item.getSurchargeTotal());
        ro.setTotal(item.getTotal());

        return ro;
    }

    default ShortChargeSummaryRO mapDetailedChargeSummaryToShortChargeSummaryRO(DetailedChargeSummary detailedChargeSummary) {
        ShortChargeSummaryRO shortChargeSummaryRO = new ShortChargeSummaryRO();

        List<BigDecimal> itemsChargeList = detailedChargeSummary.getItems().stream()
                .map(DetailedChargeSummaryItem::getTotal)
                .collect(Collectors.toList());

        shortChargeSummaryRO.setTotal(detailedChargeSummary.getTotal());
        shortChargeSummaryRO.setItemsPriceList(itemsChargeList);

        return shortChargeSummaryRO;
    };

}
