package com.github.funthomas424242.rades.annotations.processors;

//import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SupportedAnnotationTypes("com.github.funthomas424242.rades.annotations.RadesBuilder")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
//@AutoService(Processor.class)
public class RadesBuilderProcessor extends AbstractProcessor {

    protected ProcessingEnvironment processingEnvironment;

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.processingEnvironment = processingEnv;
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        final Types types = this.processingEnvironment.getTypeUtils();

        for (TypeElement annotation : annotations) {
            final Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            for (final Element annotatedElement : annotatedElements) {
                // TODO compute ANNOTATION_TYPE additional to CLASS
                if( !annotatedElement.getKind().isClass()){
                    continue;
                }
                final TypeElement typeElement = (TypeElement) annotatedElement;
                final Map<Name, Name> mapName2Type = new HashMap<Name, Name>();
                final List<? extends Element> classMembers = annotatedElement.getEnclosedElements();
                for (final Element classMember : classMembers) {
                    if (classMember.getKind().isField()) {
                        final Set<Modifier> fieldModifiers = classMember.getModifiers();
                        if (fieldModifiers.contains(Modifier.PUBLIC) || fieldModifiers.contains(Modifier.PROTECTED)) {
                            final Name fieldName = classMember.getSimpleName();
                            final TypeMirror fieldTypeMirror = classMember.asType();
                            final Element fieldTypeElement = types.asElement(fieldTypeMirror);

                            mapName2Type.put(fieldName, fieldTypeElement.getSimpleName());
                        }
                    }
                }

                final Name className = typeElement.getQualifiedName();
                try {
                    writeBuilderFile(className, mapName2Type);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    private void writeBuilderFile(final Name typeName, Map<Name, Name> mapFieldName2Type)
            throws IOException {

        final String className = typeName.toString();

        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }

        String simpleClassName = className.substring(lastDot + 1);
        final String objectName = simpleClassName.substring(0, 1).toLowerCase() + simpleClassName.substring(1);
        String builderClassName = className + "Builder";
        String builderSimpleClassName = builderClassName
                .substring(lastDot + 1);

        JavaFileObject builderFile = processingEnv.getFiler()
                .createSourceFile(builderClassName);

        try {
            final PrintWriter  out = new PrintWriter(builderFile.openWriter());

            if (packageName != null) {
                out.print("package ");
                out.print(packageName);
                out.println(";");
                out.println();
            }

            out.print("public class ");
            out.print(builderSimpleClassName);
            out.println(" {");
            out.println();

            out.print("    private ");
            out.print(simpleClassName);
            out.print(" " + objectName + " = new ");
            out.print(simpleClassName);
            out.println("();");
            out.println();

            out.print("    public ");
            out.print(simpleClassName);
            out.println(" build() {");
            out.println("        final " + simpleClassName + " value = this." + objectName + ";");
            out.println("        this." + objectName + " = null;");
            out.println("        return value;");
            out.println("    }");
            out.println();

            final Set<Map.Entry<Name,Name>> entries= mapFieldName2Type.entrySet();
            for(final Map.Entry fields : entries){
                final String fieldName = fields.getKey().toString();
                final String attributName = fieldName.toString();
                final String setterName = "with" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                final String argumentType = fields.getValue().toString();

                out.print("    public ");
                out.print(builderSimpleClassName);
                out.print(" ");
                out.print(setterName);

                out.print("( final ");

                out.print(argumentType);
                out.println(" " + attributName + " ) {");
                out.print("        this." + objectName + ".");
                out.print(attributName);
                out.println(" = " + attributName + ";");
                out.println("        return this;");
                out.println("    }");
                out.println();
            }

//            forEach(fields -> {
//                final String fieldName = fields.getKey().toString();
//                final String attributName = fieldName.toString();
//                final String setterName = "with" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
//                final String argumentType = fields.getValue().toString();
//
//                out.print("    public ");
//                out.print(builderSimpleClassName);
//                out.print(" ");
//                out.print(setterName);
//
//                out.print("( final ");
//
//                out.print(argumentType);
//                out.println(" " + attributName + " ) {");
//                out.print("        this." + objectName + ".");
//                out.print(attributName);
//                out.println(" = " + attributName + ";");
//                out.println("        return this;");
//                out.println("    }");
//                out.println();
//            });

            out.println("}");
        }finally {
            System.out.println("Finnally");
        }
    }

}
