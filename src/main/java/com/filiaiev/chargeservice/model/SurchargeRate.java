package com.filiaiev.chargeservice.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class SurchargeRate {

    private Integer id;
    private SurchargeType type;
    private BigDecimal ratePerKg;
    private Instant effectiveDate;
}
