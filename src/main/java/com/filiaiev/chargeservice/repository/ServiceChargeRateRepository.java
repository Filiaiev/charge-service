package com.filiaiev.chargeservice.repository;

import com.filiaiev.chargeservice.repository.entity.ServiceChargeRateDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceChargeRateRepository extends JpaRepository<ServiceChargeRateDO, Integer> {

    @Query(value = "SELECT scr FROM service_charge_rates scr WHERE scr.effectiveDate = (SELECT MAX(scr.effectiveDate) FROM service_charge_rates scr WHERE scr.effectiveDate <= NOW())")
    ServiceChargeRateDO findLatestEffectiveServiceCharge();
}
