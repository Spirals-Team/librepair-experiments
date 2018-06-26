/*
 * JBoss, Home of Professional Open Source
 * Copyright 2017 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
package org.aesh.terminal.telnet;

import org.aesh.terminal.TestBase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ReadBufferTest extends TestBase {

  private ArrayBlockingQueue<Runnable> commands;
  private ReadBuffer buf;
  private ArrayList<int[]> reads;

  @Before
  public void setUp() {
    commands = new ArrayBlockingQueue<>(1);
    buf = new ReadBuffer(new Executor() {
      @Override
      public void execute(Runnable command) {
        commands.add(command);
      }
    });
    reads = new ArrayList<>();
  }

  @Test
  public void testFoo() {
    final ArrayList<int[]> reads = new ArrayList<>();
    buf.setReadHandler(event -> reads.add(event));
    buf.accept(new int[]{'f', 'o', 'o'});
    assertEquals(0, commands.size());
    assertEquals(1, reads.size());
    assertEquals(reads.get(0), new int[]{'f','o','o'});
  }

  @Test
  public void testBar() throws Exception {
    buf.accept(new int[]{'f', 'o', 'o'});
    assertEquals(0, reads.size());
    assertEquals(0, commands.size());
    buf.setReadHandler(event -> reads.add(event));
    assertEquals(0, reads.size());
    assertEquals(1, commands.size());
    commands.poll().run();
    assertEquals(1, reads.size());
    assertEquals(0, commands.size());
    assertEquals(reads.get(0), new int[]{'f','o','o'});
  }

  @Test
  public void testJuu() throws Exception {
    buf.accept(new int[]{'f', 'o', 'o'});
    buf.accept(new int[]{'b', 'a', 'r'});
    assertEquals(0, reads.size());
    assertEquals(0, commands.size());
    buf.setReadHandler(event -> {
      reads.add(event);
      buf.setReadHandler(null);
    });
    assertEquals(0, reads.size());
    assertEquals(1, commands.size());
    commands.poll().run();
    assertEquals(1, reads.size());
    assertEquals(0, commands.size());
    assertEquals(reads.get(0), new int[]{'f', 'o', 'o'});
    assertEquals(null, buf.getReadHandler());
  }

  @Test
  public void testBilto() throws Exception {
    buf.accept(new int[]{'f', 'o', 'o'});
    buf.accept(new int[]{'b', 'a', 'r'});
    assertEquals(0, reads.size());
    assertEquals(0, commands.size());
    buf.setReadHandler(event -> reads.add(event));
    assertEquals(0, reads.size());
    assertEquals(1, commands.size());
    commands.poll().run();
    assertEquals(1, reads.size());
    assertEquals(1, commands.size());
    assertEquals(reads.get(0), new int[]{'f', 'o', 'o'});
    buf.accept(new int[]{'j', 'u', 'u'});
    assertEquals(3, reads.size());
    assertEquals(1, commands.size());
    assertEquals(reads.get(1), new int[]{'b', 'a', 'r'});
    assertEquals(reads.get(2), new int[]{'j', 'u', 'u'});
    commands.poll().run();
    assertEquals(3, reads.size());
    assertEquals(0, commands.size());
  }

}
