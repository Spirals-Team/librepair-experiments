/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014 Red Hat Inc. and/or its affiliates and other contributors
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
package org.aesh.readline;

import org.aesh.readline.terminal.Key;
import org.aesh.readline.tty.terminal.TestConnection;
import org.aesh.readline.util.Parser;
import org.junit.Test;

/**
 * @author <a href=mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public class MaskingReadlineTest {

    @Test
    public void maskingTest() {
        TestConnection term = new TestConnection(new Prompt("", '\u0000'));

        term.read("MMyPassword");
        term.assertOutputBuffer("");
        term.read(Key.BACKSPACE);
        term.read(Key.ENTER);
        term.assertLine("MMyPasswor");

        term = new TestConnection(new Prompt(Parser.toCodePoints("[foo] "), '%'));
        term.read("MMyPassword");
        term.assertOutputBuffer("[foo] %%%%%%%%%%%");
        term.clearOutputBuffer();
        term.read(Key.BACKSPACE);
        term.read(Key.ENTER);
        term.assertLine("MMyPasswor");
    }
}
