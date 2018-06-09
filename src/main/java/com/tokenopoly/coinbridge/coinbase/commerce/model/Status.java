/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import com.google.common.collect.ComparisonChain;

import com.tokenopoly.util.CompareUtils;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import javax.annotation.Nonnull;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Embeddable
@SuppressWarnings("unused")
public class Status implements Serializable, Comparable<Status> {
    private static final long serialVersionUID = 656845959161022248L;

    public static final Comparator<StatusValue> StatusNaturalNullsLast =
        Comparator.nullsLast(Comparator.naturalOrder());
    public static final Comparator<ContextValue> ContextNaturalNullsLast =
        Comparator.nullsLast(Comparator.naturalOrder());

    @Nonnull
    private Date time;
    @Nonnull
    @Enumerated(EnumType.STRING)
    private StatusValue status;
    @Enumerated(EnumType.STRING)
    private ContextValue context;

    private PaymentPK payment;

    @SuppressWarnings("NullableProblems")
    @Override
    public int compareTo(@NotNull Status o) {
        if (o == null) {
            return -1;
        } else if (this == o) {
            return 0;
        } else {
            return ComparisonChain.start()
                .compare(this.time, o.time, CompareUtils.DateNaturalNullsLast)
                .compare(this.status, o.status, StatusNaturalNullsLast)
                .compare(this.context, o.context, ContextNaturalNullsLast)
                .compare(this.payment, o.payment, PaymentIdentifier.PaymentIdentifierNaturalNullsLast)
                .result();
        }
    }

    public enum StatusValue {
        NEW,
        PENDING,
        CONFIRMED,
        COMPLETED,
        EXPIRED,
        UNRESOLVED,
        RESOLVED
    }


    public enum ContextValue {
        UNDERPAID, OVERPAID, DELAYED, MULTIPLE, MANUAL, OTHER
    }
}
