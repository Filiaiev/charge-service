package com.filiaiev.chargeservice.repository.specification;

import com.filiaiev.chargeservice.repository.entity.WeightRateDO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class WeightRateSpecification extends RateSpecification<WeightRateDO> {

    public WeightRateSpecification() {
        super(WeightRateDO.class);
    }

    public Specification<WeightRateDO> isLatestEffective(Integer zoneRouteId) {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.asc(root.get("weightLimitKg")));

            return super.isLatestEffective(zoneRouteId).toPredicate(root, query, criteriaBuilder);
        };
    }
}
