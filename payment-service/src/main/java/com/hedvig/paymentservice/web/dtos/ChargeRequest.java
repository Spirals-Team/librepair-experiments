package com.hedvig.paymentservice.web.dtos;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

import lombok.Value;

@Value
public class ChargeRequest {
    MonetaryAmount amount;
    CurrencyUnit currency;
    String email;
}
