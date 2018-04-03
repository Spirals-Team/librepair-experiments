/*
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
package com.facebook.presto.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class FileBasedSystemAccessControlRules
{
    private final List<CatalogAccessControlRule> catalogRules;
    private final List<Pattern> userPatterns;

    @JsonCreator
    public FileBasedSystemAccessControlRules(
            @JsonProperty("catalogs") Optional<List<CatalogAccessControlRule>> catalogRules,
            @JsonProperty("user_patterns") Optional<List<Pattern>> userPatterns)
    {
        this.catalogRules = catalogRules.map(ImmutableList::copyOf).orElse(ImmutableList.of());
        this.userPatterns = userPatterns.map(ImmutableList::copyOf).orElse(ImmutableList.of());
    }

    public List<CatalogAccessControlRule> getCatalogRules()
    {
        return catalogRules;
    }

    public List<Pattern> getUserPatterns()
    {
        return userPatterns;
    }
}
