/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
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
 * </p>
 */

package com.dangdang.ddframe.job.lite.lifecycle;

import com.dangdang.ddframe.job.reg.exception.RegExceptionHandler;
import com.google.common.base.Joiner;
import org.apache.curator.test.TestingServer;
import org.junit.BeforeClass;

import java.io.File;
import java.io.IOException;

public abstract class AbstractEmbedZookeeperBaseTest {
    
    private static final int PORT = 3181;
    
    private static volatile TestingServer testingServer;
    
    @BeforeClass
    public static void setUp() {
        startEmbedTestingServer();
    }
    
    private static void startEmbedTestingServer() {
        if (null != testingServer) {
            return;
        }
        try {
            testingServer = new TestingServer(PORT, new File(String.format("target/test_zk_data/%s/", System.nanoTime())));
            // CHECKSTYLE:OFF
        } catch (final Exception ex) {
            // CHECKSTYLE:ON
            RegExceptionHandler.handleException(ex);
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                
                @Override
                public void run() {
                    try {
                        testingServer.close();
                    } catch (final IOException ex) {
                        RegExceptionHandler.handleException(ex);
                    }
                }
            });
        }
    }
    
    public static String getConnectionString() {
        return Joiner.on(":").join("localhost", PORT);
    }
}
