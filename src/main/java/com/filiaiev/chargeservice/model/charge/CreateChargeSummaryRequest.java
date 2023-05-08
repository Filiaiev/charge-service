package com.filiaiev.chargeservice.model.charge;

import lombok.Data;

import java.util.List;

@Data
public class CreateChargeSummaryRequest {

    private Integer zoneRouteId;
    private List<CreateChargeSummaryRequestItem> items;
}
