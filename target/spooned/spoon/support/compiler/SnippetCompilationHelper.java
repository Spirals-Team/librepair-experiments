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
package spoon.support.compiler;


/**
 * Helper class for working with snippets
 */
public class SnippetCompilationHelper {
    private SnippetCompilationHelper() {
    }

    private static final java.lang.String WRAPPER_CLASS_NAME = "Wrapper";

    private static final java.lang.String WRAPPER_METHOD_NAME = "wrap";

    public static void compileAndReplaceSnippetsIn(spoon.reflect.declaration.CtType<?> c) {
        spoon.reflect.factory.Factory f = c.getFactory();
        spoon.reflect.declaration.CtType<?> workCopy = c;
        java.util.Set<spoon.reflect.declaration.ModifierKind> backup = java.util.EnumSet.noneOf(spoon.reflect.declaration.ModifierKind.class);
        backup.addAll(workCopy.getModifiers());
        workCopy.removeModifier(spoon.reflect.declaration.ModifierKind.PUBLIC);
        try {
            spoon.support.compiler.SnippetCompilationHelper.build(f, workCopy.toString());
        } finally {
            // restore modifiers
            c.setModifiers(backup);
        }
    }

    public static spoon.reflect.code.CtStatement compileStatement(spoon.reflect.code.CtCodeSnippetStatement st) throws spoon.support.compiler.SnippetCompilationError {
        return spoon.support.compiler.SnippetCompilationHelper.internalCompileStatement(st, st.getFactory().Type().VOID_PRIMITIVE);
    }

    public static spoon.reflect.code.CtStatement compileStatement(spoon.reflect.code.CtCodeSnippetStatement st, spoon.reflect.reference.CtTypeReference returnType) throws spoon.support.compiler.SnippetCompilationError {
        return spoon.support.compiler.SnippetCompilationHelper.internalCompileStatement(st, returnType);
    }

    private static spoon.reflect.code.CtStatement internalCompileStatement(spoon.reflect.declaration.CtElement st, spoon.reflect.reference.CtTypeReference returnType) {
        spoon.reflect.factory.Factory f = st.getFactory();
        java.lang.String contents = spoon.support.compiler.SnippetCompilationHelper.createWrapperContent(st, f, returnType);
        spoon.support.compiler.SnippetCompilationHelper.build(f, contents);
        spoon.reflect.declaration.CtType<?> c = f.Type().get(spoon.support.compiler.SnippetCompilationHelper.WRAPPER_CLASS_NAME);
        // Get the part we want
        spoon.reflect.declaration.CtMethod<?> wrapper = c.getMethod(spoon.support.compiler.SnippetCompilationHelper.WRAPPER_METHOD_NAME);
        java.util.List<spoon.reflect.code.CtStatement> statements = wrapper.getBody().getStatements();
        spoon.reflect.code.CtStatement ret = statements.get(((statements.size()) - 1));
        // Clean up
        c.getPackage().removeType(c);
        if (ret instanceof spoon.reflect.declaration.CtClass) {
            spoon.reflect.declaration.CtClass klass = ((spoon.reflect.declaration.CtClass) (ret));
            ret.getFactory().Package().getRootPackage().addType(klass);
            klass.setSimpleName(klass.getSimpleName().replaceAll("^[0-9]*", ""));
        }
        return ret;
    }

    @java.lang.SuppressWarnings("unchecked")
    public static <T> spoon.reflect.code.CtExpression<T> compileExpression(spoon.reflect.code.CtCodeSnippetExpression<T> expr) throws spoon.support.compiler.SnippetCompilationError {
        spoon.reflect.code.CtReturn<T> ret = ((spoon.reflect.code.CtReturn<T>) (spoon.support.compiler.SnippetCompilationHelper.internalCompileStatement(expr, expr.getFactory().Type().OBJECT)));
        return ret.getReturnedExpression();
    }

    private static void build(spoon.reflect.factory.Factory f, java.lang.String contents) {
        // Build contents
        spoon.SpoonModelBuilder builder = new spoon.support.compiler.jdt.JDTSnippetCompiler(f, contents);
        try {
            builder.build();
        } catch (java.lang.Exception e) {
            throw new spoon.compiler.ModelBuildingException(("snippet compilation error while compiling: " + contents), e);
        }
    }

    private static java.lang.String createWrapperContent(final spoon.reflect.declaration.CtElement element, final spoon.reflect.factory.Factory f, final spoon.reflect.reference.CtTypeReference returnType) {
        spoon.reflect.declaration.CtClass<?> w = f.Class().create(spoon.support.compiler.SnippetCompilationHelper.WRAPPER_CLASS_NAME);
        spoon.reflect.code.CtBlock body = f.Core().createBlock();
        if (element instanceof spoon.reflect.code.CtStatement) {
            body.addStatement(((spoon.reflect.code.CtStatement) (element)));
        }else
            if (element instanceof spoon.reflect.code.CtExpression) {
                spoon.reflect.code.CtReturn ret = f.Core().createReturn();
                ret.setReturnedExpression(((spoon.reflect.code.CtExpression) (element)));
                body.addStatement(ret);
            }

        java.util.Set<spoon.reflect.declaration.ModifierKind> modifiers = java.util.EnumSet.of(spoon.reflect.declaration.ModifierKind.STATIC);
        java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes = new java.util.HashSet<>();
        thrownTypes.add(f.Class().<java.lang.Throwable>get(java.lang.Throwable.class).getReference());
        f.Method().create(w, modifiers, returnType, spoon.support.compiler.SnippetCompilationHelper.WRAPPER_METHOD_NAME, spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtParameter<?>>emptyList(), thrownTypes, body);
        java.lang.String contents = w.toString();
        // Clean up (delete wrapper from factory) after it is printed. The DefaultJavaPrettyPrinter needs w in model to be able to print it correctly
        w.getPackage().removeType(w);
        return contents;
    }
}

