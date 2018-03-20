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
package com.zerocracy.tk.project;

import com.zerocracy.Farm;
import com.zerocracy.Par;
import com.zerocracy.Project;
import com.zerocracy.pmo.Catalog;
import com.zerocracy.pmo.Pmo;
import com.zerocracy.tk.RsParFlash;
import java.io.IOException;
import java.util.logging.Level;
import org.takes.Response;
import org.takes.facets.fork.RqRegex;
import org.takes.facets.fork.TkRegex;
import org.takes.facets.forward.RsForward;
import org.takes.rs.RsWithBody;
import org.takes.rs.RsWithHeaders;
import org.takes.rs.RsWithType;

/**
 * Project badge.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.18
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class TkBadge implements TkRegex {

    /**
     * Farm.
     */
    private final Farm farm;

    /**
     * Ctor.
     * @param frm Farm
     */
    public TkBadge(final Farm frm) {
        this.farm = frm;
    }

    @Override
    public Response act(final RqRegex req) throws IOException {
        final String pid = req.matcher().group(1);
        final Project pmo = new Pmo(this.farm);
        final Catalog catalog = new Catalog(pmo).bootstrap();
        if (!catalog.exists(pid)) {
            throw new RsForward(
                new RsParFlash(
                    new Par("Project %s not found").say(pid),
                    Level.WARNING
                )
            );
        }
        return new RsWithHeaders(
            new RsWithType(
                new RsWithBody(
                    this.getClass().getResource("badge.svg")
                ),
                "image/svg+xml"
            ),
            "Cache-Control: no-cache",
            String.format("X-Zerocracy-Project-ID: %s", pid)
        );
    }

}
