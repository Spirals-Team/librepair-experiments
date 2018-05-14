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
package com.alibaba.dubbo.common.serialize.serialization;

import com.alibaba.dubbo.common.serialize.kryo.utils.ReflectionUtils;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ReflectionUtilsTest {

    @Test
    public void test() {
        assertTrue(ReflectionUtils.checkZeroArgConstructor(String.class));
        assertTrue(ReflectionUtils.checkZeroArgConstructor(Bar.class));
        assertFalse(ReflectionUtils.checkZeroArgConstructor(Foo.class));
    }

    static class Foo {
        public Foo(int i) {

        }
    }

    static class Bar {
        private Bar() {

        }
    }
}
