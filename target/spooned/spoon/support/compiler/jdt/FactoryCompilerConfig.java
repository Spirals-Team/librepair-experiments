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
package spoon.support.compiler.jdt;


public class FactoryCompilerConfig implements spoon.SpoonModelBuilder.InputType {
    public static final spoon.SpoonModelBuilder.InputType INSTANCE = new spoon.support.compiler.jdt.FactoryCompilerConfig();

    // avoid direct instantiation. But somebody can inherit
    protected FactoryCompilerConfig() {
    }

    /**
     * returns the compilation units corresponding to the types in the factory.
     */
    @java.lang.Override
    public void initializeCompiler(spoon.support.compiler.jdt.JDTBatchCompiler compiler) {
        spoon.support.compiler.jdt.JDTBasedSpoonCompiler jdtCompiler = compiler.getJdtCompiler();
        java.util.List<org.eclipse.jdt.internal.compiler.batch.CompilationUnit> unitList = new java.util.ArrayList<>();
        for (spoon.reflect.declaration.CtType<?> ctType : jdtCompiler.getFactory().Type().getAll()) {
            if (ctType.isTopLevel()) {
                unitList.add(new spoon.support.compiler.jdt.CompilationUnitWrapper(ctType));
            }
        }
        compiler.setCompilationUnits(unitList.toArray(new org.eclipse.jdt.internal.compiler.batch.CompilationUnit[unitList.size()]));
    }
}

