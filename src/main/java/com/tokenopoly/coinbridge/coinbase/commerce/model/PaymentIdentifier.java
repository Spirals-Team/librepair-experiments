/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import com.google.common.collect.ComparisonChain;

import com.tokenopoly.util.CompareUtils;

import java.util.Comparator;

/**
 * This interface allows us to generalize the information needed to uniquely identify a payment.
 */
public interface PaymentIdentifier extends Comparable<PaymentIdentifier> {

    Comparator<PaymentIdentifier> PaymentIdentifierNaturalNullsLast =
        Comparator.nullsLast(Comparator.naturalOrder());
    
    String getNetwork();

    String getTransactionId();

    @SuppressWarnings("NullableProblems")
    @Override
    default int compareTo(PaymentIdentifier o) {
        if (o == null) {
            return -1;
        } else if (this == o) {
            return 0;
        } else {
            return ComparisonChain.start()
                .compare(this.getNetwork(), o.getNetwork(), CompareUtils.StringNaturalNullsLast)
                .compare(this.getTransactionId(), o.getTransactionId(), CompareUtils.StringNaturalNullsLast)
                .result();
        }
    }

}
