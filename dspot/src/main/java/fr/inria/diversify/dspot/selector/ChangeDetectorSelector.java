package fr.inria.diversify.dspot.selector;

import fr.inria.diversify.automaticbuilder.AutomaticBuilderFactory;
import fr.inria.diversify.dspot.support.DSpotCompiler;
import fr.inria.diversify.utils.AmplificationChecker;
import fr.inria.diversify.utils.AmplificationHelper;
import fr.inria.diversify.utils.DSpotUtils;
import fr.inria.diversify.utils.Initializer;
import fr.inria.diversify.utils.sosiefier.InputConfiguration;
import fr.inria.diversify.utils.sosiefier.InputProgram;
import fr.inria.stamp.minimization.GeneralMinimizer;
import fr.inria.stamp.minimization.Minimizer;
import fr.inria.stamp.test.launcher.TestLauncher;
import fr.inria.stamp.test.listener.TestListener;
import org.codehaus.plexus.util.FileUtils;
import org.junit.runner.notification.Failure;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtNamedElement;
import spoon.reflect.declaration.CtType;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 09/08/17
 */
public class ChangeDetectorSelector implements TestSelector {

    private String pathToChangedVersionOfProgram;

    private Map<CtMethod<?>, Failure> failurePerAmplifiedTest;

    private InputConfiguration configuration;

    private InputProgram program;

    private CtType<?> currentClassTestToBeAmplified;

    public ChangeDetectorSelector() {
        this.failurePerAmplifiedTest = new HashMap<>();
    }

    @Override
    public void init(InputConfiguration configuration) {
        this.configuration = configuration;
        this.program = this.configuration.getInputProgram();
        final String configurationPath = configuration.getProperty("configPath");
        final String pathToFolder = configuration.getProperty("folderPath");
        InputConfiguration inputConfiguration;
        try {
            inputConfiguration = new InputConfiguration(configurationPath);
            InputProgram inputProgram = InputConfiguration.initInputProgram(inputConfiguration);
            inputConfiguration.setInputProgram(inputProgram);
            this.pathToChangedVersionOfProgram = pathToFolder +
                    DSpotUtils.shouldAddSeparator.apply(pathToFolder) +
                    (inputConfiguration.getProperty("targetModule") != null ?
                            inputConfiguration.getProperty("targetModule") +
                                    DSpotUtils.shouldAddSeparator.apply(pathToFolder)
                            : "");
            inputProgram.setProgramDir(this.pathToChangedVersionOfProgram);
            Initializer.initialize(configuration, inputProgram);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CtMethod<?>> selectToAmplify(List<CtMethod<?>> testsToBeAmplified) {
        if (this.currentClassTestToBeAmplified == null && !testsToBeAmplified.isEmpty()) {
            this.currentClassTestToBeAmplified = testsToBeAmplified.get(0).getDeclaringType();
            this.failurePerAmplifiedTest.clear();
        }
        return testsToBeAmplified;
    }


    @Override
    public List<CtMethod<?>> selectToKeep(List<CtMethod<?>> amplifiedTestToBeKept) {
        if (amplifiedTestToBeKept.isEmpty()) {
            return amplifiedTestToBeKept;
        }
        CtType clone = this.currentClassTestToBeAmplified.clone();
        clone.setParent(this.currentClassTestToBeAmplified.getParent());
        this.currentClassTestToBeAmplified.getMethods().stream()
                .filter(AmplificationChecker::isTest)
                .forEach(clone::removeMethod);
        amplifiedTestToBeKept.forEach(clone::addMethod);

        DSpotUtils.printCtTypeToGivenDirectory(clone, new File(DSpotCompiler.pathToTmpTestSources));
        final String classpath = AutomaticBuilderFactory
                .getAutomaticBuilder(this.configuration)
                .buildClasspath(this.program.getProgramDir())
                + AmplificationHelper.PATH_SEPARATOR + "target/dspot/dependencies/";

        DSpotCompiler.compile(DSpotCompiler.pathToTmpTestSources,
                classpath
                        + AmplificationHelper.PATH_SEPARATOR +
                        this.program.getProgramDir() + "/" + this.program.getClassesDir()
                        + AmplificationHelper.PATH_SEPARATOR +
                        this.program.getProgramDir() + "/" + this.program.getTestClassesDir(),
                new File(this.pathToChangedVersionOfProgram + "/" + this.program.getTestClassesDir()));

        final TestListener results = TestLauncher.run(this.configuration,
                classpath + AmplificationHelper.PATH_SEPARATOR +
                        this.pathToChangedVersionOfProgram + "/" + this.program.getClassesDir()
                        + AmplificationHelper.PATH_SEPARATOR +
                        this.pathToChangedVersionOfProgram + "/" + this.program.getTestClassesDir(),
                clone, amplifiedTestToBeKept.stream()
                        .map(CtNamedElement::getSimpleName)
                        .collect(Collectors.toList())
        );
        if (!results.getFailingTests().isEmpty()) {
            results.getFailingTests()
                    .forEach(failure ->
                            this.failurePerAmplifiedTest.put(
                                    amplifiedTestToBeKept.stream()
                                            .filter(ctMethod ->
                                                    ctMethod.getSimpleName().equals(failure.getDescription().getMethodName())
                                            ).findFirst()
                                            .get(), failure)
                    );
        }
        return amplifiedTestToBeKept;
    }

    @Override
    public List<CtMethod<?>> getAmplifiedTestCases() {
        return new ArrayList<>(this.failurePerAmplifiedTest.keySet());
    }

    @Override
    public Minimizer getMinimizer() {
        return new GeneralMinimizer();
    }

    protected void reset() {
        this.currentClassTestToBeAmplified = null;
    }

    @Override
    public void report() {
        final String output = "======= REPORT =======" + AmplificationHelper.LINE_SEPARATOR +
                this.failurePerAmplifiedTest.size() + " amplified test fails on the new versions." +
                AmplificationHelper.LINE_SEPARATOR +
                this.failurePerAmplifiedTest.keySet()
                        .stream()
                        .reduce("",
                                (acc, amplifiedTest) -> acc +
                                        this.failurePerAmplifiedTest.get(amplifiedTest).toString() +
                                        AmplificationHelper.LINE_SEPARATOR,
                                String::concat);
        System.out.println(output);
        try {
            FileUtils.forceMkdir(new File(this.configuration.getOutputDirectory() + "/" +
                    this.currentClassTestToBeAmplified.getQualifiedName()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try (FileWriter writer = new FileWriter(new File(this.configuration.getOutputDirectory() + "/" +
                this.currentClassTestToBeAmplified.getQualifiedName() + "_change_report.txt"))) {
            writer.write(output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try (FileWriter writer = new FileWriter(new File(this.configuration.getOutputDirectory() + "/" +
                this.currentClassTestToBeAmplified.getQualifiedName() + "_stacktraces.txt"))) {
            final PrintWriter printWriter = new PrintWriter(writer);
            this.failurePerAmplifiedTest.keySet()
                    .forEach(amplifiedTest -> this.failurePerAmplifiedTest.get(amplifiedTest)
                            .getException()
                            .printStackTrace(printWriter)
                    );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.reset();
    }
}
