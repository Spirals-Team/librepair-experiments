/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.dao;

import com.tokenopoly.coinbridge.coinbase.DefaultTestApp;
import com.tokenopoly.coinbridge.coinbase.commerce.model.Charge;
import com.tokenopoly.coinbridge.coinbase.commerce.model.Webhook;
import com.tokenopoly.coinbridge.coinbase.commerce.model.WebhookTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 *
 */
@RunWith(SpringRunner.class)
@ActiveProfiles({"test"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = DefaultTestApp.class)
public class ChargeDaoTest {

    @Autowired
    private ChargeDao dao;

    private WebhookTest webhookTest = new WebhookTest();

    @Test
    public void testPersistFromWebhook() {
        Webhook webhook = webhookTest.deserializeWebhookPayload();
        Charge c = dao.saveAndFlush(webhook.getEvent().getData());

        assertEquals("ABC123PQ", c.getCode());
        assertEquals(1, c.getPayments().size());
    }
}
