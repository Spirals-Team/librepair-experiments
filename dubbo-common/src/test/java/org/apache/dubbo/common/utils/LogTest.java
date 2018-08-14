/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dubbo.common.utils;

import org.apache.log4j.Level;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LogTest {
    @Test
    public void testLogName() throws Exception {
        Log log = new Log();
        log.setLogName("log-name");
        assertThat(log.getLogName(), equalTo("log-name"));
    }

    @Test
    public void testLogLevel() throws Exception {
        Log log = new Log();
        log.setLogLevel(Level.ALL);
        assertThat(log.getLogLevel(), is(Level.ALL));
    }

    @Test
    public void testLogMessage() throws Exception {
        Log log = new Log();
        log.setLogMessage("log-message");
        assertThat(log.getLogMessage(), equalTo("log-message"));
    }

    @Test
    public void testLogThread() throws Exception {
        Log log = new Log();
        log.setLogThread("log-thread");
        assertThat(log.getLogThread(), equalTo("log-thread"));
    }

}
