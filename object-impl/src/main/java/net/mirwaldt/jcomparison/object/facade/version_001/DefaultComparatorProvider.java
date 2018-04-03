package net.mirwaldt.jcomparison.object.facade.version_001;

import net.mirwaldt.jcomparison.core.array.impl.DefaultArrayComparator;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.facade.BasicComparators;
import net.mirwaldt.jcomparison.core.facade.EpsilonValueComparators;
import net.mirwaldt.jcomparison.core.facade.ValueComparators;
import net.mirwaldt.jcomparison.core.facade.builder.DefaultListComparatorBuilder;
import net.mirwaldt.jcomparison.core.facade.builder.DefaultSubstringComparatorBuilder;
import net.mirwaldt.jcomparison.core.facade.builder.DefaultMapComparatorBuilder;
import net.mirwaldt.jcomparison.core.facade.builder.DefaultSetComparatorBuilder;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.primitive.api.MutablePrimitive;
import net.mirwaldt.jcomparison.core.decorator.tracing.CycleDetectingComparator;
import net.mirwaldt.jcomparison.core.decorator.tracing.TracingComparator;
import net.mirwaldt.jcomparison.core.primitive.impl.MutableDouble;
import net.mirwaldt.jcomparison.core.primitive.impl.MutableFloat;
import net.mirwaldt.jcomparison.core.string.impl.DefaultStringBasedComparator;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;
import net.mirwaldt.jcomparison.object.facade.builder.DefaultObjectComparatorBuilder;
import net.mirwaldt.jcomparison.provider.api.ComparatorProvider;
import net.mirwaldt.jcomparison.provider.impl.ListComparatorProvider;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;


/**
 * This file is part of the open-source-framework jComparison.
 * Copyright (C) 2015-2017 Michael Mirwaldt.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class DefaultComparatorProvider implements ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>> {
    private final boolean isStrict;
    private final Function<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>, ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>> preComparatorFunction;

    private final Map<String, ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>> cachedComparators;

    private final ListComparatorProvider listComparatorProvider;
    private final BiConsumer<Object, ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>>> builderConfigurator;

    private final UnaryOperator<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>> cycleProtectingPreComparatorFunction;

    public DefaultComparatorProvider(boolean isStrict,
                                     boolean isThreadSafe,
                                     Function<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>, ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>> preComparatorFunction,
                                     ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>> cycleHandlingComparator,
                                     BiConsumer<Object, ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>>> builderConfigurator
    ) {
        this.isStrict = isStrict;
        if (isThreadSafe) {
            cachedComparators = new ConcurrentHashMap<>();
        } else {
            cachedComparators = new HashMap<>();
        }
        this.builderConfigurator = builderConfigurator;

        this.cycleProtectingPreComparatorFunction = (itemComparator) -> new CycleDetectingComparator(new TracingComparator(itemComparator), cycleHandlingComparator);

        this.preComparatorFunction = preComparatorFunction.compose(cycleProtectingPreComparatorFunction);

        final List<ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>>> comparatorProviders = new ArrayList<>();
        createDefaultComparatorProvider(comparatorProviders);

        final DefaultObjectComparatorBuilder<Object> defaultObjectComparator = ObjectComparators.createDefaultObjectComparatorBuilder();
        this.builderConfigurator.accept(defaultObjectComparator, this);

        //TODO: useComparatorProvider-call on builder to builderConfigurator
        defaultObjectComparator.useComparatorProvider(this);

        this.listComparatorProvider = new ListComparatorProvider(comparatorProviders, defaultObjectComparator.build());
    }

    @Override
    public ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>> provideComparator(Object objectMeta, Object leftObject, Object rightObject) {
        return listComparatorProvider.provideComparator(objectMeta, leftObject, rightObject);
    }

    private void createDefaultComparatorProvider(List<ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>>> comparatorProviders) {
        final ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>> primitiveTypeComparatorProvider = (Object objectMeta, Object leftObject, Object rightObject) -> {
            final Class<?> type = getTypeFrom(objectMeta);
            if (type != null) {
                if (type.isPrimitive()) {
                    if (type.equals(Double.class) || type.equals(Float.class) || type.equals(MutableDouble.class) || type.equals(MutableFloat.class)) {
                        return getCachedOrCreate(Double.class.getName(), () -> preComparatorFunction.apply(EpsilonValueComparators.ulpEpsilonDoubleComparator()));
                    } else {
                        return getCacheOrCreateEqualsComparatorWithPrecomparator();
                    }
                } else if (MutablePrimitive.class.isAssignableFrom(type)) {
                    return getCacheOrCreateEqualsComparatorWithPrecomparator();
                }
            }
            return BasicComparators.getNoComparator();
        };
        comparatorProviders.add(primitiveTypeComparatorProvider);

        final ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>> enumComparatorProvider = (Object objectMeta, Object leftObject, Object rightObject) -> {
            final Class<?> type = getTypeFrom(objectMeta);
            if (type != null) {
                if (type.isEnum()) {
                    return getCacheOrCreateEqualsComparatorWithPrecomparator();
                }
            }
            return BasicComparators.getNoComparator();
        };
        comparatorProviders.add(enumComparatorProvider);

        final ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>> substringComparatorProvider = (Object objectMeta, Object leftObject, Object rightObject) -> {
            final Class<?> type = getTypeFrom(objectMeta);
            if (type != null) {
                if (type.equals(String.class)) {
                    return getOrCreateSubstringComparator();
                }
            }
            return BasicComparators.getNoComparator();
        };
        comparatorProviders.add(substringComparatorProvider);

        final ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>> arrayComparatorProvider = (Object objectMeta, Object leftObject, Object rightObject) -> {
            final Class<?> type = getTypeFrom(objectMeta);
            if (type != null) {
                if (type.isArray()) {
                    return getCachedOrCreate(Object[].class.getName(), () -> {
                        DefaultArrayComparator<Object> defaultArrayComparator = DefaultComparators.createDefaultArrayComparatorBuilder()
                                .useCycleProtectingPreComparatorFunction(cycleProtectingPreComparatorFunction).build();
                        return preComparatorFunction.apply(DefaultComparators.createTypeSafeDefaultArrayComparator(defaultArrayComparator, isStrict));
                    });
                }
            }
            return BasicComparators.getNoComparator();
        };
        comparatorProviders.add(arrayComparatorProvider);

        final ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>> collectionKindComparatorProvider = (Object objectMeta, Object leftObject, Object rightObject) -> {
            final Class<?> type = getTypeFrom(objectMeta);
            if (type != null) {
                if (Collection.class.isAssignableFrom(type)) {
                    if (type.equals(Set.class)) {
                        return getCachedOrCreate(Set.class.getName(), () -> {
                            final DefaultSetComparatorBuilder<Object> defaultSetComparatorBuilder = DefaultComparators.createDefaultSetComparatorBuilder();
                            builderConfigurator.accept(defaultSetComparatorBuilder, this);
                            return preComparatorFunction.apply(DefaultComparators.createSafeDefaultSetComparator(defaultSetComparatorBuilder, isStrict));
                        });
                    } else if (type.equals(List.class)) {
                        return getCachedOrCreate(List.class.getName(), () -> {
                            final DefaultListComparatorBuilder<Object> defaultListComparatorBuilder = DefaultComparators.<Object>createDefaultListComparatorBuilder();
                            builderConfigurator.accept(defaultListComparatorBuilder, this);
                            return preComparatorFunction.apply(DefaultComparators.createTypeSafeDefaultListComparator(defaultListComparatorBuilder, isStrict));
                        });
                    } else {
                        return getCacheOrCreateEqualsComparatorWithPrecomparator();
                    }
                } else if (Map.class.isAssignableFrom(type)) { // a map is no collection!
                    return getCachedOrCreate(Map.class.getName(), () -> {
                        final DefaultMapComparatorBuilder<?, ?> defaultMapComparatorBuilder = DefaultComparators.createDefaultMapComparatorBuilder();
                        builderConfigurator.accept(defaultMapComparatorBuilder, this);
                        return preComparatorFunction.apply(DefaultComparators.createSafeDefaultMapComparator(defaultMapComparatorBuilder, isStrict));
                    });
                }
            }
            return BasicComparators.getNoComparator();
        };
        comparatorProviders.add(collectionKindComparatorProvider);

        final ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>> descriptorComparatorProvider = (Object objectMeta, Object leftObject, Object rightObject) -> {
            final Class<?> type = getTypeFrom(objectMeta);
            if (type != null) {
                if (type.equals(File.class)) {
                    return new DefaultStringBasedComparator<>((file) -> ((File) file).getPath(), getOrCreateSubstringComparator());
                } else if (type.equals(URI.class)) {
                    return new DefaultStringBasedComparator<>((uri) -> ((URI) uri).getPath(), getOrCreateSubstringComparator());
                } else if (type.equals(URL.class)) {
                    return new DefaultStringBasedComparator<>((url) -> ((URL) url).getPath(), getOrCreateSubstringComparator());
                }
            }
            return BasicComparators.getNoComparator();
        };
        comparatorProviders.add(descriptorComparatorProvider);
    }

    private ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>> getCacheOrCreateEqualsComparatorWithPrecomparator() {
        return getCachedOrCreate("equals", () -> preComparatorFunction.apply(ValueComparators.immutableResultEqualsComparator()));
    }

    private Class<?> getTypeFrom(Object objectMeta) {
        if (objectMeta instanceof Class<?>) {
            return (Class<?>) objectMeta;
        } else if (objectMeta instanceof Field) {
            return ((Field) objectMeta).getType();
        }
        return null;
    }

    private ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>> getOrCreateSubstringComparator() {
        DefaultSubstringComparatorBuilder defaultSubstringComparatorBuilder = DefaultComparators.createDefaultSubstringComparatorBuilder();
        builderConfigurator.accept(defaultSubstringComparatorBuilder, this);
        return getCachedOrCreate(String.class.getName(), () -> preComparatorFunction.apply(DefaultComparators.createSafeDefaultSubstringComparator(defaultSubstringComparatorBuilder, isStrict)));
    }

    private ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>> getCachedOrCreate(String comparatorId, Supplier<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>> comparatorSupplier) {
        return cachedComparators.computeIfAbsent(comparatorId, (key) -> comparatorSupplier.get());
    }
}
