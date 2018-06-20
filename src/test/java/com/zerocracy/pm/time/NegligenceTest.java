/**
 * Copyright (c) 2016-2018 Zerocracy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.zerocracy.pm.time;

import com.zerocracy.farm.fake.FkFarm;
import com.zerocracy.farm.fake.FkProject;
import com.zerocracy.pmo.Negligence;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link Negligence}.
 *
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id$
 * @since 0.23
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class NegligenceTest {

    /**
     * {@link Negligence} can be bootstrapped.
     * @throws Exception If something goes wrong.
     */
    @Test
    public void bootstraps() throws Exception {
        MatcherAssert.assertThat(
            new Negligence(new FkFarm(new FkProject()), "amihaiemil")
                .bootstrap(),
            Matchers.allOf(
                Matchers.instanceOf(Negligence.class),
                Matchers.notNullValue()
            )
        );
    }

    /**
     * {@link Negligence} can return the number of delays
     * (not implemented yet).
     * @throws Exception If something goes wrong.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void returnsDelays() throws Exception {
        new Negligence(new FkFarm(new FkProject()), "g4s8")
            .bootstrap().delays();
    }

}
