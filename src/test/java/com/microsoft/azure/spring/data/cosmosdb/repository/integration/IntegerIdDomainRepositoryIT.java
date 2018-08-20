/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure.spring.data.cosmosdb.repository.integration;

import com.microsoft.azure.spring.data.cosmosdb.domain.IntegerIdDomain;
import com.microsoft.azure.spring.data.cosmosdb.repository.repository.IntegerIdDomainRepository;
import com.microsoft.azure.spring.data.cosmosdb.repository.TestRepositoryConfig;
import com.microsoft.azure.spring.data.cosmosdb.repository.support.DocumentDbEntityInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
public class IntegerIdDomainRepositoryIT {

    private static final Integer ID = 231234;
    private static final String NAME = "panli";
    private static final IntegerIdDomain DOMAIN = new IntegerIdDomain(ID, NAME);

    @Autowired
    private IntegerIdDomainRepository repository;

    @Before
    public void setup() {
        this.repository.save(DOMAIN);
    }

    @After
    public void cleanup() {
        this.repository.deleteAll();
    }

    @Test
    public void testIntegerIdDomain() {
        this.repository.deleteAll();
        Assert.assertFalse(this.repository.findById(ID).isPresent());

        this.repository.save(DOMAIN);
        final Optional<IntegerIdDomain> foundOptional = this.repository.findById(ID);

        Assert.assertTrue(foundOptional.isPresent());
        Assert.assertEquals(foundOptional.get().getNumber(), DOMAIN.getNumber());
        Assert.assertEquals(foundOptional.get().getName(), DOMAIN.getName());

        this.repository.delete(DOMAIN);

        Assert.assertFalse(this.repository.findById(ID).isPresent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDomain() {
        new DocumentDbEntityInformation<InvalidDomain, Integer>(InvalidDomain.class);
    }

    @AllArgsConstructor
    @Data
    private static class InvalidDomain {

        private int count;

        private String location;
    }
}
