package com.filiaiev.chargeservice.repository;

import com.filiaiev.chargeservice.repository.entity.ServiceChargeRateDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceChargeRateRepository extends JpaRepository<ServiceChargeRateDO, Integer>, JpaSpecificationExecutor<ServiceChargeRateDO> {
}
