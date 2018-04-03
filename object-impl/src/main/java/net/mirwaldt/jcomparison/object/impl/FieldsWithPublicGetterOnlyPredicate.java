package net.mirwaldt.jcomparison.object.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.BiPredicate;

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
public class FieldsWithPublicGetterOnlyPredicate implements BiPredicate<Class<?>, Field> {

    public static Method findGetterMethod(Class<?> aClass, Field field) throws NoSuchMethodException {
        final StringBuilder stringBuilder = new StringBuilder(field.getName());
        stringBuilder.replace(0, 1, field.getName().toUpperCase().substring(0, 1));
        final String getterMethodName = "get" + stringBuilder.toString();
        return aClass.getDeclaredMethod(getterMethodName);
    }

    @Override
    public boolean test(Class<?> aClass, Field field) {
        try {
            findGetterMethod(aClass, field);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }
}
