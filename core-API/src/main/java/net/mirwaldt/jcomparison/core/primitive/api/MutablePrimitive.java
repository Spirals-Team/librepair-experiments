package net.mirwaldt.jcomparison.core.primitive.api;

import java.util.Map;
import java.util.function.Supplier;

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
public interface MutablePrimitive<Type> extends Supplier<Type> {
    /**
     * copies its content to another instance.
     * If one exists in cachedMutablePrimitives, then this one is used.
     * Else a new instance is created and added to cachedMutablePrimitives for next use.
     *
     * @param cachedMutablePrimitives the cached instances
     * @return a copy of this instance
     */
    MutablePrimitive<Type> copy(Map<Class<?>, MutablePrimitive<?>> cachedMutablePrimitives);

    boolean isZero();
    
    int NUMBER_OF_PRIMITIVE_TYPES_IN_JAVA = 8;
}
