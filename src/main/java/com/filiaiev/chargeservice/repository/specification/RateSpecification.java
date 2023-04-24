package com.filiaiev.chargeservice.repository.specification;

import com.filiaiev.chargeservice.repository.entity.Rate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

@AllArgsConstructor
public abstract class RateSpecification<T extends Rate> {

    private final Class<T> clazz;

    public Specification<T> hasZoneRouteId(Integer zoneRouteId) {
        return (root, query, criteriaBuilder) ->
                zoneRouteId == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("zoneRouteId"), zoneRouteId);
    }

    public Specification<T> isEffectiveDateInRange(Instant from, Instant to) {
        return (root, query, criteriaBuilder) ->
                from == null || to == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.between(root.get("effectiveDate"), from, to);
    }

    public Specification<T> isLatestEffective(Integer zoneRouteId) {
        return (root, query, criteriaBuilder) -> {
            Subquery<Instant> subquery = query.subquery(Instant.class);
            Root<T> subRoot = subquery.from(clazz);

            subquery.select(criteriaBuilder.greatest(subRoot.<Instant>get("effectiveDate")))
                    .where(criteriaBuilder.and(
                                    criteriaBuilder.equal(subRoot.get("zoneRouteId"), zoneRouteId),
                                    criteriaBuilder.lessThanOrEqualTo(subRoot.get("effectiveDate"), Instant.now())
                            )
                    );

            return criteriaBuilder.equal(root.get("effectiveDate"), subquery);
        };
    }
}
