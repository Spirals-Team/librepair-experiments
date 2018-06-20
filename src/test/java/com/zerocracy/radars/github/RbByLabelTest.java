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
package com.zerocracy.radars.github;

import com.jcabi.github.Github;
import com.jcabi.github.mock.MkGithub;
import com.zerocracy.farm.fake.FkFarm;
import java.io.IOException;
import javax.json.Json;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test case for {@link RbByLabel}.
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @since 0.16
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class RbByLabelTest {

    @Test
    public void ignoresLabel() throws IOException {
        final Github github = new MkGithub();
        final Rebound rebound = Mockito.mock(Rebound.class);
        MatcherAssert.assertThat(
            new RbByLabel(rebound, "bug").react(
                new FkFarm(),
                github,
                Json.createObjectBuilder().add(
                    "label",
                    Json.createObjectBuilder().add(
                        "name", "feature"
                    )
                ).build()
            ),
            Matchers.equalTo("[feature]: not this one")
        );
    }

    @Test
    public void passesThrough() throws IOException {
        final Github github = new MkGithub();
        final Rebound rebound = Mockito.mock(Rebound.class);
        final String label = "improvement";
        MatcherAssert.assertThat(
            new RbByLabel(rebound, label).react(
                new FkFarm(),
                github,
                Json.createObjectBuilder().add(
                    "label",
                    Json.createObjectBuilder().add(
                        "name", label
                    )
                ).build()
            ),
            Matchers.equalTo("[improvement]: null")
        );
    }
}
