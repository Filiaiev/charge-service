package com.filiaiev.chargeservice.resource.mapper;

import com.filiaiev.chargeservice.model.Dimension;
import com.filiaiev.chargeservice.model.charge.*;
import com.filiaiev.chargeservice.resource.entity.DimensionRO;
import com.filiaiev.chargeservice.resource.entity.charge.*;
import com.filiaiev.chargeservice.service.util.ChargeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PricingResourceMapper {

    CreateChargeSummaryRequest mapRoToModel(CreateChargeSummaryRequestRO objectRO);

    default CreateChargeSummaryRequestItem mapRequestItemROToRequestItem(CreateChargeSummaryRequestItemRO requestRO, int position) {
        CreateChargeSummaryRequestItem item = new CreateChargeSummaryRequestItem();

        item.setPosition(position);
        item.setQuantity(requestRO.getQuantity());
        item.setDeclaredWeight(requestRO.getDeclaredWeight());

        Dimension dimension = dimensionRoToDimension(requestRO.getDimension());

        item.setDimension(dimension);
        item.setShippingWeight(ChargeUtils.getShippingWeight(dimension, requestRO.getDeclaredWeight()));
        item.setDeclaredCargoPrice(requestRO.getDeclaredCargoPrice());

        return item;
    }

    default List<CreateChargeSummaryRequestItem> mapCalculateShippingPriceRequestItemROListToCreateChargeSummaryRequestItems(List<CreateChargeSummaryRequestItemRO> requestItems) {
        if (requestItems == null) {
            return null;
        }

        List<CreateChargeSummaryRequestItem> resultList = new ArrayList<>();
        for (int i = 1; i < requestItems.size()+1; i++) {
            resultList.add(mapRequestItemROToRequestItem(requestItems.get(i-1), i));
        }

        return resultList;
    }

    Dimension dimensionRoToDimension(DimensionRO dimensionRO);

    default ChargeSummaryRO mapChargeSummaryToChargeSummaryRO(ChargeSummary chargeSummary) {
        ChargeSummaryRO chargeSummaryRO = new ChargeSummaryRO();

        chargeSummaryRO.setItemsBreakdown(mapChargeSummaryItemsToChargeSummaryItemROs(chargeSummary.getItemsBreakdown()));
        chargeSummaryRO.setTotal(chargeSummary.getTotal());

        List<ItemChargeRO> itemsBreakdown = chargeSummary.getTotalBreakdownByType().entrySet().stream()
                .map((entry) -> mapItemChargeToItemChargeRO(
                        new ItemCharge(entry.getKey(), entry.getValue()))
                )
                .collect(Collectors.toList());

        chargeSummaryRO.setTotalBreakdown(itemsBreakdown);

        return chargeSummaryRO;
    };

    @Mapping(target = "itemBreakdown", source = "itemBreakdown")
    ChargeSummaryItemRO mapChargeSummaryItemToChargeSummaryItemRO(ChargeSummaryItem chargeSummaryItem);

    List<ChargeSummaryItemRO> mapChargeSummaryItemsToChargeSummaryItemROs(List<ChargeSummaryItem> items);

    ItemChargeRO mapItemChargeToItemChargeRO(ItemCharge itemCharge);
}
