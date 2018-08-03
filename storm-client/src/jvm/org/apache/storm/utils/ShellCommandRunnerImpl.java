/*
 * Copyright 2017 The Apache Software Foundation.
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

package org.apache.storm.utils;

import java.io.IOException;
import java.util.Map;

public class ShellCommandRunnerImpl implements ShellCommandRunner {

    @Override
    public String execCommand(String... cmd) throws IOException {
        return execCommand(null, cmd, 0L);
    }

    @Override
    public String execCommand(Map<String, String> env, String[] cmd, long timeout) throws IOException {
        ShellUtils.ShellCommandExecutor exec = new ShellUtils.ShellCommandExecutor(cmd, null, env,
                                                                                   timeout);
        exec.execute();
        return exec.getOutput();
    }

    @Override
    public String execCommand(Map<String, String> env, String... cmd) throws IOException {
        return execCommand(env, cmd, 0L);
    }

    @Override
    public String getTokenSeparatorRegex() {
        return ShellUtils.TOKEN_SEPARATOR_REGEX;
    }
}
