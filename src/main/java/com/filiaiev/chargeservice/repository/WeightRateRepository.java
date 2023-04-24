package com.filiaiev.chargeservice.repository;

import com.filiaiev.chargeservice.repository.entity.WeightRateDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface WeightRateRepository extends JpaRepository<WeightRateDO, Integer>, JpaSpecificationExecutor<WeightRateDO> {
}
