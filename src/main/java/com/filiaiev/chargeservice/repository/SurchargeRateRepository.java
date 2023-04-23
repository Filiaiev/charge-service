package com.filiaiev.chargeservice.repository;

import com.filiaiev.chargeservice.repository.entity.SurchargeRateDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurchargeRateRepository extends JpaRepository<SurchargeRateDO, Integer> {

    @Query(value = "SELECT sr FROM surcharge_rates sr WHERE sr.effectiveDate = (SELECT MAX(sr.effectiveDate) FROM surcharge_rates sr WHERE sr.effectiveDate <= NOW()) AND sr.zoneRouteId = ?1")
    List<SurchargeRateDO> findLatestEffectiveSurchargesRateByZoneRouteId(Integer id);
}
