/*
 * Copyright (C) 2012 Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.airlift.drift.codec.guice;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import io.airlift.drift.codec.InternalThriftCodec;
import io.airlift.drift.codec.ThriftCodec;
import io.airlift.drift.codec.ThriftCodecManager;
import io.airlift.drift.codec.internal.ForCompiler;
import io.airlift.drift.codec.internal.ThriftCodecFactory;
import io.airlift.drift.codec.internal.compiler.CompilerThriftCodecFactory;
import io.airlift.drift.codec.metadata.ThriftCatalog;

import java.util.Objects;

import static com.google.inject.multibindings.Multibinder.newSetBinder;

public class ThriftCodecModule
        implements Module
{
    private final ClassLoader parent;

    public ThriftCodecModule()
    {
        this(ThriftCodecModule.class.getClassLoader());
    }

    public ThriftCodecModule(ClassLoader parent)
    {
        this.parent = parent;
    }

    @Override
    public void configure(Binder binder)
    {
        binder.bind(ThriftCodecFactory.class).to(CompilerThriftCodecFactory.class).in(Scopes.SINGLETON);
        binder.bind(ThriftCatalog.class).in(Scopes.SINGLETON);
        binder.bind(ThriftCodecManager.class).in(Scopes.SINGLETON);
        newSetBinder(binder, new TypeLiteral<ThriftCodec<?>>() {}, InternalThriftCodec.class).permitDuplicates();

        binder.bind(ClassLoader.class)
                .annotatedWith(ForCompiler.class)
                .toInstance(parent);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ThriftCodecModule that = (ThriftCodecModule) o;
        return Objects.equals(parent, that.parent);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(parent);
    }
}
