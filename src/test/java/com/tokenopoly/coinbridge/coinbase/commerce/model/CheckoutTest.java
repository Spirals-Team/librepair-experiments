/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

/**
 * gratuitous coverage test
 */
public class CheckoutTest {

    private Checkout checkingThisOut;

    @Before
    public void setUp() {
        checkingThisOut = new Checkout();
        checkingThisOut.setId("ABC123");
        checkingThisOut.setName("Test");
        checkingThisOut.setDescription("Test coverage");
        checkingThisOut.setLogoUrl(null);
        checkingThisOut.setRequestedInfo(Collections.singletonList("email"));
        checkingThisOut.setPricingType("no_price");
        checkingThisOut.setLocalPrice(null);
    }

    @Test
    public void canEqual() {
        assertTrue(checkingThisOut.canEqual(new Checkout()));
        assertFalse(checkingThisOut.canEqual(new Price()));
    }

    @Test
    public void getTests() {
        assertEquals("ABC123", checkingThisOut.getId());
        assertEquals("Test", checkingThisOut.getName());
        assertEquals("Test coverage", checkingThisOut.getDescription());
        assertNull(checkingThisOut.getLogoUrl());
        assertTrue(checkingThisOut.getRequestedInfo().contains("email"));
        assertEquals("no_price", checkingThisOut.getPricingType());
        assertNull(checkingThisOut.getLocalPrice());

        final Checkout c2 = new Checkout();

        c2.setId(checkingThisOut.getId());
        assertNotSame(checkingThisOut, c2);
        assertEquals(checkingThisOut, c2);

    }
    
}