package com.filiaiev.chargeservice.repository;

import com.filiaiev.chargeservice.repository.entity.SurchargeRateDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SurchargeRateRepository extends JpaRepository<SurchargeRateDO, Integer>, JpaSpecificationExecutor<SurchargeRateDO> {
}
