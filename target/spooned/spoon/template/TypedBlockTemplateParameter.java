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
package spoon.template;


/**
 * This class represents a template parameter that defines a block statement
 * directly expressed in Java (must return an expression of type <code>R</code>
 * ).
 *
 * <p>
 * To define a new block template parameter, you must subclass this class and
 * implement the {@link #block()} method, which actually defines the Java block.
 * It corresponds to a {@link spoon.reflect.code.CtBlock}.
 */
public abstract class TypedBlockTemplateParameter<R> implements spoon.template.TemplateParameter<R> {
    /**
     * Creates a new block template parameter.
     */
    public TypedBlockTemplateParameter() {
    }

    /**
     * This method must be implemented to define the template block.
     */
    public abstract R block() throws java.lang.Throwable;

    @java.lang.SuppressWarnings("unchecked")
    public spoon.reflect.code.CtBlock<R> getSubstitution(spoon.reflect.declaration.CtType<?> targetType) {
        spoon.reflect.declaration.CtClass<?> c;
        c = targetType.getFactory().Class().get(this.getClass());
        if (c == null) {
            c = targetType.getFactory().Class().get(this.getClass());
        }
        spoon.reflect.declaration.CtMethod m = c.getMethod("block");
        if ((this) instanceof spoon.template.Template) {
            return spoon.template.Substitution.substitute(targetType, ((spoon.template.Template<?>) (this)), m.getBody());
        }
        return m.getBody().clone();
    }

    public R S() {
        return null;
    }
}

