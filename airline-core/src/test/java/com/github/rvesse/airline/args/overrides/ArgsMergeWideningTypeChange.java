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
package com.github.rvesse.airline.args.overrides;

import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;

@Command(name = "ArgsMergeWideningTypeChange")
public class ArgsMergeWideningTypeChange extends ArgsMergeTypeParent {

    /**
     * This is an illegal override as it involves a widening type conversion
     */
    @Option(name = "--test", arity = 1, override = true)
    public A test;
}
