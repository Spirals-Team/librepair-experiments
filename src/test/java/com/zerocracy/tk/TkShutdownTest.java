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
package com.zerocracy.tk;

import com.zerocracy.farm.fake.FkFarm;
import com.zerocracy.farm.props.PropsFarm;
import java.net.HttpURLConnection;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.takes.facets.hamcrest.HmRsStatus;
import org.takes.rq.RqFake;
import org.takes.rq.RqWithHeader;
import org.takes.rq.RqWrap;

/**
 * Test case for {@link TkShutdown}.
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @since 0.20
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class TkShutdownTest {
    @Test
    public void returnOk() throws Exception {
        MatcherAssert.assertThat(
            new TkApp(new PropsFarm(new FkFarm())).act(
                new RqWithHeader(
                    new TkShutdownTest.RqShutdown(),
                    "X-Auth",
                    "test"
                )
            ),
            new HmRsStatus(HttpURLConnection.HTTP_OK)
        );
    }

    /**
     * Shutdown fake request.
     */
    private static final class RqShutdown extends RqWrap {
        /**
         * Ctor.
         */
        RqShutdown() {
            super(new RqFake("GET", "/shutdown"));
        }
    }
}
