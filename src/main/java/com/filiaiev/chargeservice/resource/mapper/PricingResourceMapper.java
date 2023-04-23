package com.filiaiev.chargeservice.resource.mapper;

import com.filiaiev.chargeservice.model.CreateChargeSummaryRequest;
import com.filiaiev.chargeservice.model.CreateChargeSummaryRequestItem;
import com.filiaiev.chargeservice.model.DetailedChargeSummary;
import com.filiaiev.chargeservice.model.DetailedChargeSummaryItem;
import com.filiaiev.chargeservice.resource.pricing.ro.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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

    ShortChargeSummaryRO mapDetailedChargeSummaryToShortChargeSummaryRO(DetailedChargeSummary detailedChargeSummary);

}
