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


class TreeBuilderCompiler extends org.eclipse.jdt.internal.compiler.Compiler {
    TreeBuilderCompiler(org.eclipse.jdt.internal.compiler.env.INameEnvironment environment, org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy policy, org.eclipse.jdt.internal.compiler.impl.CompilerOptions options, org.eclipse.jdt.internal.compiler.ICompilerRequestor requestor, org.eclipse.jdt.internal.compiler.IProblemFactory problemFactory, java.io.PrintWriter out, org.eclipse.jdt.core.compiler.CompilationProgress progress) {
        super(environment, policy, options, requestor, problemFactory, out, progress);
    }

    // this method is not meant to be in the public API
    protected org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration[] buildUnits(org.eclipse.jdt.internal.compiler.batch.CompilationUnit[] sourceUnits) {
        // //////////////////////////////////////////////////////////////////////////
        // This code is largely inspired from JDT's
        // CompilationUnitResolver.resolve
        org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration unit = null;
        int i = 0;
        // build and record parsed units
        beginToCompile(sourceUnits);
        // process all units (some more could be injected in the loop by
        // the lookup environment)
        for (; i < (this.totalUnits); i++) {
            unit = unitsToProcess[i];
            // System.err.println(unit);
            this.parser.getMethodBodies(unit);
            // fault in fields & methods
            if ((unit.scope) != null) {
                unit.scope.faultInTypes();
            }
            // verify inherited methods
            if ((unit.scope) != null) {
                unit.scope.verifyMethods(lookupEnvironment.methodVerifier());
            }
            // type checking
            unit.resolve();
            // flow analysis
            unit.analyseCode();
            unit.ignoreFurtherInvestigation = false;
            requestor.acceptResult(unit.compilationResult);
        }
        java.util.ArrayList<org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration> unitsToReturn = new java.util.ArrayList<org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration>();
        for (org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration cud : this.unitsToProcess) {
            if (cud != null) {
                unitsToReturn.add(cud);
            }
        }
        return unitsToReturn.toArray(new org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration[unitsToReturn.size()]);
    }
}

