/**
 * Copyright (C) 2010-16 the original author or authors.
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
package com.github.rvesse.airline.parser.errors;

import java.util.Set;

/**
 * Exception that occurs when alias chaining is enabled and a circular reference
 * is encountered
 *
 */
public class ParseAliasCircularReferenceException extends ParseException {
    private static final long serialVersionUID = 1982540121928126507L;
    
    private final String offendingAlias;
    private final Set<String> referenceChain;

    public ParseAliasCircularReferenceException(String alias, Set<String> referenceChain) {
        super("Circular alias reference detected, aliases chain %s references %s which was already resolved",
                referenceChain, alias);
        this.offendingAlias = alias;
        this.referenceChain = referenceChain;
    }

    /**
     * Gets the alias that was encountered that is a circular reference
     * 
     * @return Offending alias
     */
    public String getOffendingAlias() {
        return this.offendingAlias;
    }

    /**
     * Gets the chain of alias resolutions that led to the circular reference
     * 
     * @return Alias chain
     */
    public Set<String> getAliasChain() {
        return this.referenceChain;
    }
}
