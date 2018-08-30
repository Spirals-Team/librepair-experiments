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

import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;
import com.github.rvesse.airline.annotations.OptionType;
import com.github.rvesse.airline.annotations.restrictions.MutuallyExclusiveWith;

@Command(name = "MutuallyExclusiveOptionsCommand")
public class MutuallyExclusiveOptions {

  @Option(type = OptionType.COMMAND, name = { "-verbose" })
  @MutuallyExclusiveWith(tag = "verbosity")
  private boolean verbose;

  @Option(type = OptionType.COMMAND, name = { "-quiet" })
  @MutuallyExclusiveWith(tag = "verbosity")
  private boolean quiet;
  
  @Option(type = OptionType.COMMAND, name = { "-all" })
  @MutuallyExclusiveWith(tag = "verbosity")
  private boolean all;
  
  @Option(type = OptionType.COMMAND, name = { "-other" })
  private boolean other;
}
