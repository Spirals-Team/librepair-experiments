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

package com.alibaba.dubbo.config;

import com.alibaba.dubbo.common.Constants;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class MethodConfigTest {
    @Test
    public void testName() throws Exception {
        MethodConfig method = new MethodConfig();
        method.setName("hello");
        assertThat(method.getName(), equalTo("hello"));
        Map<String, String> parameters = new HashMap<String, String>();
        MethodConfig.appendParameters(parameters, method);
        assertThat(parameters, not(hasKey("name")));
    }

    @Test
    public void testStat() throws Exception {
        MethodConfig method = new MethodConfig();
        method.setStat(10);
        assertThat(method.getStat(), equalTo(10));
    }

    @Test
    public void testRetry() throws Exception {
        MethodConfig method = new MethodConfig();
        method.setRetry(true);
        assertThat(method.isRetry(), is(true));
    }

    @Test
    public void testReliable() throws Exception {
        MethodConfig method = new MethodConfig();
        method.setReliable(true);
        assertThat(method.isReliable(), is(true));
    }

    @Test
    public void testExecutes() throws Exception {
        MethodConfig method = new MethodConfig();
        method.setExecutes(10);
        assertThat(method.getExecutes(), equalTo(10));
    }

    @Test
    public void testDeprecated() throws Exception {
        MethodConfig method = new MethodConfig();
        method.setDeprecated(true);
        assertThat(method.getDeprecated(), is(true));
    }

    @Test
    public void testArguments() throws Exception {
        MethodConfig method = new MethodConfig();
        ArgumentConfig argument = new ArgumentConfig();
        method.setArguments(Collections.singletonList(argument));
        assertThat(method.getArguments(), contains(argument));
        assertThat(method.getArguments(), Matchers.<ArgumentConfig>hasSize(1));
    }

    @Test
    public void testSticky() throws Exception {
        MethodConfig method = new MethodConfig();
        method.setSticky(true);
        assertThat(method.getSticky(), is(true));
    }

    @Test
    public void testOnreturn() throws Exception {
        MethodConfig method = new MethodConfig();
        method.setOnreturn("on-return-object");
        assertThat(method.getOnreturn(), equalTo((Object) "on-return-object"));
        Map<Object, Object> attribute = new HashMap<Object, Object>();
        MethodConfig.appendAttributes(attribute, method);
        assertThat(attribute, hasEntry((Object) Constants.ON_RETURN_INSTANCE_KEY, (Object) "on-return-object"));
        Map<String, String> parameters = new HashMap<String, String>();
        MethodConfig.appendParameters(parameters, method);
        assertThat(parameters.size(), is(0));
    }

    @Test
    public void testOnreturnMethod() throws Exception {
        MethodConfig method = new MethodConfig();
        method.setOnreturnMethod("on-return-method");
        assertThat(method.getOnreturnMethod(), equalTo("on-return-method"));
        Map<Object, Object> attribute = new HashMap<Object, Object>();
        MethodConfig.appendAttributes(attribute, method);
        assertThat(attribute, hasEntry((Object) Constants.ON_RETURN_METHOD_KEY, (Object) "on-return-method"));
        Map<String, String> parameters = new HashMap<String, String>();
        MethodConfig.appendParameters(parameters, method);
        assertThat(parameters.size(), is(0));
    }

    @Test
    public void testOnthrow() throws Exception {
        MethodConfig method = new MethodConfig();
        method.setOnthrow("on-throw-object");
        assertThat(method.getOnthrow(), equalTo((Object) "on-throw-object"));
        Map<Object, Object> attribute = new HashMap<Object, Object>();
        MethodConfig.appendAttributes(attribute, method);
        assertThat(attribute, hasEntry((Object) Constants.ON_THROW_INSTANCE_KEY, (Object) "on-throw-object"));
        Map<String, String> parameters = new HashMap<String, String>();
        MethodConfig.appendParameters(parameters, method);
        assertThat(parameters.size(), is(0));
    }

    @Test
    public void testOnthrowMethod() throws Exception {
        MethodConfig method = new MethodConfig();
        method.setOnthrowMethod("on-throw-method");
        assertThat(method.getOnthrowMethod(), equalTo("on-throw-method"));
        Map<Object, Object> attribute = new HashMap<Object, Object>();
        MethodConfig.appendAttributes(attribute, method);
        assertThat(attribute, hasEntry((Object) Constants.ON_THROW_METHOD_KEY, (Object) "on-throw-method"));
        Map<String, String> parameters = new HashMap<String, String>();
        MethodConfig.appendParameters(parameters, method);
        assertThat(parameters.size(), is(0));
    }

    @Test
    public void testOninvoke() throws Exception {
        MethodConfig method = new MethodConfig();
        method.setOninvoke("on-invoke-object");
        assertThat(method.getOninvoke(), equalTo((Object) "on-invoke-object"));
        Map<Object, Object> attribute = new HashMap<Object, Object>();
        MethodConfig.appendAttributes(attribute, method);
        assertThat(attribute, hasEntry((Object) Constants.ON_INVOKE_INSTANCE_KEY, (Object) "on-invoke-object"));
        Map<String, String> parameters = new HashMap<String, String>();
        MethodConfig.appendParameters(parameters, method);
        assertThat(parameters.size(), is(0));
    }

    @Test
    public void testOninvokeMethod() throws Exception {
        MethodConfig method = new MethodConfig();
        method.setOninvokeMethod("on-invoke-method");
        assertThat(method.getOninvokeMethod(), equalTo("on-invoke-method"));
        Map<Object, Object> attribute = new HashMap<Object, Object>();
        MethodConfig.appendAttributes(attribute, method);
        assertThat(attribute, hasEntry((Object) Constants.ON_INVOKE_METHOD_KEY, (Object) "on-invoke-method"));
        Map<String, String> parameters = new HashMap<String, String>();
        MethodConfig.appendParameters(parameters, method);
        assertThat(parameters.size(), is(0));
    }

    @Test
    public void testReturn() throws Exception {
        MethodConfig method = new MethodConfig();
        method.setReturn(true);
        assertThat(method.isReturn(), is(true));
    }
}
