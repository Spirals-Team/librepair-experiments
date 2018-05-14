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

package com.alibaba.com.caucho.hessian.io.java8;


import com.alibaba.com.caucho.hessian.io.HessianHandle;

import java.io.Serializable;
import java.lang.reflect.Method;

@SuppressWarnings("unchecked")
public class LocalTimeHandle implements HessianHandle, Serializable {
    private static final long serialVersionUID = -5892919085390462315L;

    private int hour;
    private int minute;
    private int second;
    private int nano;

    public LocalTimeHandle() {
    }

    public LocalTimeHandle(Object o) {
        try {
            Class c = Class.forName("java.time.LocalTime");
            Method m = c.getDeclaredMethod("getHour");
            this.hour = (Integer) m.invoke(o);
            m = c.getDeclaredMethod("getMinute");
            this.minute = (Integer) m.invoke(o);
            m = c.getDeclaredMethod("getSecond");
            this.second = (Integer) m.invoke(o);
            m = c.getDeclaredMethod("getNano");
            this.nano = (Integer) m.invoke(o);
        } catch (Throwable t) {
            // ignore
        }
    }

    private Object readResolve() {
        try {
            Class c = Class.forName("java.time.LocalTime");
            Method m = c.getDeclaredMethod("of", int.class, int.class, int.class, int.class);
            return m.invoke(null, hour, minute, second, nano);
        } catch (Throwable t) {
            // ignore
        }
        return null;
    }
}
