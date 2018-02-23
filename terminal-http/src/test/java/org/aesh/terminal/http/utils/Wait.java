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
package org.aesh.terminal.http.utils;

import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

/**
 * @author <a href="mailto:matejonnet@gmail.com">Matej Lazar</a>
 */
public class Wait {

    public static void forCondition(Supplier<Boolean> evaluationSupplier, long timeout, TemporalUnit timeUnit) throws InterruptedException, TimeoutException {
        forCondition(evaluationSupplier, timeout, timeUnit, "");
    }

    public static void forCondition(Supplier<Boolean> evaluationSupplier, long timeout, TemporalUnit timeUnit, String failedMessage) throws InterruptedException, TimeoutException {
        LocalDateTime started = LocalDateTime.now();
        while (true) {
            if (started.plus(timeout, timeUnit).isBefore(LocalDateTime.now())) {
                throw new TimeoutException(failedMessage + " Reached timeout " + timeout + " " + timeUnit);
            }
            if (evaluationSupplier.get()) {
                break;
            } else {
                Thread.sleep(100);
            }
        }
    }


}
