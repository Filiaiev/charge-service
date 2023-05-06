package com.filiaiev.chargeservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Dimension {

    private double height;
    private double length;
    private double width;
}
