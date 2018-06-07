/**
 * This file is part of Automated Testing Framework for Java (atf4j).
 *
 * Atf4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Atf4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with atf4j.  If not, see http://www.gnu.org/licenses/.
 */

package net.atf4j.core;

import java.lang.reflect.Field;

import org.slf4j.*;

/**
 * Narrate a test from the call stack.
 */
public final class Narrator {

    private static final String NULL_OBJECT = "object is [NULL]";
    private static final String NULL_SUPER_CLASS = "superClass is [NULL]";
    private static final String NO_MEMBERS = "[NO MEMBERS]";

    /** LAYOUT_STYLE. */
    private static final String LAYOUT_STYLE = "%s [%s]";

    private static final Logger LOG = LoggerFactory.getLogger(Narrator.class);

    /**
     * private constructor to prevent rampant instantiation.
     */
    private Narrator() {
        super();
    }

    /**
     * Use Reflection to produce String by introspection.
     *
     * @param object the object
     * @return the string Object.
     */
    public static String reflectObjectToString(final Object object) {
        if (object == null) {
            return NULL_OBJECT;
        } else {
            final StringBuilder reflection = new StringBuilder();
            final Class<? extends Object> aClass = object.getClass();
            reflection
                .append(Narrator.membersToString(object, aClass))
                .append(Narrator.reflectClassToString(object, aClass));
            return String.format(LAYOUT_STYLE, aClass.getSimpleName(), reflection);
        }
    }

    /**
     * Reflect class to string.
     *
     * @param object the object
     * @param aClass the a class
     * @return the string
     */
    private static String reflectClassToString(final Object object, final Class<? extends Object> aClass) {
        final Class<? extends Object> superClass = aClass.getSuperclass();
        if (superClass == null) {
            return NULL_SUPER_CLASS;
        } else {
            if (superClass.isInstance(Object.class)) {
                return Narrator.membersToString(object, aClass);
            } else {
                final String simpleName = superClass.getSimpleName();
                final String reflectClassToString = Narrator.reflectClassToString(object, superClass);
                return String.format(LAYOUT_STYLE, simpleName, reflectClassToString);
            }
        }
    }

    /**
     * Members to string.
     *
     * @param object the object
     * @param aClass the a class
     * @return the string
     */
    private static String membersToString(final Object object, final Class<?> aClass) {
        if (object == null) {
            return "[NULL]";
        } else {
            final Field[] declaredFields = aClass.getDeclaredFields();
            if (declaredFields.length == 0) {
                return NO_MEMBERS;
            } else {
                final StringBuilder stringBuilder = new StringBuilder("");
                for (final Field field : declaredFields) {
                    try {
                        final String fieldName = field.getName();
                        final Object fieldType = field.get(object);
                        final String memberStr = String.format("%s = %s,", fieldName, fieldType);
                        stringBuilder.append(memberStr);
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        LOG.trace(e.toString());
                    }
                }
                return stringBuilder.toString();
            }
        }
    }
}
