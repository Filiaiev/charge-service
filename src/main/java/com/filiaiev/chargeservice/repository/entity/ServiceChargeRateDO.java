package com.filiaiev.chargeservice.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Entity(name = "service_charge_rates")
@Data
public class ServiceChargeRateDO implements Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private BigDecimal handlingChargeRatePerKg;
    private BigDecimal handlingChargeMin;
    private BigDecimal customsClearance;
    private Instant effectiveDate;
}
