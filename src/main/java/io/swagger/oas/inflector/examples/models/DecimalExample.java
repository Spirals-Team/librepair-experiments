/*
 *  Copyright 2017 SmartBear Software
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.swagger.oas.inflector.examples.models;

import java.math.BigDecimal;

public class DecimalExample extends AbstractExample {
    private BigDecimal value;

    public DecimalExample() {
        super.setTypeName("decimal");
    }
    public DecimalExample(BigDecimal value) {
        this();
        this.value = value;
    }

    public String asString() {
        return value.toPlainString();
    }

    public BigDecimal getValue() {
        return value != null ? value : new BigDecimal(1.23);
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
