package spoon.processing;


public class CtGenerationTest {
    @org.junit.Test
    public void testGenerateReplacementVisitor() throws java.lang.Exception {
        java.lang.System.setProperty("line.separator", "\n");
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setAutoImports(false);
        launcher.getEnvironment().setNoClasspath(true);
        launcher.getEnvironment().setCommentEnabled(true);
        launcher.getEnvironment().useTabulations(true);
        launcher.setSourceOutputDirectory("./target/generated/");
        launcher.addInputResource("./src/main/java/spoon/reflect/code");
        launcher.addInputResource("./src/main/java/spoon/reflect/declaration");
        launcher.addInputResource("./src/main/java/spoon/reflect/reference");
        launcher.addInputResource("./src/main/java/spoon/reflect/internal");
        launcher.addInputResource("./src/main/java/spoon/reflect/visitor/CtScanner.java");
        try (java.util.stream.Stream<java.nio.file.Path> files = java.nio.file.Files.list(new java.io.File("./src/main/java/spoon/support/visitor/replace/").toPath())) {
            files.forEach(( path) -> {
                if (!(path.getFileName().toString().endsWith("ReplacementVisitor.java"))) {
                    launcher.addInputResource(path.toString());
                }
            });
        }
        launcher.addInputResource("./src/test/java/spoon/generating/replace/ReplacementVisitor.java");
        launcher.addProcessor(new spoon.generating.ReplacementVisitorGenerator());
        launcher.setOutputFilter(new spoon.processing.CtGenerationTest.RegexFilter("spoon.support.visitor.replace.*"));
        launcher.run();
        spoon.reflect.declaration.CtClass<java.lang.Object> actual = spoon.testing.utils.ModelUtils.build(new java.io.File(((launcher.getModelBuilder().getSourceOutputDirectory()) + "/spoon/support/visitor/replace/ReplacementVisitor.java"))).Class().get("spoon.support.visitor.replace.ReplacementVisitor");
        spoon.reflect.declaration.CtClass<java.lang.Object> expected = spoon.testing.utils.ModelUtils.build(new java.io.File("./src/main/java/spoon/support/visitor/replace/ReplacementVisitor.java")).Class().get("spoon.support.visitor.replace.ReplacementVisitor");
        try {
            spoon.testing.Assert.assertThat(actual).isEqualTo(expected);
        } catch (java.lang.AssertionError e) {
            throw new org.junit.ComparisonFailure("ReplacementVisitor different", expected.toString(), actual.toString());
        }
    }

    @org.junit.Test
    public void testGenerateCtBiScanner() throws java.lang.Exception {
        java.lang.System.setProperty("line.separator", "\n");
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.getEnvironment().setCommentEnabled(true);
        launcher.getEnvironment().useTabulations(true);
        launcher.setSourceOutputDirectory("./target/generated/");
        launcher.addInputResource("./src/main/java/spoon/reflect/code");
        launcher.addInputResource("./src/main/java/spoon/reflect/declaration");
        launcher.addInputResource("./src/main/java/spoon/reflect/reference");
        launcher.addInputResource("./src/main/java/spoon/reflect/internal");
        launcher.addInputResource("./src/main/java/spoon/reflect/visitor/CtScanner.java");
        launcher.addInputResource("./src/test/java/spoon/generating/scanner/");
        launcher.addProcessor(new spoon.generating.CtBiScannerGenerator());
        launcher.setOutputFilter(new spoon.processing.CtGenerationTest.RegexFilter("spoon.reflect.visitor.CtBiScannerDefault"));
        launcher.run();
        spoon.testing.Assert.assertThat(spoon.testing.utils.ModelUtils.build(new java.io.File("./src/main/java/spoon/reflect/visitor/CtBiScannerDefault.java")).Class().get(spoon.reflect.visitor.CtBiScannerDefault.class)).isEqualTo(spoon.testing.utils.ModelUtils.build(new java.io.File("./target/generated/spoon/reflect/visitor/CtBiScannerDefault.java")).Class().get(spoon.reflect.visitor.CtBiScannerDefault.class));
    }

    @org.junit.Test
    public void testGenerateCloneVisitor() throws java.lang.Exception {
        java.lang.System.setProperty("line.separator", "\n");
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.getEnvironment().setCommentEnabled(true);
        launcher.getEnvironment().useTabulations(true);
        launcher.setSourceOutputDirectory("./target/generated/");
        launcher.addInputResource("./src/main/java/spoon/reflect/code");
        launcher.addInputResource("./src/main/java/spoon/reflect/declaration");
        launcher.addInputResource("./src/main/java/spoon/reflect/reference");
        launcher.addInputResource("./src/main/java/spoon/reflect/internal");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/code");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/declaration");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/reference");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/internal");
        launcher.addInputResource("./src/main/java/spoon/reflect/visitor/CtScanner.java");
        launcher.addInputResource("./src/main/java/spoon/reflect/visitor/CtInheritanceScanner.java");
        launcher.addInputResource("./src/test/java/spoon/generating/clone/");
        launcher.addProcessor(new spoon.generating.CloneVisitorGenerator());
        launcher.setOutputFilter(new spoon.processing.CtGenerationTest.RegexFilter("spoon.support.visitor.clone.*"));
        launcher.run();
        spoon.reflect.declaration.CtClass<java.lang.Object> actual = spoon.testing.utils.ModelUtils.build(new java.io.File(((launcher.getModelBuilder().getSourceOutputDirectory()) + "/spoon/support/visitor/clone/CloneBuilder.java"))).Class().get("spoon.support.visitor.clone.CloneBuilder");
        spoon.reflect.declaration.CtClass<java.lang.Object> expected = spoon.testing.utils.ModelUtils.build(new java.io.File("./src/main/java/spoon/support/visitor/clone/CloneBuilder.java")).Class().get("spoon.support.visitor.clone.CloneBuilder");
        try {
            spoon.testing.Assert.assertThat(actual).isEqualTo(expected);
        } catch (java.lang.AssertionError e) {
            throw new org.junit.ComparisonFailure("CloneBuilder different", expected.toString(), actual.toString());
        }
        actual = spoon.testing.utils.ModelUtils.build(new java.io.File(((launcher.getModelBuilder().getSourceOutputDirectory()) + "/spoon/support/visitor/clone/CloneVisitor.java"))).Class().get("spoon.support.visitor.clone.CloneVisitor");
        expected = spoon.testing.utils.ModelUtils.build(new java.io.File("./src/main/java/spoon/support/visitor/clone/CloneVisitor.java")).Class().get("spoon.support.visitor.clone.CloneVisitor");
        try {
            spoon.testing.Assert.assertThat(actual).isEqualTo(expected);
        } catch (java.lang.AssertionError e) {
            throw new org.junit.ComparisonFailure("CloneVisitor different", expected.toString(), actual.toString());
        }
    }

    @org.junit.Test
    public void testGenerateRoleHandler() throws java.lang.Exception {
        java.lang.System.setProperty("line.separator", "\n");
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setAutoImports(true);
        launcher.getEnvironment().setNoClasspath(true);
        launcher.getEnvironment().setCommentEnabled(true);
        launcher.getEnvironment().setCopyResources(false);
        launcher.getEnvironment().useTabulations(true);
        launcher.setSourceOutputDirectory("./target/generated/");
        launcher.addInputResource("./src/main/java/spoon/reflect/code");
        launcher.addInputResource("./src/main/java/spoon/reflect/declaration");
        launcher.addInputResource("./src/main/java/spoon/reflect/reference");
        launcher.addInputResource("./src/main/java/spoon/reflect/internal");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/code");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/declaration");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/reference");
        launcher.addInputResource("./src/test/java/spoon/generating/meta");
        launcher.addInputResource("./src/main/java/spoon/reflect/meta/impl/AbstractRoleHandler.java");
        launcher.addProcessor(new spoon.generating.RoleHandlersGenerator());
        launcher.setOutputFilter(new spoon.processing.CtGenerationTest.RegexFilter((("\\Q" + (spoon.generating.RoleHandlersGenerator.TARGET_PACKAGE)) + ".ModelRoleHandlers\\E.*")));
        launcher.run();
        spoon.reflect.declaration.CtClass<java.lang.Object> actual = spoon.testing.utils.ModelUtils.build(new java.io.File(((launcher.getModelBuilder().getSourceOutputDirectory()) + "/spoon/reflect/meta/impl/ModelRoleHandlers.java"))).Class().get("spoon.reflect.meta.impl.ModelRoleHandlers");
        spoon.reflect.declaration.CtClass<java.lang.Object> expected = spoon.testing.utils.ModelUtils.build(new java.io.File("./src/main/java/spoon/reflect/meta/impl/ModelRoleHandlers.java")).Class().get("spoon.reflect.meta.impl.ModelRoleHandlers");
        try {
            spoon.testing.Assert.assertThat(actual).isEqualTo(expected);
        } catch (java.lang.AssertionError e) {
            throw new org.junit.ComparisonFailure("ModelRoleHandlers different", expected.toString(), actual.toString());
        }
    }

    private class RegexFilter implements spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtType<?>> {
        private final java.util.regex.Pattern regex;

        private RegexFilter(java.lang.String regex) {
            if (regex == null) {
                throw new java.lang.IllegalArgumentException();
            }
            this.regex = java.util.regex.Pattern.compile(regex);
        }

        public boolean matches(spoon.reflect.declaration.CtType<?> element) {
            java.util.regex.Matcher m = regex.matcher(element.getQualifiedName());
            return m.matches();
        }
    }
}

