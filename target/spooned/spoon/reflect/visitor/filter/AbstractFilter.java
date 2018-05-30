/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.reflect.visitor.filter;


/**
 * Defines an abstract filter based on matching on the element types.
 *
 * Not necessary in simple cases thanks to the use of runtime reflection.
 */
public abstract class AbstractFilter<T extends spoon.reflect.declaration.CtElement> implements spoon.reflect.visitor.Filter<T> {
    private java.lang.Class<T> type;

    /**
     * Creates a filter with the type of the potentially matching elements.
     */
    @java.lang.SuppressWarnings("unchecked")
    public AbstractFilter(java.lang.Class<? super T> type) {
        this.type = ((java.lang.Class<T>) (type));
    }

    /**
     * Creates a filter with the type computed by reflection from the matches method parameter
     */
    @java.lang.SuppressWarnings("unchecked")
    public AbstractFilter() {
        java.lang.reflect.Method method = spoon.support.util.RtHelper.getMethod(getClass(), "matches", 1);
        if (method == null) {
            throw new spoon.SpoonException(("The method matches with one parameter was not found on the class " + (getClass().getName())));
        }
        this.type = ((java.lang.Class<T>) (method.getParameterTypes()[0]));
    }

    public java.lang.Class<T> getType() {
        return type;
    }

    @java.lang.Override
    public boolean matches(T element) {
        return type.isAssignableFrom(element.getClass());
    }
}

