package net.mirwaldt.jcomparison.object.impl;

import net.mirwaldt.jcomparison.core.exception.CannotAccessFieldException;
import net.mirwaldt.jcomparison.object.api.FieldValueAccessor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
public class GetterMethodInvokingFieldValueAccessor implements FieldValueAccessor {
    @Override
    public Object accessFieldValue(Class<?> type, Field targetField, Object targetObject) throws CannotAccessFieldException {
        try {
            final Method getterMethod = FieldsWithPublicGetterOnlyPredicate.findGetterMethod(type, targetField);
            try {
                return getterMethod.invoke(targetObject);
            } catch (IllegalAccessException e) {
                throw new CannotAccessFieldException("Illegal access on method '" + getterMethod + "'.", e, type, targetField, targetObject);
            } catch (InvocationTargetException e) {
                throw new CannotAccessFieldException("Cannot invoke method '" + getterMethod + "'.", e, type, targetField, targetObject);
            }
        } catch (NoSuchMethodException e) {
            throw new CannotAccessFieldException("No getter method found for field '" + targetField + "'.", e, type, targetField, targetObject);
        }
    }
}
