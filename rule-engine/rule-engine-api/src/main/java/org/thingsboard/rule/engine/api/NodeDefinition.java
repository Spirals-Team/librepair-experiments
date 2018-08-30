/**
 * Copyright © 2016-2018 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.thingsboard.rule.engine.api;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class NodeDefinition {

    private String details;
    private String description;
    private boolean inEnabled;
    private boolean outEnabled;
    String[] relationTypes;
    boolean customRelations;
    JsonNode defaultConfiguration;
    String[] uiResources;
    String configDirective;
    String icon;
    String iconUrl;
    String docUrl;

}
