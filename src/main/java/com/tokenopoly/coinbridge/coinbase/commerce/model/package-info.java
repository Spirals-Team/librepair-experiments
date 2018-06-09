/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

/**
 * This package contains the domain model for Coinbase Commerce.
 * <p>
 *     The root of the model for handling a webhook call is the
 *     {@link com.tokenopoly.coinbridge.coinbase.commerce.model.Webhook} class.
 *     Nested within a {@code Webhook} is an {@link com.tokenopoly.coinbridge.coinbase.commerce.model.Event}.
 *     And, nested within an {@code Event} is a {@link com.tokenopoly.coinbridge.coinbase.commerce.model.Charge}.
 * </p>
 * <p>The {@code Charge} class is the root of the default persistence hierarchy.</p>
 */
package com.tokenopoly.coinbridge.coinbase.commerce.model;