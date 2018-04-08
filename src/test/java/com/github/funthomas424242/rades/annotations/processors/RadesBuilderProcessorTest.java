package com.github.funthomas424242.rades.annotations.processors;

import com.github.funthomas424242.rades.annotations.lang.java.JavaModelServiceProvider;
import com.github.funthomas424242.rades.annotations.lang.java.JavaSrcFileCreator;
import com.google.common.truth.ExpectFailure;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import javax.annotation.processing.Filer;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github.funthomas424242.rades.annotations.processors.AssertionHelper.assertThat;
import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.Compiler.javac;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class RadesBuilderProcessorTest {

    protected final String NONE_WRITEABLE_BUILDER_JAVA = "package com.github.funthomas424242.domain;\n" +
            "import javax.annotation.Generated;\n" +
            "import org.apache.commons.lang3.StringUtils;\n" +
            "\n" +
            "import javax.validation.ConstraintViolation;\n" +
            "import javax.validation.Validation;\n" +
            "import javax.validation.ValidationException;\n" +
            "import javax.validation.Validator;\n" +
            "\n" +
            "@Generated(value=\"com.github.funthomas424242.rades.annotations.processors.RadesBuilderProcessor\"\n" +
            ", comments=\"com.github.funthomas424242.domain.NoneWriteable\")\n" +
            "public class NoneWriteableBuilder {\n" +
            "\n" +
            "    private NoneWriteable noneWriteable;\n" +
            "\n" +
            "    public NoneWriteableBuilder(){\n" +
            "        this(new NoneWriteable());\n" +
            "    }\n" +
            "\n" +
            "    public NoneWriteableBuilder( final NoneWriteable noneWriteable ){\n" +
            "        this.noneWriteable = noneWriteable;\n" +
            "    }\n" +
            "\n" +
            "    public NoneWriteable build() {\n" +
            "        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();\n" +
            "        final java.util.Set<ConstraintViolation<NoneWriteable>> constraintViolations = validator.validate(this.noneWriteable);\n" +
            "\n" +
            "        if (constraintViolations.size() > 0) {\n" +
            "            java.util.Set<String> violationMessages = new java.util.HashSet<String>();\n" +
            "\n" +
            "            for (ConstraintViolation<?> constraintViolation : constraintViolations) {\n" +
            "                violationMessages.add(constraintViolation.getPropertyPath() + \": \" + constraintViolation.getMessage());\n" +
            "            }\n" +
            "\n" +
            "            throw new ValidationException(\"NoneWriteable is not valid:\\n\" + StringUtils.join(violationMessages, \"\\n\"));\n" +
            "        }\n" +
            "        final NoneWriteable value = this.noneWriteable;\n" +
            "        this.noneWriteable = null;\n" +
            "        return value;\n" +
            "    }\n" +
            "\n" +
            "}\n";


    protected static final String TEST_SRC_FOLDER = "src/test/java/";
    protected static final String TEST_EXPECTATION_FOLDER = "src/test/expectations/";

    protected static URL urlPersonJava;
    protected static URL urlPersonBuilderJava;
    protected static URL urlFirmaJava;
    protected static URL urlFirmaBuilderJava;
    protected static URL urlAutoJava;
    protected static URL urlAutoBuilderJava;
    protected static URL urlMetaAnnotationJava;
    protected static URL urlNonePackageClassJava;
    protected static URL urlNoneWriteableBuilderJava;


    @Rule
    public final ExpectFailure expectFailure = new ExpectFailure();

    static class DefaultJavaModelProvider extends JavaModelServiceProvider {
        @Override
        public String getNowAsISOString() {
            return "2018-04-06T20:36:46.750";
        }
    }


    static class NoWritableJavaModelProvider extends JavaModelServiceProvider {
        @Override
        public JavaSrcFileCreator getJavaSrcFileCreator(final Filer filer, final String className) {
            final JavaSrcFileCreator creator = new JavaSrcFileCreator(filer, className, this) {
                @Override
                public void init() throws IOException {
                    throw new IOException("Medium ist schreibgeschützt!");
                }
            };
            return creator;
        }
    }

    static class NoClosebleJavaModelProvider extends JavaModelServiceProvider {
        @Override
        public JavaSrcFileCreator getJavaSrcFileCreator(final Filer filer, final String className) {
            final JavaSrcFileCreator creator = new JavaSrcFileCreator(filer, className, this) {
                @Override
                public void close() throws Exception {
                    throw new Exception("Kann Stream nicht schließen!");
                }
            };
            return creator;
        }
    }


    protected static URL getResourceURL(String projectSrcRoot, String resourcePath) throws MalformedURLException {
        final Path tmpPath = Paths.get(projectSrcRoot + resourcePath);
        final URL resourceURL = tmpPath.toAbsolutePath().toUri().toURL();
        return resourceURL;
    }


    @BeforeClass
    public static void setUp() throws MalformedURLException {
        urlPersonJava = getResourceURL(TEST_SRC_FOLDER, "com/github/funthomas424242/domain/Person.java");
        urlPersonBuilderJava = getResourceURL(TEST_EXPECTATION_FOLDER, "PersonBuilder.java");
        urlFirmaJava = getResourceURL(TEST_SRC_FOLDER, "com/github/funthomas424242/domain/Firma.java");
        urlFirmaBuilderJava = getResourceURL(TEST_EXPECTATION_FOLDER, "FirmaAGErbauer.java");
        urlAutoJava = getResourceURL(TEST_SRC_FOLDER, "com/github/funthomas424242/domain/Auto.java");
        urlAutoBuilderJava = getResourceURL(TEST_EXPECTATION_FOLDER, "CarBuilder.java");
        urlMetaAnnotationJava = getResourceURL(TEST_SRC_FOLDER, "com/github/funthomas424242/MetaAnnotation.java");
        urlNonePackageClassJava = getResourceURL(TEST_EXPECTATION_FOLDER, "NonePackageClass.java");
        urlNoneWriteableBuilderJava = getResourceURL(TEST_EXPECTATION_FOLDER, "NoneWriteableBuilder.java");
    }


    @Test
    public void processPerson() throws MalformedURLException {
        final RadesBuilderProcessor processor = new RadesBuilderProcessor();
        processor.setJavaModelService(new DefaultJavaModelProvider());

        final Compilation compilation = javac()
                .withProcessors(processor)
                .compile(JavaFileObjects.forResource(urlPersonJava));
        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("com.github.funthomas424242.domain.PersonBuilder")
                .hasSourceEquivalentTo(JavaFileObjects.forResource(urlPersonBuilderJava)
                );

        // equivalent mitl altem API
        final RadesBuilderProcessor processor1 = new RadesBuilderProcessor();
        processor1.setJavaModelService(new DefaultJavaModelProvider());

        assertAbout(javaSource())
                .that(JavaFileObjects.forResource(urlPersonJava))
                .processedWith(processor1)
                .compilesWithoutError()
                .and()
                .generatesSources(JavaFileObjects.forResource(urlPersonBuilderJava));


    }

    @Test
    public void processFirma() throws MalformedURLException {
        final RadesBuilderProcessor processor = new RadesBuilderProcessor();
        processor.setJavaModelService(new DefaultJavaModelProvider());

        final Compilation compilation = javac()
                .withProcessors(processor)
                .compile(JavaFileObjects.forResource(urlFirmaJava));
        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("com.github.funthomas424242.domain.FirmaAGErbauer")
                .hasSourceEquivalentTo(JavaFileObjects.forResource(urlFirmaBuilderJava)
                );
    }

    @Test
    public void processAuto() throws MalformedURLException {
        final RadesBuilderProcessor processor = new RadesBuilderProcessor();
        processor.setJavaModelService(new DefaultJavaModelProvider());

        final Compilation compilation = javac()
                .withProcessors(processor)
                .compile(JavaFileObjects.forResource(urlAutoJava));
        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("com.github.funthomas424242.domain.CarBuilder")
                .hasSourceEquivalentTo(JavaFileObjects.forResource(urlAutoBuilderJava)
                );
    }

    @Test
    public void shouldCompilePersonJavaWithoutErrors() {
        final RadesBuilderProcessor processor = new RadesBuilderProcessor();
        processor.setJavaModelService(new DefaultJavaModelProvider());

        assertAbout(javaSource())
                .that(JavaFileObjects.forResource(urlPersonJava))
                .processedWith(processor)
                .compilesWithoutError();

    }

    @Test
    public void shouldCompileMetaAnnotationJavaWithoutErrors() {
        final RadesBuilderProcessor processor = new RadesBuilderProcessor();
        processor.setJavaModelService(new DefaultJavaModelProvider());

        assertAbout(javaSource())
                .that(JavaFileObjects.forResource(urlMetaAnnotationJava))
                .processedWith(processor)
                .compilesWithoutError();

    }


    @Test
    public void shouldCompileNonPackageClassWithoutErrors() {
        final RadesBuilderProcessor processor = new RadesBuilderProcessor();
        processor.setJavaModelService(new DefaultJavaModelProvider());

        assertAbout(javaSource())
                .that(JavaFileObjects.forSourceString("NonePackageClass", "\n" +
                        "@com.github.funthomas424242.rades.annotations.RadesBuilder\n" +
                        "public class NonePackageClass {\n" +
                        "}\n"))
                .processedWith(processor)
                .compilesWithoutError();

    }


    @Test
    public void shouldFailToWriteNoneWriteableBuilderClass() {

        final RadesBuilderProcessor processor = new RadesBuilderProcessor();
        processor.setJavaModelService(new NoWritableJavaModelProvider());

        expectFailure.whenTesting()
                .about(javaSource())
                .that(JavaFileObjects.forSourceString("com.github.funthomas424242.domain.NoneWriteable", "\n" +
                        "package com.github.funthomas424242.domain;\n" +
                        "@com.github.funthomas424242.rades.annotations.RadesBuilder\n" +
                        "public class NoneWriteable {\n" +
                        "}\n"))
                .processedWith(processor)
                .compilesWithoutError()
                .and()
                .generatesFiles(JavaFileObjects.forSourceString(
                        "com.github.funthomas424242.domain.NoneWriteableBuilder", NONE_WRITEABLE_BUILDER_JAVA));

        final AssertionError expected = expectFailure.getFailure();
        assertThat(expected.getMessage()).contains("Did not find a generated file corresponding to com/github/funthomas424242/domain/NoneWriteableBuilder.java");
    }


    @Test(expected = NullPointerException.class)
    public void shouldFailToCloseNoneCloseableBuilderClass() {

        final RadesBuilderProcessor processor = new RadesBuilderProcessor();
        processor.setJavaModelService(new NoClosebleJavaModelProvider());

        assertAbout(javaSource())
                .that(JavaFileObjects.forResource(urlPersonJava))
                .processedWith(processor)
                .compilesWithoutError()
                .and()
                .generatesSources(JavaFileObjects.forResource(urlPersonBuilderJava));
    }
}