package com.filiaiev.chargeservice.repository.specification;

import com.filiaiev.chargeservice.repository.entity.SurchargeRateDO;
import org.springframework.stereotype.Component;

@Component
public class SurchargeRateSpecification extends RateSpecification<SurchargeRateDO> {

    public SurchargeRateSpecification() {
        super(SurchargeRateDO.class);
    }
}
