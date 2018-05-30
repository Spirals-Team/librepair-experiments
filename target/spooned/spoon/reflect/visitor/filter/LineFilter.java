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
 * This filter matches all elements that can be considered as line of code (e.g. directly contained in a block, or a then statement). This discards CtStatement that are not used as statement (such as a method call used as RHS).
 * <pre>
 * // lines of a method
 * List&lt;CtStatement&gt; lines = method.getElements(new LineFilter());
 * // find the parent that is used as a statement (in a block or in a branch)
 * CtStatement parentStatement = element.getParent(new LineFilter());
 * </pre>
 */
public class LineFilter extends spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtStatement> {
    /**
     * Creates the filter.
     */
    public LineFilter() {
        super(spoon.reflect.code.CtStatement.class);
    }

    @java.lang.Override
    public boolean matches(spoon.reflect.code.CtStatement element) {
        if (!(super.matches(element))) {
            return false;
        }
        if (element instanceof spoon.reflect.code.CtBlock) {
            return false;
        }
        spoon.reflect.declaration.CtElement parent;
        try {
            parent = element.getParent();
        } catch (spoon.reflect.declaration.ParentNotInitializedException e) {
            return false;
        }
        if (parent instanceof spoon.reflect.code.CtStatementList) {
            return true;
        }
        if (parent instanceof spoon.reflect.code.CtIf) {
            spoon.reflect.code.CtIf anIf = ((spoon.reflect.code.CtIf) (parent));
            return (element.equals(anIf.getThenStatement())) || (element.equals(anIf.getElseStatement()));
        }
        if (parent instanceof spoon.reflect.code.CtLoop) {
            spoon.reflect.code.CtLoop loop = ((spoon.reflect.code.CtLoop) (parent));
            spoon.reflect.code.CtStatement body = loop.getBody();
            if (body == null) {
                return false;
            }
            return body.equals(element);
        }
        return false;
    }
}

