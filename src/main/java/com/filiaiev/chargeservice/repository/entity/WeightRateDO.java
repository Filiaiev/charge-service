package com.filiaiev.chargeservice.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Entity(name = "weight_rates")
@Data
public class WeightRateDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer zoneRouteId;
    private Double weightLimitKg;
    private BigDecimal baseRate;
    private BigDecimal ratePerKg;
    private Instant effectiveDate;
}
