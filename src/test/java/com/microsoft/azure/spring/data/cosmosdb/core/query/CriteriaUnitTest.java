/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure.spring.data.cosmosdb.core.query;

import com.microsoft.azure.spring.data.cosmosdb.common.TestConstants;
import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class CriteriaUnitTest {

    @Test
    public void testSimpleCriteria() {
        final Criteria c = new Criteria(new ArrayList<>(), TestConstants.CRITERIA_KEY);
        c.is(TestConstants.CRITERIA_OBJECT);

        assertThat(c.getKey()).isEqualTo(TestConstants.CRITERIA_KEY);
        assertThat(c.getCriteriaObject()).isEqualTo(TestConstants.CRITERIA_OBJECT);
    }
}
