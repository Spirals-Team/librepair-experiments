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

import org.aesh.terminal.Device;
import org.aesh.terminal.tty.Capability;

import java.util.function.Consumer;

/**
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public class TelnetDevice implements Device {

    private final String type;

    public TelnetDevice(String terminalType) {
        type = terminalType;
    }

    @Override
    public String type() {
        return type;
    }

    @Override
    public boolean getBooleanCapability(Capability capability) {
        return false;
    }

    @Override
    public Integer getNumericCapability(Capability capability) {
        return null;
    }

    @Override
    public String getStringCapability(Capability capability) {
        return null;
    }

    @Override
    public int[] getStringCapabilityAsInts(Capability capability) {
        return new int[0];
    }

    @Override
    public boolean puts(Consumer<int[]> output, Capability capability) {
        return false;
    }
}
