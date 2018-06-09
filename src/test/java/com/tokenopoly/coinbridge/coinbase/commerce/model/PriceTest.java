/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 *
 */
public class PriceTest {

    private Price price;

    @Before
    public void setUp() throws Exception {
        price = new Price(BigDecimal.valueOf(100), "USD");
    }

    @Test
    public void getSetTests() {
        assertEquals("USD", price.getCurrency());
        assertEquals(100, price.getAmount().longValue());

        final Price p2 = new Price();
        p2.setAmount(price.getAmount());
        p2.setCurrency(price.getCurrency());

        assertEquals(price, p2);
    }
}