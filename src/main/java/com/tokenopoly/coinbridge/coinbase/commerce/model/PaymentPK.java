/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The concrete implementation of {@link PaymentIdentifier} for persistence purposes.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)

@Embeddable
public class PaymentPK implements PaymentIdentifier, Serializable {

    private static final long serialVersionUID = 6917567371063229215L;

    private String network;
    private String transactionId;

    @SuppressWarnings("unused")
    public static PaymentPK newPaymentId(final PaymentIdentifier payment) {
        return new PaymentPK(payment.getNetwork(), payment.getTransactionId());
    }

}
