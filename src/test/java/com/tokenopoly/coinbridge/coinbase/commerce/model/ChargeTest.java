/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 *
 */
public class ChargeTest {

    @SuppressWarnings("SimplifiableJUnitAssertion")
    @Test
    public void coverage() {
        final Charge c1 = new Charge();
        c1.setCode("ABC123RF");

        assertNotEquals(0, c1.hashCode());

        final Charge c2 = new Charge();
        c1.setCode(c1.getCode());

        assertEquals(c1, c2);
        assertTrue(c1.equals(c2));
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    @Test
    public void compareTo() {
        final Charge c1 = new Charge();

        final Date now = new Date();
        c1.setCreatedAt(now);
        c1.setExpiresAt(now);

        final Charge c2 = new Charge();

        assertTrue(c1.compareTo(c2) < 0);

        c1.setConfirmedAt(now);
        assertTrue(c1.compareTo(c2) < 0);

        c2.setConfirmedAt(now);
        assertTrue(c1.compareTo(c2) < 0);

        c2.setCreatedAt(now);
        c2.setExpiresAt(now);
        assertEquals(0, c1.compareTo(c2));

        c1.setCode("XYZ");
        assertTrue(c1.compareTo(c2) < 0);

        c2.setCode("ABC");
        assertFalse(c1.compareTo(c2) < 0);


        assertEquals(-1, c1.compareTo(null));
        final Charge c3 = c1;
        assertEquals(0, c1.compareTo(c3));

        assertNotEquals(c1, c2);



    }
}