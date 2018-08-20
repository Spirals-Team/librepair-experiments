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

import com.zerocracy.Farm;
import com.zerocracy.farm.guts.Guts;
import org.cactoos.scalar.NumberEnvelope;
import org.cactoos.scalar.NumberOf;

/**
 * Reactive farm is still alive?
 *
 * @since 1.0
 */
public final class RvAlive extends NumberEnvelope {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = 8977134566634953102L;

    /**
     * Ctor.
     * @param farm Original farm
     */
    public RvAlive(final Farm farm) {
        super(() -> new NumberOf(
            new Guts(farm).value().xpath(
                "sum(/guts/farm[@id='RvFarm']/alive/count/text())"
            ).get(0)
        ).doubleValue());
    }

}
