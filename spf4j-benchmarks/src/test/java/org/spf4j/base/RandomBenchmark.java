/*
 * Copyright (c) 2001-2017, Zoltan Farkas All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * Additionally licensed with:
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.spf4j.base;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
//import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;

/**
 *
 * @author zoly
 */
@State(Scope.Benchmark)
@Fork(2)
@Threads(value = 8)
public class RandomBenchmark {

  private static final IntMath.XorShift32ThreadSafe RND = new IntMath.XorShift32ThreadSafe();

  @State(Scope.Thread)
  public static class ThreadState {

    final IntMath.XorShift32 rnd = new IntMath.XorShift32();

  }

  @State(Scope.Thread)
  public static class ThreadState2 {

    final ThreadLocalRandom rnd = ThreadLocalRandom.current();

  }


  //@Benchmark
  public int testSpf4jRandomThreadLocal() throws IOException {
    return RND.nextInt();
  }

  //@Benchmark
  public int testSpf4jRandomLocal(ThreadState ts) throws IOException {
    return ts.rnd.nextInt();
  }

  //@Benchmark
  public int testJdkRandomLocal(ThreadState2 ts) throws IOException {
    return ts.rnd.nextInt();
  }

  //@Benchmark
  public int testJdkRandomThreadLocal() throws IOException {
    return ThreadLocalRandom.current().nextInt();
  }


}
