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
package com.github.rvesse.airline;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * Factory for various channels used by airline.
 */
public interface ChannelFactory {

    /**
     * Returns output channel.
     *
     * @return output channel
     */
    PrintStream createOutput();

    /**
     * Returns error channel.
     *
     * @return error channel
     */
    PrintStream createError();

    /**
     * Returns input channel.
     *
     * @return input channel
     */
    InputStream createInput();
}
