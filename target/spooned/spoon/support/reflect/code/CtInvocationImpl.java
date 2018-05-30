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
package spoon.support.reflect.code;


public class CtInvocationImpl<T> extends spoon.support.reflect.code.CtTargetedExpressionImpl<T, spoon.reflect.code.CtExpression<?>> implements spoon.reflect.code.CtInvocation<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.LABEL)
    java.lang.String label;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.ARGUMENT)
    java.util.List<spoon.reflect.code.CtExpression<?>> arguments = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.EXECUTABLE_REF)
    spoon.reflect.reference.CtExecutableReference<T> executable;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtInvocation(this);
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.code.CtExpression<?>> getArguments() {
        return arguments;
    }

    private <C extends spoon.reflect.code.CtAbstractInvocation<T>> C addArgument(int position, spoon.reflect.code.CtExpression<?> argument) {
        if (argument == null) {
            return ((C) (this));
        }
        if ((arguments) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtExpression<?>>emptyList())) {
            arguments = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.PARAMETERS_CONTAINER_DEFAULT_CAPACITY);
        }
        argument.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.ARGUMENT, this.arguments, position, argument);
        arguments.add(position, argument);
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtAbstractInvocation<T>> C addArgument(spoon.reflect.code.CtExpression<?> argument) {
        return addArgument(arguments.size(), argument);
    }

    @java.lang.Override
    public void removeArgument(spoon.reflect.code.CtExpression<?> argument) {
        if ((arguments) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtExpression<?>>emptyList())) {
            return;
        }
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.ARGUMENT, arguments, arguments.indexOf(argument), argument);
        arguments.remove(argument);
    }

    @java.lang.Override
    public spoon.reflect.reference.CtExecutableReference<T> getExecutable() {
        if ((executable) == null) {
            // default reference
            executable = getFactory().Core().createExecutableReference();
            executable.setParent(this);
        }
        return executable;
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtStatement> C insertAfter(spoon.reflect.code.CtStatement statement) {
        spoon.support.reflect.code.CtStatementImpl.insertAfter(this, statement);
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtStatement> C insertBefore(spoon.reflect.code.CtStatement statement) {
        spoon.support.reflect.code.CtStatementImpl.insertBefore(this, statement);
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtStatement> C insertAfter(spoon.reflect.code.CtStatementList statements) {
        spoon.support.reflect.code.CtStatementImpl.insertAfter(this, statements);
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtStatement> C insertBefore(spoon.reflect.code.CtStatementList statements) {
        spoon.support.reflect.code.CtStatementImpl.insertBefore(this, statements);
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtAbstractInvocation<T>> C setArguments(java.util.List<spoon.reflect.code.CtExpression<?>> arguments) {
        if ((arguments == null) || (arguments.isEmpty())) {
            this.arguments = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((C) (this));
        }
        if ((this.arguments) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtExpression<?>>emptyList())) {
            this.arguments = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.PARAMETERS_CONTAINER_DEFAULT_CAPACITY);
        }
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.ARGUMENT, this.arguments, new java.util.ArrayList<>(this.arguments));
        this.arguments.clear();
        for (spoon.reflect.code.CtExpression<?> expr : arguments) {
            addArgument(expr);
        }
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtAbstractInvocation<T>> C setExecutable(spoon.reflect.reference.CtExecutableReference<T> executable) {
        if (executable != null) {
            executable.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.EXECUTABLE_REF, executable, this.executable);
        this.executable = executable;
        return ((C) (this));
    }

    @java.lang.Override
    public java.lang.String getLabel() {
        return label;
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtStatement> C setLabel(java.lang.String label) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.LABEL, label, this.label);
        this.label = label;
        return ((C) (this));
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public spoon.reflect.reference.CtTypeReference<T> getType() {
        return (getExecutable()) == null ? null : getExecutable().getType();
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public <C extends spoon.reflect.declaration.CtTypedElement> C setType(spoon.reflect.reference.CtTypeReference<T> type) {
        if (type != null) {
            type.setParent(this);
        }
        if ((getExecutable()) != null) {
            getExecutable().setType(type);
        }
        return ((C) (this));
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.reference.CtTypeReference<?>> getActualTypeArguments() {
        return (getExecutable()) == null ? spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<?>>emptyList() : getExecutable().getActualTypeArguments();
    }

    @java.lang.Override
    public <T extends spoon.reflect.reference.CtActualTypeContainer> T setActualTypeArguments(java.util.List<? extends spoon.reflect.reference.CtTypeReference<?>> actualTypeArguments) {
        if ((getExecutable()) != null) {
            getExecutable().setActualTypeArguments(actualTypeArguments);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.reference.CtActualTypeContainer> T addActualTypeArgument(spoon.reflect.reference.CtTypeReference<?> actualTypeArgument) {
        if ((getExecutable()) != null) {
            getExecutable().addActualTypeArgument(actualTypeArgument);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public boolean removeActualTypeArgument(spoon.reflect.reference.CtTypeReference<?> actualTypeArgument) {
        if ((getExecutable()) != null) {
            return getExecutable().removeActualTypeArgument(actualTypeArgument);
        }
        return false;
    }

    @java.lang.Override
    public spoon.reflect.code.CtInvocation<T> clone() {
        return ((spoon.reflect.code.CtInvocation<T>) (super.clone()));
    }
}

