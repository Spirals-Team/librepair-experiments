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
package com.zerocracy.pmo;

import com.jcabi.matchers.XhtmlMatchers;
import com.zerocracy.Farm;
import com.zerocracy.Item;
import com.zerocracy.Xocument;
import com.zerocracy.farm.fake.FkFarm;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link Resumes}.
 *
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @since 0.22.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
public final class ResumesTest {
    @Test
    public void addsResumes() throws Exception {
        final Farm farm = new FkFarm();
        new Resumes(farm).bootstrap()
            .add(
                "new",
                LocalDateTime.of(
                    LocalDate.of(2018, Month.JANUARY, 1),
                    LocalTime.of(0, 0)
                ),
                "Invite me",
                "INTJ-A",
                187141,
                "test"
            );
        try (final Item item = new Pmo(farm).acq("resumes.xml")) {
            MatcherAssert.assertThat(
                new Xocument(item).nodes("/resumes/resume[@login = 'new']"),
                Matchers.contains(
                    XhtmlMatchers.hasXPaths(
                        "resume/text[text() = 'Invite me']",
                        "resume/submitted[text() = '2018-01-01T00:00:00']",
                        "resume/personality[text() = 'INTJ-A']",
                        "resume/stackoverflow[text() = '187141']"
                    )
                )
            );
        }
    }
}
