/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.esigate.extension.parallelesi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.esigate.HttpErrorPage;
import org.esigate.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ensure the multi-threaded esi execution.
 * 
 * @author Nicolas Richeton
 * 
 */
public class ParallelEsiTest extends AbstractElementTest {

    @Override
    protected void setUp() {
        super.setUp();
        addResource("/test", "test");
    }

    /**
     * This tests uses a blocking executor and ensure all includes are started in a new thread before running them.
     * 
     * @throws IOException
     * @throws HttpErrorPage
     */
    public void testParallelInclude() throws IOException, HttpErrorPage {
        // ESI executor
        final BlockingExecutor exe = new BlockingExecutor();
        setTested(new EsiRenderer(exe));

        // Build page and expected result
        StringBuilder expect = new StringBuilder(Parameters.DEFAULT_BUFFER_SIZE);
        StringBuilder page = new StringBuilder(Parameters.DEFAULT_BUFFER_SIZE);
        page.append("before ");
        expect.append("before ");
        for (int i = 0; i < 100; i++) {
            page.append("<esi:include src=\"$(PROVIDER{mock})/test\" />");
            expect.append("test");
        }
        page.append(" after");
        expect.append(" after");

        // This watches over the blocking executor and release execution when
        // all includes are added.
        Runnable r = new Runnable() {
            @Override
            public void run() {
                boolean quit = false;
                while (!quit) {
                    if (exe.getCount() == 100) {
                        exe.perform();
                        quit = true;
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        ExecutorService watchDog = Executors.newSingleThreadExecutor();
        watchDog.execute(r);

        String result = render(page.toString());

        // close
        watchDog.shutdown();

        // Asserts
        assertEquals(100, exe.getCount());
        assertEquals(expect.toString(), result);
    }

    /**
     * A blocking executor which will not start runnable unless {@link #perform()} is called.
     * 
     * @author nricheto
     * 
     */
    static class BlockingExecutor implements Executor {
        private static final Logger LOG = LoggerFactory.getLogger(BlockingExecutor.class);
        private List<Runnable> runnables = new ArrayList<>();

        @Override
        public void execute(Runnable command) {
            LOG.info("Adding command {}", command);
            this.runnables.add(command);
        }

        /**
         * Start thread execution
         */
        public void perform() {
            Executor exe = Executors.newCachedThreadPool();
            for (Runnable r : this.runnables) {
                exe.execute(r);
            }
        }

        /**
         * Get current number of Runnable queued.
         * 
         * @return current number of Runnable queued.
         */
        public int getCount() {
            return this.runnables.size();
        }
    }

}
