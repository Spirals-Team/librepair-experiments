package net.mirwaldt.jcomparison.core.util;

import java.util.function.Function;
import java.util.function.Predicate;

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
public class CopyIfNonEmptyFunction<Type> implements Function<Type, Type> {
    private final Function<Type, Type> copyFunction;
    private final Predicate<Type> isEmptyPredicate;

    public CopyIfNonEmptyFunction(Predicate<Type> isEmptyPredicate, Function<Type, Type> copyFunction) {
        this.isEmptyPredicate = isEmptyPredicate;
        this.copyFunction = copyFunction;
    }

    @Override
    public Type apply(Type toBeCopied) {
        if(isEmptyPredicate.test(toBeCopied)) {
            return toBeCopied;
        } else {
            return copyFunction.apply(toBeCopied);
        }
    }
}
