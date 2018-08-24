/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.qos.command.impl;

import org.apache.dubbo.qos.command.CommandContext;
import org.apache.dubbo.qos.common.QosConstants;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class QuitTest {
    @Test
    public void testExecute() throws Exception {
        Quit quit = new Quit();
        String output = quit.execute(Mockito.mock(CommandContext.class), null);
        assertThat(output, equalTo(QosConstants.CLOSE));
    }
}
