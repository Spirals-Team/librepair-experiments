package com.hedvig.productPricing.service.web.dto;


import com.hedvig.productPricing.service.aggregates.ProductStates;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class ProductStatusTest {

    Clock clock;
    private final ProductStates state;
    LocalDateTime fromDate;
    LocalDateTime toDate;
    private final ProductStatus expectedStatus;

    @Parameterized.Parameters
    public static Collection<Object> data() {
        return Arrays.asList(new Object[][] {
                {"2018-02-01T00:01:01.00", ProductStates.SIGNED, "2018-02-01T00:01:01", null, ProductStatus.ACTIVE},
                {"2018-02-02T00:01:01.00", ProductStates.SIGNED, "2018-02-01T00:01:01", null, ProductStatus.ACTIVE},
                {"2018-02-02T00:01:01.00", ProductStates.SIGNED, "2018-02-03T00:01:01", null, ProductStatus.INACTIVE_WITH_START_DATE},
                {"2018-02-03T00:01:01.00", ProductStates.SIGNED, "2018-02-01T00:01:01", "2018-02-02T00:01:01", ProductStatus.INACTIVE},
                {"2018-02-02T00:01:01.00", ProductStates.SIGNED, null, null, ProductStatus.INACTIVE},
                {"2018-02-02T00:01:01.00", ProductStates.TERMINATED, null, null, ProductStatus.INACTIVE},
                {"2018-02-02T00:01:01.00", ProductStates.QUOTE, null, null, ProductStatus.PENDING}
                });
    }

    public ProductStatusTest(String clock, ProductStates states, String fromDate, String toDate, ProductStatus expectedStatus) {

        this.clock = Clock.fixed(Instant.parse(clock + "Z"), ZoneId.systemDefault());
        this.state = states;
        this.fromDate = parseDate(fromDate);
        this.toDate = parseDate(toDate);

        this.expectedStatus = expectedStatus;
    }

    @Test
    public void test() {
        assertThat(ProductStatus.createStatus(clock, state, fromDate, toDate).getStatus()).isEqualTo(expectedStatus);
    }

    private LocalDateTime parseDate(String dateString) {
        return dateString == null ? null : LocalDateTime.parse(dateString);
    }

}
