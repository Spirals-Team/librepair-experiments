package com.hedvig.productPricing.service.query;

import java.math.BigDecimal;
import java.time.LocalDateTime;

interface InsuranceBillingEntity {
    String getId();
    MemberBillingEntity getMember();
    LocalDateTime getActiveFrom();
    LocalDateTime getActiveTo();
    BigDecimal getCurrentTotalPrice();

    interface MemberBillingEntity {
        String getId();
    }
}
