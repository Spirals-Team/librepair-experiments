package net.mirwaldt.jcomparison.core.exception;

import java.lang.reflect.Field;

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
public class CannotAccessFieldException extends Exception {
    private final Class<?> type;
    private final Field targetField;
    private final Object targetObject;

    public CannotAccessFieldException(Class<?> type, Field targetField, Object targetObject) {
        this.type = type;
        this.targetField = targetField;
        this.targetObject = targetObject;
    }

    public CannotAccessFieldException(String message, Class<?> type, Field targetField, Object targetObject) {
        super(message);
        this.type = type;
        this.targetField = targetField;
        this.targetObject = targetObject;
    }

    public CannotAccessFieldException(String message, Throwable cause, Class<?> type, Field targetField, Object targetObject) {
        super(message, cause);
        this.type = type;
        this.targetField = targetField;
        this.targetObject = targetObject;
    }

    public CannotAccessFieldException(Throwable cause, Class<?> type, Field targetField, Object targetObject) {
        super(cause);
        this.type = type;
        this.targetField = targetField;
        this.targetObject = targetObject;
    }

    public CannotAccessFieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Class<?> type, Field targetField, Object targetObject) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.type = type;
        this.targetField = targetField;
        this.targetObject = targetObject;
    }
}
