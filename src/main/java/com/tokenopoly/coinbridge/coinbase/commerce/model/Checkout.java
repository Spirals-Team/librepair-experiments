/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Nonnull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Models a Checkout to handle the case where a {@link Charge} was created from one.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@SuppressWarnings("WeakerAccess")
public class Checkout implements Serializable {

    private static final long serialVersionUID = 4136748974630732575L;

    @Nonnull
    private String id;

    private String name;

    private String description;

    private String logoUrl;

    private List<String> requestedInfo;

    private String pricingType;

    private Price localPrice;
}
