/*
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
package com.facebook.presto.verifier;

import io.airlift.airline.Cli;
import io.airlift.airline.Help;

public class PrestoVerifier
{
    private PrestoVerifier()
    {
    }

    public static void main(String[] args)
            throws Exception
    {
        Cli<Runnable> verifierParser = Cli.<Runnable>builder("verifier")
                .withDescription("Presto Verifier")
                .withDefaultCommand(Help.class)
                .withCommand(Help.class)
                .withCommand(VerifyCommand.class)
                .build();

        verifierParser.parse(args).run();
    }
}
