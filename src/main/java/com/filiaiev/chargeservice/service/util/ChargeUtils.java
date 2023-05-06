package com.filiaiev.chargeservice.service.util;

import com.filiaiev.chargeservice.model.Dimension;

public abstract class ChargeUtils {

    public static double getShippingWeight(Dimension dimension, double declaredWeight) {
        double volumetricWeight =
                dimension.getHeight() *
                dimension.getWidth() *
                dimension.getLength() * 167;

        return Double.max(volumetricWeight, declaredWeight);
    }
}
