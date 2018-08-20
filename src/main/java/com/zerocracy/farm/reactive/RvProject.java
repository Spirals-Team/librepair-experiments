/*
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
package com.zerocracy.farm.reactive;

import com.zerocracy.Item;
import com.zerocracy.Project;
import java.io.IOException;
import lombok.EqualsAndHashCode;
import org.cactoos.Proc;

/**
 * Reactive project.
 *
 * @since 1.0
 */
@EqualsAndHashCode(of = "origin")
final class RvProject implements Project {

    /**
     * Origin project.
     */
    private final Project origin;

    /**
     * The flush.
     */
    private final Proc<Project> flush;

    /**
     * Testing.
     */
    private final boolean testing;

    /**
     * Ctor.
     * @param pkt Project
     * @param tgr Trigger
     * @param tst Testing
     */
    RvProject(final Project pkt, final Proc<Project> tgr,
        final boolean tst) {
        this.origin = pkt;
        this.flush = tgr;
        this.testing = tst;
    }

    @Override
    public String pid() throws IOException {
        return this.origin.pid();
    }

    @Override
    public Item acq(final String file) throws IOException {
        Item item = this.origin.acq(file);
        if ("claims.xml".equals(file)) {
            item = new RvClaims(
                item,
                item1 -> this.flush.exec(this),
                this.testing
            );
        }
        return item;
    }

}
