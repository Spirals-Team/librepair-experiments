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

package io.swagger.oas.inflector.utils;


import io.swagger.oas.inflector.Constants;
import io.swagger.v3.core.filter.OpenAPISpecFilter;
import io.swagger.v3.core.filter.SpecFilter;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Schema;

import java.util.List;
import java.util.Map;

public class VendorSpecFilter extends SpecFilter {

    @Override
    public Map<String, Schema> filterComponentsSchema(OpenAPISpecFilter filter, Map<String, Schema> definitions,
                                                      Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        final Map<String, Schema> filteredDefinitions = super.filterComponentsSchema(filter, definitions, params, cookies,
                headers);

        if( filteredDefinitions != null ) {
            for (Schema model : filteredDefinitions.values()) {
                if(model != null && model.getExtensions() != null) {
                    filterVendorExtensions(model.getExtensions());
                }
            }
        }

        return filteredDefinitions;
    }

    @Override
    public Operation filterOperation(OpenAPISpecFilter filter, Operation op, String path, String key ,
                                     Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        final Operation operation = super.filterOperation(filter, op, path, key, params, cookies, headers);

        if(operation != null) {
            if (operation.getExtensions() != null) {
                filterVendorExtensions(operation.getExtensions());
            }
        }

        return operation;
    }

    private void filterVendorExtensions(Map<String, Object> vendorExtensions) {
        for (Constants.VendorExtension vendorExtension : Constants.VendorExtension.values()) {
            vendorExtensions.remove(vendorExtension.getValue());
        }
    }
}
