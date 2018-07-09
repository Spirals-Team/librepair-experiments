/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.jackrabbit.oak.plugins.document.rdb;

import java.io.PrintStream;
import java.util.List;

/**
 * A single item in a log generated by {@link RDBDataSourceWrapper}.
 */
public class RDBLogEntry {

    private final long start;
    private final long duration;
    private final String caller;
    private final String message;

    public RDBLogEntry(long start, String message) {
        this.start = start;
        this.duration = System.nanoTime() - this.start;
        this.message = message;
        String t = null;
        for (StackTraceElement ste : new Exception().getStackTrace()) {
            if (ste.getClassName().equals(RDBDocumentStore.class.getName())) {
                t = ste.getMethodName() + ":" + ste.getLineNumber();
            }
        }
        caller = t;
    }

    public String toString() {
        return String.format("%d %6d %s %s", this.start / 1000, this.duration / 1000, this.caller, this.message);
    }

    public static void DUMP(PrintStream out, List<RDBLogEntry> log) {
        if (log != null) {
            for (RDBLogEntry entry : log) {
                out.println(entry);
            }
        }
    }
}
