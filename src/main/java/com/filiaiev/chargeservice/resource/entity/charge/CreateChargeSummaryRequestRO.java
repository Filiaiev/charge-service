package com.filiaiev.chargeservice.resource.entity.charge;

import lombok.Data;

import java.util.List;

@Data
public class CreateChargeSummaryRequestRO {

    private Integer zoneRouteId;
    private List<CreateChargeSummaryRequestItemRO> items;
}
