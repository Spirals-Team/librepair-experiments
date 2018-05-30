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
 * This Query expects a {@link CtField} as input
 * and returns all CtElements,
 * which are in visibility scope of that field.
 * In other words, it returns all elements,
 * which might be reference to that field.
 * <br>
 * It can be used to search for variable declarations or
 * variable references which might be in name conflict with input field.
 * <br>
 * Usage:<br>
 * <pre> {@code
 * CtField param = ...;
 * param.map(new FieldScopeFunction()).forEach(...process result...);
 * }
 * </pre>
 */
public class FieldScopeFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtField<?>> {
    public FieldScopeFunction() {
    }

    @java.lang.Override
    public void apply(spoon.reflect.declaration.CtField<?> field, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        if (field.hasModifier(spoon.reflect.declaration.ModifierKind.PRIVATE)) {
            searchForPrivateField(field, outputConsumer);
        }else
            if (field.hasModifier(spoon.reflect.declaration.ModifierKind.PUBLIC)) {
                searchForPublicField(field, outputConsumer);
            }else
                if (field.hasModifier(spoon.reflect.declaration.ModifierKind.PROTECTED)) {
                    searchForProtectedField(field, outputConsumer);
                }else {
                    searchForPackageProtectedField(field, outputConsumer);
                }


    }

    protected void searchForPrivateField(spoon.reflect.declaration.CtField<?> field, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        // private field can be referred from the scope of current top level type only and children
        field.getTopLevelType().filterChildren(null).forEach(outputConsumer);
    }

    protected void searchForProtectedField(spoon.reflect.declaration.CtField<?> field, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        // protected field can be referred from the scope of current top level type only and children
        // visit all elements in scope of these inherited types
        // search for all types which inherits from declaring type of this field
        field.getFactory().getModel().filterChildren(new spoon.reflect.visitor.filter.SubtypeFilter(field.getDeclaringType().getReference())).filterChildren(null).forEach(outputConsumer);
    }

    protected void searchForPublicField(spoon.reflect.declaration.CtField<?> field, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        // public field is visible everywhere
        // visit all children of root package
        field.getFactory().getModel().filterChildren(null).forEach(outputConsumer);
    }

    protected void searchForPackageProtectedField(spoon.reflect.declaration.CtField<?> field, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        // package protected fields are visible in scope of the package of the top level type of the `field`
        // visit all children of package, where top level type of the field is declared
        field.getTopLevelType().getPackage().filterChildren(null).forEach(outputConsumer);
    }
}

