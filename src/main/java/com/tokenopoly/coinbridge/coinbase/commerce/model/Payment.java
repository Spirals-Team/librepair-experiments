/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SelectBeforeUpdate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"network", "transactionId"})

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)

@Entity
@IdClass(PaymentPK.class)
@Table(schema = "coinbase")
@DynamicUpdate
@SelectBeforeUpdate

@SuppressWarnings({"unused", "WeakerAccess"})
public class Payment implements PaymentIdentifier, Serializable {

    private static final long serialVersionUID = -5853451019519546806L;

    public static final String LOCAL_VALUE_KEY = "local";
    public static final String CRYPTO_VALUE_KEY = "crypto";

    @Builder
    public Payment(PaymentPK paymentId, Status.StatusValue status, Map<String, Price> value, Block block) {
        this.network = paymentId == null ? null : paymentId.getNetwork();
        this.transactionId = paymentId == null ? null : paymentId.getTransactionId();
        this.status = status;
        this.value = (value == null) ? new HashMap<>() : value;
        this.block = block;
    }

    @Id
    private String network;

    @Id
    private String transactionId;

    @Enumerated(EnumType.STRING)
    private Status.StatusValue status;

    @Fetch(FetchMode.JOIN)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "payment_values", schema = "coinbase"
        , joinColumns = {@JoinColumn(name = "network"), @JoinColumn(name = "transaction_id")}
    )
    @MapKeyColumn(name = "type")
    private Map<String, Price> value;

    @Embedded
    private Block block;

    @Transient
    protected Price getPriceForKey(final String key) {
        return (value != null && value.containsKey(key))
               ? value.get(key)
               : null;
    }

    @Transient
    public Price getLocalPrice() {
        return getPriceForKey(LOCAL_VALUE_KEY);
    }

    @Transient
    public Price getCryptoPrice() {
        return getPriceForKey(CRYPTO_VALUE_KEY);
    }
    
}
