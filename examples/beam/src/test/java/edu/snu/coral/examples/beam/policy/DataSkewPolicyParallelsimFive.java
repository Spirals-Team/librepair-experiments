/*
 * Copyright (C) 2017 Seoul National University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.snu.coral.examples.beam.policy;

import edu.snu.coral.compiler.optimizer.pass.compiletime.CompileTimePass;
import edu.snu.coral.compiler.optimizer.policy.DataSkewPolicy;
import edu.snu.coral.compiler.optimizer.policy.Policy;
import edu.snu.coral.runtime.common.optimizer.pass.runtime.RuntimePass;

import java.util.List;

/**
 * A data-skew policy with fixed parallelism 5 for tests.
 */
public final class DataSkewPolicyParallelsimFive implements Policy {
  private final Policy policy;

  public DataSkewPolicyParallelsimFive() {
    this.policy = PolicyTestUtil.overwriteParallelism(5, DataSkewPolicy.class.getCanonicalName());
  }

  @Override
  public List<CompileTimePass> getCompileTimePasses() {
    return this.policy.getCompileTimePasses();
  }

  @Override
  public List<RuntimePass<?>> getRuntimePasses() {
    return this.policy.getRuntimePasses();
  }
}
