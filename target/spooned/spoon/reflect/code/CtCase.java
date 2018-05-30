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
package spoon.reflect.code;


/**
 * This code element defines a case within a switch-case.
 *
 * Example: <pre>
 * int x = 0;
 * switch(x) {
 *     case 1: // &lt;-- case statement
 *       System.out.println("foo");
 * }</pre>
 *
 * @param <S>
 * 		This type must be assignable from the switch type
 * @see spoon.reflect.code.CtSwitch
 */
public interface CtCase<S> extends spoon.reflect.code.CtStatement , spoon.reflect.code.CtStatementList {
    /**
     * Gets the case expression.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<S> getCaseExpression();

    /**
     * Sets the case expression.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    <T extends spoon.reflect.code.CtCase<S>> T setCaseExpression(spoon.reflect.code.CtExpression<S> caseExpression);

    @java.lang.Override
    spoon.reflect.code.CtCase<S> clone();
}

