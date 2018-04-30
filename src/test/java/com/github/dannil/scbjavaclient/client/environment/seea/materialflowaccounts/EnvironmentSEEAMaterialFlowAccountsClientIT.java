/*
 * Copyright 2017 Daniel Nilsson
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.github.dannil.scbjavaclient.client.environment.seea.materialflowaccounts;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.github.dannil.scbjavaclient.test.extensions.Date;
import com.github.dannil.scbjavaclient.test.extensions.Remote;
import com.github.dannil.scbjavaclient.test.extensions.Suite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Suite
@Remote
public class EnvironmentSEEAMaterialFlowAccountsClientIT {

    private EnvironmentSEEAMaterialFlowAccountsClient client;

    @BeforeEach
    public void setup() {
        this.client = new EnvironmentSEEAMaterialFlowAccountsClient();
    }

    @Test
    @Date("2017-05-29")
    public void getMaterialFlows() {
        assertNotEquals(0, this.client.getMaterialFlows().size());
    }

    @Test
    @Date("2017-05-29")
    public void getMaterialFlowsWithParametersEmptyLists() {
        assertNotEquals(0,
                this.client.getMaterialFlows(Collections.<String>emptyList(), Collections.<Integer>emptyList()).size());
    }

    @Test
    @Date("2017-05-29")
    public void getMaterialFlowsWithParameters() {
        List<String> categories = Arrays.asList("1.2.1", "1.2.2");
        List<Integer> years = Arrays.asList(2010, 2011);

        assertNotEquals(0, this.client.getMaterialFlows(categories, years).size());
    }

}
