package com.filiaiev.chargeservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

@SpringBootTest
class ChargeServiceApplicationTests {

    @Test
    void contextLoads() {
        Instant now = Instant.now();
        System.out.println(now);
    }

}
