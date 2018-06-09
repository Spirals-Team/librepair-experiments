/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Nonnull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The entrance-point object for handling Coinbase Commerce webhook callbacks.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Webhook implements Serializable {

    private static final long serialVersionUID = -367624435630683620L;

    @Nonnull
    private String id;

    @Nonnull
    private Date scheduledFor;

    /**
     * Coinbase keeps repeatedly tries to deliver the {@link Event}, incrementing this counter
     * with each attempt.
     */
    private Integer attemptNumber;

    /**
     * The primary payload of this webhook callback.
     */
    private Event event;

    public Webhook(@Nonnull String id, @Nonnull Date scheduledFor) {
        this.id = id;
        this.scheduledFor = scheduledFor;
    }

}
