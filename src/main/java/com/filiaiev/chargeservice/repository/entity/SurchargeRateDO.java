package com.filiaiev.chargeservice.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Entity(name = "surcharge_rates")
@Data
public class SurchargeRateDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer zoneRouteId;
    @Enumerated(value = EnumType.STRING)
    private SurchargeTypeDO type;
    private BigDecimal ratePerKg;
    private Instant effectiveDate;
}
