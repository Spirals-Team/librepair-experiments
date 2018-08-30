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
package com.github.rvesse.airline.examples.simple;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.rvesse.airline.annotations.Arguments;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.restrictions.Required;
import com.github.rvesse.airline.examples.ExampleExecutor;
import com.github.rvesse.airline.examples.ExampleRunnable;

/**
 * An example command that has required arguments
 *
 */
@Command(name = "req-args", description = "A command with required arguments")
public class RequiredArguments implements ExampleRunnable {

    @Arguments
    @Required
    private List<String> args = new ArrayList<String>();

    public static void main(String[] args) {
        ExampleExecutor.executeSingleCommand(RequiredArguments.class, args);
    }

    @Override
    public int run() {
        System.out.println("Arguments given were " + StringUtils.join(args, ", "));
        return 0;
    }
}
