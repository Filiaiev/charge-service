package com.filiaiev.chargeservice.repository.specification;

import com.filiaiev.chargeservice.repository.entity.WeightRateDO;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public class RateSpecification {

    public static Specification<WeightRateDO> hasZoneRouteId(Integer zoneRouteId) {
        return (root, query, criteriaBuilder) ->
                zoneRouteId == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("zoneRouteId"), zoneRouteId);
    }

    public static Specification<WeightRateDO> isEffectiveDateInRange(Instant from, Instant to) {
        return (root, query, criteriaBuilder) ->
                from == null || to == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.between(root.get("effectiveDate"), from, to);
    }

    public static Specification<WeightRateDO> isLatestEffective(Integer zoneRouteId) {
        return (root, query, criteriaBuilder) -> {
            Subquery<Instant> subquery = query.subquery(Instant.class);
            Root<WeightRateDO> subRoot = subquery.from(WeightRateDO.class);

            subquery.select(criteriaBuilder.greatest(subRoot.<Instant>get("effectiveDate")))
                    .where(criteriaBuilder.and(
                                    criteriaBuilder.equal(subRoot.get("zoneRouteId"), zoneRouteId),
                                    criteriaBuilder.lessThanOrEqualTo(subRoot.get("effectiveDate"), Instant.now())
                            )
                    );

            query.orderBy(criteriaBuilder.asc(root.get("weightLimitKg")));

            return criteriaBuilder.equal(root.get("effectiveDate"), subquery);
        };
    }
}
