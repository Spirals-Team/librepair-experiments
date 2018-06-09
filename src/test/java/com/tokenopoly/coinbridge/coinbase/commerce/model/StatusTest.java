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
public class StatusTest {

    @Test
    public void compareTo() {
        final Status s1 = new Status();
        final Status s2 = new Status();

        assertNotSame(s1, s2);

        final Status s3 = s1;
        assertEquals(-1, s1.compareTo(null));
        assertEquals(0, s1.compareTo(s3));

        final Date now = new Date();
        s1.setTime(now);
        s2.setTime(now);

        s1.setStatus(Status.StatusValue.NEW);
        s2.setStatus(Status.StatusValue.NEW);

        assertNotSame(s1, s2);
        assertEquals(s1, s2);
        assertEquals(0, s1.compareTo(s2));

        s2.setStatus(Status.StatusValue.COMPLETED);
        assertNotEquals(s1, s2);
        assertNotEquals(0, s1.compareTo(s2));

        s1.setStatus(Status.StatusValue.COMPLETED);

        final PaymentPK p1 = new PaymentPK("bitcoin", "afaketransactionnonid");
        s1.setPayment(p1);

        assertNotEquals(s1, s2);
        assertTrue(s1.compareTo(s2) < 0);  // Nulls last

        s2.setPayment(p1);

        assertEquals(s1, s2);
        assertEquals(0, s1.compareTo(s2));

        s2.setPayment(new PaymentPK("bitcoin", "anothervalue"));
        assertNotEquals(s1, s2);
        assertNotEquals(0, s1.compareTo(s2));



    }
}