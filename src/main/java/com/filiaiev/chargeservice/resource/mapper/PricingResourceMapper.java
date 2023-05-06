package com.filiaiev.chargeservice.resource.mapper;

import com.filiaiev.chargeservice.model.*;
import com.filiaiev.chargeservice.resource.pricing.ro.*;
import com.filiaiev.chargeservice.service.util.ChargeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PricingResourceMapper {

    CreateChargeSummaryRequest mapRoToModel(CalculateShippingPriceRequestRO objectRO);

    default CreateChargeSummaryRequestItem mapRequestItemROToRequestItem(CalculateShippingPriceRequestItemRO requestRO) {
        CreateChargeSummaryRequestItem item = new CreateChargeSummaryRequestItem();

        item.setQuantity(requestRO.getQuantity());
        item.setDeclaredWeight(requestRO.getDeclaredWeight());

        Dimension dimension = dimensionRoToDimension(requestRO.getDimension());

        item.setDimension(dimension);
        item.setShippingWeight(ChargeUtils.getShippingWeight(dimension, requestRO.getDeclaredWeight()));
        item.setDeclaredCargoPrice(requestRO.getDeclaredCargoPrice());

        return item;
    }

    Dimension dimensionRoToDimension(DimensionRO dimensionRO);

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
