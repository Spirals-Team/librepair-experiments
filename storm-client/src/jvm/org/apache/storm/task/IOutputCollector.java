/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.  The ASF licenses this file to you under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package org.apache.storm.task;

import java.util.Collection;
import java.util.List;
import org.apache.storm.tuple.Tuple;

public interface IOutputCollector extends IErrorReporter {
    /**
     * Returns the task ids that received the tuples.
     */
    List<Integer> emit(String streamId, Collection<Tuple> anchors, List<Object> tuple);

    void emitDirect(int taskId, String streamId, Collection<Tuple> anchors, List<Object> tuple);

    void ack(Tuple input);

    void fail(Tuple input);

    void resetTimeout(Tuple input);

    void flush();
}
