/**
 * Copyright 2014 Flipkart Internet Pvt. Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flipkart.foxtrot.common.query.numeric;

import com.flipkart.foxtrot.common.query.FilterOperator;
import com.flipkart.foxtrot.common.query.FilterVisitor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * User: Santanu Sinha (santanu.sinha@flipkart.com)
 * Date: 14/03/14
 * Time: 8:17 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LessThanFilter extends NumericBinaryFilter {

    public LessThanFilter() {
        super(FilterOperator.less_than);
    }

    @Builder
    public LessThanFilter(String field, Number value, boolean temporal) {
        super(FilterOperator.less_than, field, value, temporal);
    }

    @Override
    public<T> T accept(FilterVisitor<T> visitor) throws Exception {
        return visitor.visit(this);
    }
}
