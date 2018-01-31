/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.nifi.toolkit.cli.command.session;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang3.StringUtils;
import org.apache.nifi.toolkit.cli.SessionVariable;
import org.apache.nifi.toolkit.cli.command.AbstractCommand;
import org.apache.nifi.toolkit.cli.command.CommandException;

import java.util.Properties;

/**
 * Sets a variable in the session.
 */
public class SetVariable extends AbstractCommand {

    public static final String NAME = "set";

    public SetVariable() {
        super(NAME);
    }

    @Override
    public void execute(final CommandLine commandLine) throws CommandException {
        final String[] args = commandLine.getArgs();

        if (args == null || args.length < 2 || StringUtils.isBlank(args[0]) || StringUtils.isBlank(args[1])) {
            throw new CommandException("Incorrect number of arguments, should be: <var> <value>");
        }

        final SessionVariable variable = SessionVariable.fromVariableName(args[0]);
        if (variable == null) {
            throw new CommandException("Unknown variable '" + args[0] + "'");
        }

        getContext().getSession().set(variable, args[1]);
    }

    @Override
    protected void doExecute(Properties properties) throws CommandException {
        // nothing to do since we are overriding regular execute
    }
}
