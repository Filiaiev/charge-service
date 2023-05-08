package com.filiaiev.chargeservice.resource.mapper;

import com.filiaiev.chargeservice.model.Dimension;
import com.filiaiev.chargeservice.model.charge.ChargeSummary;
import com.filiaiev.chargeservice.model.charge.ChargeSummaryItem;
import com.filiaiev.chargeservice.model.charge.CreateChargeSummaryRequest;
import com.filiaiev.chargeservice.model.charge.CreateChargeSummaryRequestItem;
import com.filiaiev.chargeservice.resource.entity.DimensionRO;
import com.filiaiev.chargeservice.resource.entity.charge.ChargeSummaryItemRO;
import com.filiaiev.chargeservice.resource.entity.charge.ChargeSummaryRO;
import com.filiaiev.chargeservice.resource.entity.charge.CreateChargeSummaryRequestItemRO;
import com.filiaiev.chargeservice.resource.entity.charge.CreateChargeSummaryRequestRO;
import com.filiaiev.chargeservice.service.util.ChargeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

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

    ChargeSummaryRO mapChargeSummaryToChargeSummaryRO(ChargeSummary chargeSummary);

    @Mapping(target = "itemBreakdown", source = "chargesBreakdown")
    ChargeSummaryItemRO mapChargeSummaryItemToChargeSummaryItemRO(ChargeSummaryItem chargeSummaryItem);

}
