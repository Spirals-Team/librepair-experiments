package spoon.support.compiler.jdt;


public class ExtendedStringLiteralTest {
    @org.junit.Test
    public void testExtendedStringLiteral() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher() {
            @java.lang.Override
            public spoon.SpoonModelBuilder createCompiler() {
                return new spoon.support.compiler.jdt.JDTBasedSpoonCompiler(getFactory()) {
                    @java.lang.Override
                    protected spoon.support.compiler.jdt.JDTBatchCompiler createBatchCompiler() {
                        return new spoon.support.compiler.jdt.JDTBatchCompiler(this) {
                            @java.lang.Override
                            public org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration[] getUnits() {
                                startTime = java.lang.System.currentTimeMillis();
                                org.eclipse.jdt.internal.compiler.env.INameEnvironment environment = this.jdtCompiler.environment;
                                if (environment == null) {
                                    environment = getLibraryAccess();
                                }
                                org.eclipse.jdt.internal.compiler.impl.CompilerOptions compilerOptions = new org.eclipse.jdt.internal.compiler.impl.CompilerOptions(this.options);
                                compilerOptions.parseLiteralExpressionsAsConstants = true;
                                spoon.support.compiler.jdt.TreeBuilderCompiler treeBuilderCompiler = new spoon.support.compiler.jdt.TreeBuilderCompiler(environment, getHandlingPolicy(), compilerOptions, this.jdtCompiler.requestor, getProblemFactory(), this.out, null);
                                org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration[] units = treeBuilderCompiler.buildUnits(getCompilationUnits());
                                return units;
                            }
                        };
                    }
                };
            }
        };
        spoon.SpoonModelBuilder comp = launcher.createCompiler();
        comp.addInputSources(spoon.compiler.SpoonResourceHelper.resources((("./src/test/java/" + (spoon.support.compiler.jdt.ExtendedStringLiteralTestClass.class.getCanonicalName().replace('.', '/'))) + ".java")));
        comp.build();
        spoon.reflect.declaration.CtClass<?> cl = comp.getFactory().Package().get("spoon.support.compiler.jdt").getType("ExtendedStringLiteralTestClass");
        spoon.reflect.declaration.CtField<?> f = cl.getField("extendedStringLiteral");
        spoon.reflect.code.CtExpression<?> de = f.getDefaultExpression();
        org.junit.Assert.assertEquals("\"hello world!\"", de.toString());
    }
}

