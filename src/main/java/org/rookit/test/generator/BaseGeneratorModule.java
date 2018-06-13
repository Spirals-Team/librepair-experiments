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

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.Random;

@SuppressWarnings("javadoc")
public class BaseGeneratorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Random.class).toInstance(new Random());
        
        bind(new TypeLiteral<Generator<String>>() {
            }).to(StringGenerator.class).in(Singleton.class);
        bind(StringGenerator.class).in(Singleton.class);
        
        bind(new TypeLiteral<Generator<Boolean>>() {
            }).to(BooleanGenerator.class).in(Singleton.class);
        bind(BooleanGenerator.class).in(Singleton.class);
        
        bind(new TypeLiteral<Generator<byte[]>>() {
            }).to(ByteArrayGenerator.class).in(Singleton.class);
        bind(ByteArrayGenerator.class).in(Singleton.class);
        
        bindNumeric();
        
        bindTime();
    }

    private void bindTime() {
        bind(new TypeLiteral<Generator<Duration>>() {
            }).to(DurationGenerator.class).in(Singleton.class);
        bind(DurationGenerator.class).in(Singleton.class);
        
        bind(new TypeLiteral<Generator<LocalDate>>() {
        }).to(PastLocalDateGenerator.class).in(Singleton.class);
        bind(PastLocalDateGenerator.class).in(Singleton.class);
    }

    private void bindNumeric() {
        bind(new TypeLiteral<Generator<Short>>() {
        }).to(ShortGenerator.class).in(Singleton.class);
        bind(ShortGenerator.class).in(Singleton.class);
        
        bind(new TypeLiteral<Generator<Double>>() {
            }).to(DoubleGenerator.class).in(Singleton.class);
        bind(DoubleGenerator.class).in(Singleton.class);
        
        bind(new TypeLiteral<Generator<Integer>>() {
        }).to(IntegerGenerator.class).in(Singleton.class);
        bind(IntegerGenerator.class).in(Singleton.class);
        
        bind(new TypeLiteral<Generator<Long>>() {
        }).to(PositiveLongGenerator.class).in(Singleton.class);
        bind(PositiveLongGenerator.class).in(Singleton.class);
    }
    
    @Provides
    private Generator<Month> getMonthGenerator(final Random random) {
        return new EnumGenerator<>(random, Month.class);
    }

}
