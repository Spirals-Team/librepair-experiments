package spoon.test.module;


public class TestModule {
    private static final java.lang.String MODULE_RESOURCES_PATH = "./src/test/resources/spoon/test/module";

    @org.junit.BeforeClass
    public static void setUp() throws java.io.IOException {
        java.io.File directory = new java.io.File(spoon.test.module.TestModule.MODULE_RESOURCES_PATH);
        try (java.util.stream.Stream<java.nio.file.Path> paths = java.nio.file.Files.walk(directory.toPath())) {
            paths.forEach(( path) -> {
                if (path.toFile().getName().equals("module-info-tpl")) {
                    try {
                        java.nio.file.Files.copy(path, new java.io.File(path.getParent().toFile(), "module-info.java").toPath());
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @org.junit.AfterClass
    public static void tearDown() throws java.io.IOException {
        java.io.File directory = new java.io.File(spoon.test.module.TestModule.MODULE_RESOURCES_PATH);
        try (java.util.stream.Stream<java.nio.file.Path> paths = java.nio.file.Files.walk(directory.toPath())) {
            paths.forEach(( path) -> {
                if (path.toFile().getName().equals("module-info.java")) {
                    try {
                        java.nio.file.Files.delete(path);
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @org.junit.Test
    public void testCompleteModuleInfoContentNoClasspath() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/spoon/test/module/simple_module/module-info.java");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.getEnvironment().setComplianceLevel(9);
        launcher.buildModel();
        org.junit.Assert.assertEquals(2, launcher.getModel().getAllModules().size());
        spoon.reflect.declaration.CtModule unnamedModule = launcher.getFactory().Module().getOrCreate(spoon.reflect.declaration.CtModule.TOP_LEVEL_MODULE_NAME);
        org.junit.Assert.assertSame(unnamedModule, launcher.getModel().getUnnamedModule());
        spoon.reflect.declaration.CtModule moduleGreetings = launcher.getFactory().Module().getOrCreate("simple_module");
        org.junit.Assert.assertEquals("simple_module", moduleGreetings.getSimpleName());
        org.junit.Assert.assertEquals(7, moduleGreetings.getModuleDirectives().size());
        java.util.List<spoon.reflect.declaration.CtModuleRequirement> requiredModules = moduleGreetings.getRequiredModules();
        org.junit.Assert.assertEquals(1, requiredModules.size());
        spoon.reflect.declaration.CtModuleRequirement moduleRequirement = requiredModules.get(0);
        org.junit.Assert.assertEquals("java.logging", moduleRequirement.getModuleReference().getSimpleName());
        org.junit.Assert.assertTrue(moduleRequirement.getRequiresModifiers().contains(spoon.reflect.declaration.CtModuleRequirement.RequiresModifier.TRANSITIVE));
        java.util.List<spoon.reflect.declaration.CtPackageExport> moduleExports = moduleGreetings.getExportedPackages();
        org.junit.Assert.assertEquals(1, moduleExports.size());
        org.junit.Assert.assertEquals("com.greetings.pkg", moduleExports.get(0).getPackageReference().getQualifiedName());
        org.junit.Assert.assertEquals(2, moduleExports.get(0).getTargetExport().size());
        for (spoon.reflect.reference.CtModuleReference target : moduleExports.get(0).getTargetExport()) {
            if ((!(target.getSimpleName().equals("com.other.module"))) && (!(target.getSimpleName().equals("com.second.module")))) {
                org.junit.Assert.fail();
            }
        }
        java.util.List<spoon.reflect.declaration.CtPackageExport> moduleOpened = moduleGreetings.getOpenedPackages();
        org.junit.Assert.assertEquals(2, moduleOpened.size());
        spoon.reflect.declaration.CtPackageExport openedFirst = moduleOpened.get(0);
        spoon.reflect.declaration.CtPackageExport openedSecond = moduleOpened.get(1);
        org.junit.Assert.assertEquals("com.greetings.otherpkg", openedFirst.getPackageReference().getSimpleName());
        org.junit.Assert.assertTrue(openedFirst.getTargetExport().isEmpty());
        org.junit.Assert.assertEquals("com.greetings.openpkg", openedSecond.getPackageReference().getSimpleName());
        org.junit.Assert.assertEquals(1, openedSecond.getTargetExport().size());
        org.junit.Assert.assertEquals("com.third.module", openedSecond.getTargetExport().iterator().next().getSimpleName());
        java.util.List<spoon.reflect.declaration.CtUsedService> consumedService = moduleGreetings.getUsedServices();
        org.junit.Assert.assertEquals(1, consumedService.size());
        org.junit.Assert.assertEquals("com.greetings.pkg.ConsumedService", consumedService.get(0).getServiceType().getQualifiedName());
        java.util.List<spoon.reflect.declaration.CtProvidedService> providedServices = moduleGreetings.getProvidedServices();
        org.junit.Assert.assertEquals(2, providedServices.size());
        spoon.reflect.declaration.CtProvidedService providedService1 = providedServices.get(0);
        spoon.reflect.declaration.CtProvidedService providedService2 = providedServices.get(1);
        org.junit.Assert.assertEquals("com.greetings.pkg.ConsumedService", providedService1.getServiceType().getQualifiedName());
        org.junit.Assert.assertEquals(2, providedService1.getImplementationTypes().size());
        org.junit.Assert.assertEquals("com.greetings.pkg.ProvidedClass1", providedService1.getImplementationTypes().get(0).getQualifiedName());
        org.junit.Assert.assertEquals("com.greetings.otherpkg.ProvidedClass2", providedService1.getImplementationTypes().get(1).getQualifiedName());
        org.junit.Assert.assertEquals("java.logging.Service", providedService2.getServiceType().getQualifiedName());
        org.junit.Assert.assertEquals(1, providedService2.getImplementationTypes().size());
        org.junit.Assert.assertEquals("com.greetings.logging.Logger", providedService2.getImplementationTypes().get(0).getQualifiedName());
    }

    @org.junit.Test
    public void testModuleInfoShouldBeCorrectlyPrettyPrinted() throws java.io.IOException {
        java.io.File input = new java.io.File("./src/test/resources/spoon/test/module/simple_module/module-info.java");
        java.io.File output = new java.io.File("./target/spoon-module");
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.getEnvironment().setComplianceLevel(9);
        launcher.setSourceOutputDirectory(output.getPath());
        launcher.addInputResource(input.getPath());
        launcher.run();
        org.junit.Assert.assertEquals(2, launcher.getModel().getAllModules().size());
        try (java.util.stream.Stream<java.nio.file.Path> files = java.nio.file.Files.list(output.toPath())) {
            org.junit.Assert.assertEquals(2, files.count());
        }
        java.io.File fileOuput = new java.io.File(output, "simple_module/module-info.java");
        java.util.List<java.lang.String> originalLines = java.nio.file.Files.readAllLines(input.toPath());
        java.util.List<java.lang.String> createdLines = java.nio.file.Files.readAllLines(fileOuput.toPath());
        org.junit.Assert.assertEquals(originalLines.size(), createdLines.size());
        for (int i = 0; i < (originalLines.size()); i++) {
            org.junit.Assert.assertEquals(originalLines.get(i), createdLines.get(i));
        }
    }

    @org.junit.Test
    public void testModuleInfoWithComments() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.getEnvironment().setComplianceLevel(9);
        launcher.getEnvironment().setCommentEnabled(true);
        launcher.addInputResource(((spoon.test.module.TestModule.MODULE_RESOURCES_PATH) + "/module_with_comments/module-info.java"));
        launcher.buildModel();
        org.junit.Assert.assertEquals(2, launcher.getModel().getAllModules().size());
        spoon.reflect.declaration.CtModule module = launcher.getFactory().Module().getModule("module_with_comments");
        org.junit.Assert.assertNotNull(module);
        org.junit.Assert.assertTrue(module.isOpenModule());
        java.util.List<spoon.reflect.code.CtComment> comments = module.getComments();
        org.junit.Assert.assertEquals(1, comments.size());
        spoon.reflect.code.CtComment comment = comments.get(0);
        org.junit.Assert.assertEquals("This is the main module of the application", comment.getContent());
        org.junit.Assert.assertEquals(spoon.reflect.code.CtComment.CommentType.JAVADOC, comment.getCommentType());
        org.junit.Assert.assertEquals(3, module.getModuleDirectives().size());
        spoon.reflect.declaration.CtModuleRequirement moduleRequirement = module.getRequiredModules().get(0);
        comments = moduleRequirement.getComments();
        org.junit.Assert.assertEquals(1, comments.size());
        comment = comments.get(0);
        org.junit.Assert.assertEquals("this is needed for logging stuff", comment.getContent());
        org.junit.Assert.assertEquals(spoon.reflect.code.CtComment.CommentType.INLINE, comment.getCommentType());
        spoon.reflect.declaration.CtProvidedService providedService = module.getProvidedServices().get(0);
        comments = providedService.getComments();
        org.junit.Assert.assertEquals(1, comments.size());
        comment = comments.get(0);
        org.junit.Assert.assertEquals("A specific implementation", comment.getContent());
        org.junit.Assert.assertEquals(spoon.reflect.code.CtComment.CommentType.JAVADOC, comment.getCommentType());
        spoon.reflect.declaration.CtUsedService usedService = module.getUsedServices().get(0);
        comments = usedService.getComments();
        org.junit.Assert.assertEquals(1, comments.size());
        comment = comments.get(0);
        org.junit.Assert.assertEquals("A simple implementation", comment.getContent());
        org.junit.Assert.assertEquals(spoon.reflect.code.CtComment.CommentType.BLOCK, comment.getCommentType());
    }

    @org.junit.Test
    public void testDirectiveOrders() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.getEnvironment().setComplianceLevel(9);
        launcher.addInputResource(((spoon.test.module.TestModule.MODULE_RESOURCES_PATH) + "/module_with_comments/module-info.java"));
        launcher.buildModel();
        org.junit.Assert.assertEquals(2, launcher.getModel().getAllModules().size());
        spoon.reflect.declaration.CtModule module = launcher.getFactory().Module().getModule("module_with_comments");
        org.junit.Assert.assertNotNull(module);
        java.util.List<spoon.reflect.declaration.CtModuleDirective> moduleDirectives = module.getModuleDirectives();
        org.junit.Assert.assertEquals(3, moduleDirectives.size());
        org.junit.Assert.assertTrue(((moduleDirectives.get(0)) instanceof spoon.reflect.declaration.CtModuleRequirement));
        org.junit.Assert.assertTrue(((moduleDirectives.get(1)) instanceof spoon.reflect.declaration.CtProvidedService));
        org.junit.Assert.assertTrue(((moduleDirectives.get(2)) instanceof spoon.reflect.declaration.CtUsedService));
    }

    @org.junit.Test
    public void testGetParentOfRootPackageOfModule() {
        final spoon.Launcher launcher = new spoon.Launcher();
        spoon.reflect.declaration.CtModule unnamedModule = launcher.getFactory().getModel().getUnnamedModule();
        org.junit.Assert.assertSame(unnamedModule, unnamedModule.getRootPackage().getParent());
    }

    @org.junit.Test
    public void testGetModuleAfterChangingItsName() {
        final spoon.Launcher launcher = new spoon.Launcher();
        spoon.reflect.declaration.CtModule module = launcher.getFactory().Module().getOrCreate("myModule");
        module.setSimpleName("newName");
        spoon.reflect.declaration.CtModule moduleNewName = launcher.getFactory().Module().getOrCreate("newName");
        org.junit.Assert.assertSame(module, moduleNewName);
    }

    @org.junit.Ignore
    @org.junit.Test
    public void testSimpleModuleCanBeBuiltAndCompiled() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setShouldCompile(true);
        launcher.getEnvironment().setComplianceLevel(9);
        launcher.addInputResource("./src/test/resources/spoon/test/module/simple_module_with_code");
        launcher.run();
        org.junit.Assert.assertEquals(2, launcher.getModel().getAllModules().size());
        org.junit.Assert.assertEquals(1, launcher.getModel().getAllTypes().size());
    }

    @org.junit.Ignore
    @org.junit.Test
    public void testMultipleModulesAndParents() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setComplianceLevel(9);
        launcher.addInputResource(((spoon.test.module.TestModule.MODULE_RESOURCES_PATH) + "/code-multiple-modules"));
        launcher.run();
        org.junit.Assert.assertEquals(3, launcher.getModel().getAllModules().size());
        spoon.reflect.declaration.CtType barclass = launcher.getFactory().Type().get("packbar.BarClass");
        org.junit.Assert.assertNotNull(barclass);
        org.junit.Assert.assertTrue(((barclass.getParent()) instanceof spoon.reflect.declaration.CtPackage));
        spoon.reflect.declaration.CtPackage packBar = ((spoon.reflect.declaration.CtPackage) (barclass.getParent()));
        org.junit.Assert.assertTrue(((packBar.getParent()) instanceof spoon.reflect.declaration.CtModule));
    }
}

