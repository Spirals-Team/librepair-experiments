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
 * This class defines a composite filter, which can compose several filters
 * together by using {@link spoon.reflect.visitor.filter.FilteringOperator}.
 *
 * @author Renaud Pawlak
 */
public class CompositeFilter<T extends spoon.reflect.declaration.CtElement> implements spoon.reflect.visitor.Filter<T> {
    /**
     * Defines the matching using
     * {@link spoon.reflect.visitor.filter.FilteringOperator}.
     */
    public boolean matches(T element) {
        switch (operator) {
            case INTERSECTION :
                for (spoon.reflect.visitor.Filter<T> f : filters) {
                    if (!(hasMatch(f, element))) {
                        return false;
                    }
                }
                return true;
            case UNION :
                for (spoon.reflect.visitor.Filter<T> f : filters) {
                    if (hasMatch(f, element)) {
                        return true;
                    }
                }
                return false;
            case SUBSTRACTION :
                if ((filters.length) == 0) {
                    return false;
                }
                if (!(filters[0].matches(element))) {
                    return false;
                }
                for (int i = 1; i < (filters.length); i++) {
                    if (filters[i].matches(element)) {
                        return false;
                    }
                }
                return true;
            default :
                return false;
        }
    }

    spoon.reflect.visitor.Filter<T>[] filters;

    spoon.reflect.visitor.filter.FilteringOperator operator;

    /**
     * Creates a new composite filter.
     *
     * @param operator
     * 		the operator used to compose the filters together
     * @param filters
     * 		the filters to be composed
     */
    public CompositeFilter(spoon.reflect.visitor.filter.FilteringOperator operator, spoon.reflect.visitor.Filter<T>... filters) {
        this.filters = filters;
        this.operator = operator;
    }

    private boolean hasMatch(spoon.reflect.visitor.Filter<T> filter, T element) {
        try {
            return filter.matches(element);
        } catch (java.lang.ClassCastException e) {
            return false;
        }
    }

    @java.lang.SuppressWarnings("unchecked")
    public java.lang.Class<T> getType() {
        return ((java.lang.Class<T>) (spoon.reflect.declaration.CtElement.class));
    }
}

