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

import com.zerocracy.Farm;
import com.zerocracy.Item;
import com.zerocracy.Xocument;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.xembly.Directives;

/**
 * Resumes.
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @since 0.22.2
 * @todo #908:30min Let's create new stakeholder to assign resume to examiner
 *  and notify. When the examiner "invites" a user,
 *  we check whether there was a resume for that user.
 *  If yes, we pay examiner +32 points. Examiner can reject a resume
 *  by saying 'deny {username}' (where username is a login in 'resumes.xml')
 *  to Zerocrat. In that case we also pay +32 to the examiner.
 */
public final class Resumes {
    /**
     * PMO.
     */
    private final Pmo pmo;

    /**
     * Ctor.
     * @param farm The farm
     */
    public Resumes(final Farm farm) {
        this(new Pmo(farm));
    }

    /**
     * Ctor.
     * @param pkt PMO
     */
    public Resumes(final Pmo pkt) {
        this.pmo = pkt;
    }

    /**
     * Bootstrap it.
     * @return This
     * @throws IOException If fails
     */
    public Resumes bootstrap() throws IOException {
        try (final Item item = this.item()) {
            new Xocument(item.path()).bootstrap("pmo/resumes");
        }
        return this;
    }

    /**
     * Add resume.
     * @param login Resume author
     * @param when When submitted
     * @param text Resume text
     * @param personality Author's personality
     * @param stackoverflow Stackoverflow id
     * @param telegram Telegram username
     * @throws IOException If fails
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    @SuppressWarnings("PMD.UseObjectForClearerAPI")
    public void add(final String login, final LocalDateTime when,
        final String text, final String personality,
        final int stackoverflow, final String telegram) throws IOException {
        try (final Item item = this.item()) {
            new Xocument(item).modify(
                new Directives()
                    .xpath("/resumes")
                    .add("resume")
                    .attr("login", login)
                    .add("submitted")
                    .set(when.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    .up()
                    .add("text")
                    .set(text)
                    .up()
                    .add("personality")
                    .set(personality)
                    .up()
                    .add("stackoverflow")
                    .set(stackoverflow)
                    .up()
                    .add("telegram")
                    .set(telegram)
            );
        }
    }

    /**
     * The item.
     * @return Item
     * @throws IOException If fails
     */
    private Item item() throws IOException {
        return this.pmo.acq("resumes.xml");
    }
}
