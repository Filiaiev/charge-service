package com.filiaiev.chargeservice.repository.specification;

import com.filiaiev.chargeservice.repository.entity.ServiceChargeRateDO;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ServiceRateSpecification extends RateSpecification<ServiceChargeRateDO> {

    public ServiceRateSpecification() {
        super(ServiceChargeRateDO.class);
    }

    public Specification<ServiceChargeRateDO> isLatestEffective() {
        return (root, query, criteriaBuilder) -> {
            Subquery<Instant> subquery = query.subquery(Instant.class);
            Root<ServiceChargeRateDO> subRoot = subquery.from(ServiceChargeRateDO.class);

            subquery.select(criteriaBuilder.greatest(subRoot.<Instant>get("effectiveDate")))
                    .where(criteriaBuilder.lessThanOrEqualTo(subRoot.get("effectiveDate"), Instant.now()));

            return criteriaBuilder.equal(root.get("effectiveDate"), subquery);
        };
    }
}
