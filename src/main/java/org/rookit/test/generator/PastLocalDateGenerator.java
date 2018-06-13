/*******************************************************************************
 * Copyright (C) 2018 Joao Sousa
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.rookit.test.generator;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Objects;
import java.util.Random;

import javax.annotation.Generated;

@SuppressWarnings("javadoc")
public class PastLocalDateGenerator extends AbstractGenerator<LocalDate> {

    private static final int YEAR_MIN = 1700;
    private static final int YEAR_MAX = Year.now().getValue();

    private final Random random;
    private final Generator<Month> monthGenerator;

    @Inject
    private PastLocalDateGenerator(final Random random, final Generator<Month> monthGenerator) {
        super();
        this.random = random;
        this.monthGenerator = monthGenerator;
    }

    @Override
    public LocalDate createRandom() {
        final int year = YEAR_MIN + this.random.nextInt(YEAR_MAX - YEAR_MIN);
        final Month month = this.monthGenerator.createRandom();
        final int dayOfMonth = 1 + this.random.nextInt(month.length(false) - 1);
        return LocalDate.of(year, month, dayOfMonth);
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof PastLocalDateGenerator) {
            if (!super.equals(object)) {
                return false;
            }
            final PastLocalDateGenerator that = (PastLocalDateGenerator) object;
            return Objects.equals(this.monthGenerator, that.monthGenerator);
        }
        return false;
    }

    @SuppressWarnings("boxing")
    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.monthGenerator);
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("super", super.toString())
                .add("YEAR_MIN", YEAR_MIN)
                .add("YEAR_MAX", YEAR_MAX)
                .add("monthGenerator", this.monthGenerator)
                .toString();
    }

}
