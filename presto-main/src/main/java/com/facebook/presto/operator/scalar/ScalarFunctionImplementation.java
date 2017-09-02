/*
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
package com.facebook.presto.operator.scalar;

import com.google.common.collect.ImmutableList;

import java.lang.invoke.MethodHandle;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.requireNonNull;

public final class ScalarFunctionImplementation
{
    private final boolean nullable;
    private final List<ArgumentProperty> argumentProperties;
    private final MethodHandle methodHandle;
    private final Optional<MethodHandle> instanceFactory;
    private final boolean deterministic;

    public ScalarFunctionImplementation(
            boolean nullable,
            List<ArgumentProperty> argumentProperties,
            MethodHandle methodHandle,
            boolean deterministic)
    {
        this(
                nullable,
                argumentProperties,
                methodHandle,
                Optional.empty(),
                deterministic);
    }

    public ScalarFunctionImplementation(
            boolean nullable,
            List<ArgumentProperty> argumentProperties,
            MethodHandle methodHandle,
            Optional<MethodHandle> instanceFactory,
            boolean deterministic)
    {
        this.nullable = nullable;
        this.argumentProperties = ImmutableList.copyOf(requireNonNull(argumentProperties, "argumentProperties is null"));
        this.methodHandle = requireNonNull(methodHandle, "methodHandle is null");
        this.instanceFactory = requireNonNull(instanceFactory, "instanceFactory is null");
        this.deterministic = deterministic;

        if (instanceFactory.isPresent()) {
            Class<?> instanceType = instanceFactory.get().type().returnType();
            checkArgument(instanceType.equals(methodHandle.type().parameterType(0)), "methodHandle is not an instance method");
        }
    }

    public boolean isNullable()
    {
        return nullable;
    }

    public ArgumentProperty getArgumentProperty(int argumentIndex)
    {
        return argumentProperties.get(argumentIndex);
    }

    public MethodHandle getMethodHandle()
    {
        return methodHandle;
    }

    public Optional<MethodHandle> getInstanceFactory()
    {
        return instanceFactory;
    }

    public boolean isDeterministic()
    {
        return deterministic;
    }

    public static class ArgumentProperty
    {
        private final boolean valueTypeArgument;    // TODO: store the Argument Type, and add isValueType into Type class
        private final Optional<NullConvention> nullConvention;
        private final Optional<Class> lambdaInterface;

        public static ArgumentProperty valueTypeArgumentProperty(NullConvention nullConvention)
        {
            return new ArgumentProperty(true, Optional.of(nullConvention), Optional.empty());
        }

        public static ArgumentProperty functionTypeArgumentProperty(Class lambdaInterface)
        {
            return new ArgumentProperty(false, Optional.empty(), Optional.of(lambdaInterface));
        }

        private ArgumentProperty(boolean valueTypeArgument, Optional<NullConvention> nullConvention, Optional<Class> lambdaInterface)
        {
            if (valueTypeArgument) {
                checkArgument(nullConvention.isPresent(), "nullConvention must present for value type");
                checkArgument(!lambdaInterface.isPresent(), "lambdaInterface must not present for value type");
            }
            else {
                checkArgument(!nullConvention.isPresent(), "nullConvention must not present for function type");
                checkArgument(lambdaInterface.isPresent(), "lambdaInterface must present for function type");
                checkArgument(lambdaInterface.get().isAnnotationPresent(FunctionalInterface.class), "lambdaInterface must be annotated with FunctionalInterface");
            }

            this.valueTypeArgument = valueTypeArgument;
            this.nullConvention = nullConvention;
            this.lambdaInterface = lambdaInterface;
        }

        public boolean isValueTypeArgument()
        {
            return valueTypeArgument;
        }

        public NullConvention getNullConvention()
        {
            checkState(isValueTypeArgument(), "nullConvention only applies to value type argument");
            return nullConvention.get();
        }

        public Class getLambdaInterface()
        {
            checkState(!isValueTypeArgument(), "lambdaInterface only applies to function type argument");
            return lambdaInterface.get();
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj) {
                return true;
            }

            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            ArgumentProperty other = (ArgumentProperty) obj;
            return this.nullConvention == other.nullConvention &&
                    this.lambdaInterface.equals(other.lambdaInterface);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(nullConvention, lambdaInterface);
        }
    }

    public enum NullConvention
    {
        RETURN_NULL_ON_NULL,
        USE_BOXED_TYPE,
        USE_NULL_FLAG,
    }
}
