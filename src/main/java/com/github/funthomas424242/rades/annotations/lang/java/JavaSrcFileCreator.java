package com.github.funthomas424242.rades.annotations.lang.java;

import javax.annotation.processing.Filer;
import java.io.IOException;
import java.io.PrintWriter;

public class JavaSrcFileCreator implements AutoCloseable {

    protected final JavaModelService javaModelService;

    protected final Filer filer;

    protected final String className;

    protected PrintWriter writer;

    public JavaSrcFileCreator(final Filer filer, final String className, final JavaModelService javaModelService) {
        this.filer = filer;
        this.className = className;
        this.javaModelService=javaModelService;

    }

// Add if needed
//    public JavaSrcFileCreator(final Filer filer, final Element packageElement, final CharSequence className) throws IOException {
//        this.writer = new PrintWriter(filer.createSourceFile(className, packageElement).openWriter());
//    }


    public void init() throws IOException {
        this.writer = new PrintWriter(filer.createSourceFile(className).openWriter());
    }


    public void writeSetterMethod(String objectName, String builderSimpleClassName, String fieldName, String setterName, String argumentType) {
        writer.print("    public ");
        writer.print(builderSimpleClassName);
        writer.print(" ");
        writer.print(setterName);

        writer.print("( final ");

        writer.print(argumentType);
        writer.println(" " + fieldName + " ) {");
        writer.print("        this." + objectName + ".");
        writer.print(fieldName);
        writer.println(" = " + fieldName + ";");
        writer.println("        return this;");
        writer.println("    }");
        writer.println();
    }

    public void writeConstructors(String simpleClassName, String objectName, String builderSimpleClassName) {
        writer.print("    public " + builderSimpleClassName + "(){\n");
        writer.print("        this(new " + simpleClassName + "());\n");
        writer.print("    }\n");
        writer.print("\n");
        writer.print("    public " + builderSimpleClassName + "( final " + simpleClassName + " " + objectName + " ){\n");
        writer.print("        this." + objectName + " = " + objectName + ";\n");
        writer.print("    }\n");
        writer.println();
    }

    public void writeFieldDefinition(String simpleClassName, String objectName) {
        writer.print("    private ");
        writer.print(simpleClassName);
        writer.print(" " + objectName + ";\n\n");
    }

    public void writeClassDeclaration(String builderSimpleClassName) {
        writer.println("public class " + builderSimpleClassName + " {");
        writer.println();
    }

    public void writeBuildMethod(String simpleClassName, String objectName) {
        writer.print("    public ");
        writer.print(simpleClassName);
        writer.println(" build() {");
        writer.println("        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();");
        writer.println("        final java.util.Set<ConstraintViolation<" + simpleClassName + ">> constraintViolations = validator.validate(this." + objectName + ");");
        writer.println();
        writer.println("        if (constraintViolations.size() > 0) {");
        writer.println("            java.util.Set<String> violationMessages = new java.util.HashSet<String>();");
        writer.println();
        writer.println("            for (ConstraintViolation<?> constraintViolation : constraintViolations) {");
        writer.println("                violationMessages.add(constraintViolation.getPropertyPath() + \": \" + constraintViolation.getMessage());");
        writer.println("            }");
        writer.println();
        writer.println("            throw new ValidationException(\"" + simpleClassName + " is not valid:\\n\" + StringUtils.join(violationMessages, \"\\n\"));");
        writer.println("        }");
        writer.println("        final " + simpleClassName + " value = this." + objectName + ";");
        writer.println("        this." + objectName + " = null;");
        writer.println("        return value;");
        writer.println("    }");
        writer.println();
    }

    public void writeClassAnnotations(String className) {
        writer.print("@Generated(value=\"com.github.funthomas424242.rades.annotations.processors.RadesBuilderProcessor\"\n" +
                ", date=\"" + javaModelService.getNowAsISOString() + "\"\n" +
                ", comments=\"" + className + "\")\n"
        );
    }

    public void writePackage(final String packageName) {
        writer.print("package ");
        writer.println(packageName + ";");
    }

    public void writeImports() {
        writer.println("import javax.annotation.Generated;");
        writer.println("import org.apache.commons.lang3.StringUtils;\n");
        writer.println("import javax.validation.ConstraintViolation;");
        writer.println("import javax.validation.Validation;");
        writer.println("import javax.validation.ValidationException;");
        writer.println("import javax.validation.Validator;");
        writer.println();
    }

    public void writeClassFinal() {
        writer.println("}");
    }

    @Override
    public void close() throws Exception {
        writer.close();
    }
}

