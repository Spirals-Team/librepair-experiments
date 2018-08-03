/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.  The ASF licenses this file to you under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package org.apache.storm.metrics2.filters;

import com.codahale.metrics.Metric;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexFilter implements StormMetricsFilter {

    private Pattern pattern;


    @Override
    public void prepare(Map<String, Object> config) {
        String expression = (String) config.get("expression");
        if (expression != null) {
            this.pattern = Pattern.compile(expression);
        } else {
            throw new IllegalStateException("RegexFilter requires an 'expression' parameter.");
        }
    }

    @Override
    public boolean matches(String name, Metric metric) {
        Matcher matcher = this.pattern.matcher(name);
        return matcher.matches();
    }

}
