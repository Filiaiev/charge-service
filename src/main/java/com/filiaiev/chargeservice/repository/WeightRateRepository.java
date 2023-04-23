package com.filiaiev.chargeservice.repository;

import com.filiaiev.chargeservice.repository.entity.WeightRateDO;
import com.filiaiev.chargeservice.repository.specification.RateSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

import static com.filiaiev.chargeservice.repository.specification.RateSpecification.*;

@Repository
public interface WeightRateRepository extends JpaRepository<WeightRateDO, Integer>, JpaSpecificationExecutor<WeightRateDO> {

    @Query(value = "SELECT wr FROM weight_rates wr WHERE wr.effectiveDate = (SELECT MAX(wr.effectiveDate) FROM weight_rates wr WHERE wr.effectiveDate <= NOW()) AND wr.zoneRouteId = ?1 ORDER BY wr.weightLimitKg")
    List<WeightRateDO> findLatestEffectiveRatesByZoneRouteId(Integer zoneRouteId);

    default List<WeightRateDO> findRatesFiltered(Integer zoneRouteId, Instant from, Instant to) {
        return findAll(hasZoneRouteId(zoneRouteId)
                .and(isEffectiveDateInRange(from, to))
        );
    }

    default List<WeightRateDO> findLatestEffectiveRates(Integer zoneRouteId) {
        return findAll(isLatestEffective(zoneRouteId));
    }
}
