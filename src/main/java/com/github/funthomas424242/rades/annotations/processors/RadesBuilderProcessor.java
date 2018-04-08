package com.github.funthomas424242.rades.annotations.processors;

import com.github.funthomas424242.rades.annotations.AddBuilder;
import com.github.funthomas424242.rades.annotations.RadesAddBuilder;
import com.github.funthomas424242.rades.annotations.lang.java.JavaModelHelper;
import com.github.funthomas424242.rades.annotations.lang.java.JavaModelService;
import com.github.funthomas424242.rades.annotations.lang.java.JavaModelServiceProvider;
import com.github.funthomas424242.rades.annotations.lang.java.JavaSrcFileCreator;
import com.google.auto.service.AutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

@SupportedAnnotationTypes({"com.github.funthomas424242.rades.annotations.RadesBuilder"
        , "com.github.funthomas424242.rades.annotations.RadesAddBuilder"
        , "com.github.funthomas424242.rades.annotations.AddBuilder"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class RadesBuilderProcessor extends AbstractProcessor {

    protected final Logger logger = LoggerFactory.getLogger(RadesBuilderProcessor.class);


    protected JavaModelService javaModelService = new JavaModelServiceProvider();

    protected ProcessingEnvironment processingEnvironment;

    /**
     * Please only use this method for mocking in your test code!
     *
     * @param javaModelService mock to replace the default intern instance.
     */
    protected void setJavaModelService(final JavaModelService javaModelService) {
        this.javaModelService = javaModelService;
    }

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.processingEnvironment = processingEnv;
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
//        final Types types = this.processingEnvironment.getTypeUtils();

        final Stack<TypeElement> allAnnotations = new Stack<>();
        annotations.stream().collect(Collectors.toList()).forEach(annotation -> {
            allAnnotations.push(annotation);
        });

        final Set<Element> processedAnnotations = new HashSet<>();
        final Set<Element> annotatedClasses = new HashSet<>();
        while (!allAnnotations.empty()) {
            final TypeElement annotation = allAnnotations.pop();
            processedAnnotations.add(annotation);

            final Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            for (final Element annotatedElement : annotatedElements) {
                if (annotatedElement.getKind() == ElementKind.ANNOTATION_TYPE) {
                    final TypeElement typeElement = (TypeElement) annotatedElement;
                    if (!processedAnnotations.contains(typeElement)) {
                        logger.debug("###Annotation: " + typeElement);
                        // als Annotation aufnehmen falls gerade nicht im Stack (Minioptimierung)
                        allAnnotations.push(typeElement);
                    }
                    continue;
                }
                if (annotatedElement.getKind().isClass()) {
                    logger.debug("###Class: " + annotatedElement);
                    annotatedClasses.add(annotatedElement);
                }
            }
        }

        annotatedClasses.forEach(element -> {
            createBuilderSrcFile(element);
        });

        return true;
    }

    private void createBuilderSrcFile(final Element annotatedElement) {
        logger.debug("###WRITE BUILDER for: " + annotatedElement);
        final TypeElement typeElement = (TypeElement) annotatedElement;
        final Map<Name, TypeMirror> mapName2Type = new HashMap<>();
        final List<? extends Element> classMembers = annotatedElement.getEnclosedElements();
        for (final Element classMember : classMembers) {
            if (classMember.getKind().isField()) {
                final Set<Modifier> fieldModifiers = classMember.getModifiers();
                if (!fieldModifiers.contains(Modifier.PRIVATE)) {
                    final Name fieldName = classMember.getSimpleName();
                    final TypeMirror fieldTypeMirror = classMember.asType();
                    mapName2Type.put(fieldName, fieldTypeMirror);
                }
            }
        }

        writeBuilderFile(typeElement, mapName2Type);
    }

    protected void writeBuilderFile(final TypeElement typeElement, Map<Name, TypeMirror> mapFieldName2Type) {

        String specifiedBuilderClassName = null;
        specifiedBuilderClassName = getRadesAddBuilderSimpleClassName(typeElement, specifiedBuilderClassName);
        specifiedBuilderClassName = getAddBuilderSimpleClassName(typeElement, specifiedBuilderClassName);
        final String qualifiedClassName = typeElement.getQualifiedName().toString();
        final String simpleClassName = typeElement.getSimpleName().toString();
        final String packageName = JavaModelHelper.computePackageName(qualifiedClassName);

        final String newInstanceName = simpleClassName.substring(0, 1).toLowerCase() + simpleClassName.substring(1);
        final String builderClassName = getBuilderClassName(specifiedBuilderClassName, packageName, qualifiedClassName);
        final String builderSimpleClassName = getBuilderSimpleClassName(specifiedBuilderClassName, simpleClassName);
        logger.debug("###specifiedBuilderClassName: " + specifiedBuilderClassName);
        logger.debug("###builderClassName: " + builderClassName);
        logger.debug("###builderSimpleClassName: " + builderSimpleClassName);

        final Filer filer = processingEnv.getFiler();
        try (final JavaSrcFileCreator javaSrcFileCreator = javaModelService.getJavaSrcFileCreator(filer, builderClassName)) {

            javaSrcFileCreator.init();

            if (packageName != null) {
                javaSrcFileCreator.writePackage(packageName);
            }
            javaSrcFileCreator.writeImports();

            javaSrcFileCreator.writeClassAnnotations(qualifiedClassName);
            javaSrcFileCreator.writeClassDeclaration(builderSimpleClassName);

            javaSrcFileCreator.writeFieldDefinition(simpleClassName, newInstanceName);

            javaSrcFileCreator.writeConstructors(simpleClassName, newInstanceName, builderSimpleClassName);


            javaSrcFileCreator.writeBuildMethod(simpleClassName, newInstanceName);

            mapFieldName2Type.entrySet().forEach(fields -> {
                final String fieldName = fields.getKey().toString();
                final String setterName = "with" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                final String argumentType = getFullQualifiedTypeSignature(fields.getValue());

                javaSrcFileCreator.writeSetterMethod(newInstanceName, builderSimpleClassName, fieldName, setterName, argumentType);
            });

            javaSrcFileCreator.writeClassFinal();
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    protected String getBuilderSimpleClassName(final String specifiedBuilderClassName, final String simpleClassName) {
        if (specifiedBuilderClassName != null) {
            return specifiedBuilderClassName;
        } else {
            return simpleClassName + "Builder";
        }
    }

    protected String getBuilderClassName(final String specifiedBuilderClassName, final String packageName, final String className) {
        if(specifiedBuilderClassName!=null){
            return packageName + "." + specifiedBuilderClassName;
        }else{
            return className + "Builder";
        }
    }

    protected String getRadesAddBuilderSimpleClassName(final TypeElement typeElement, final String specifiedBuilderClassName) {
        final RadesAddBuilder radesAddBuilder = typeElement.getAnnotation(RadesAddBuilder.class);
        if (specifiedBuilderClassName == null && radesAddBuilder != null) {
            final String tmp = radesAddBuilder.simpleBuilderClassName().trim();
            if (tmp.length() > 0) {
                return tmp;
            }
            logger.debug("###1|SimpleBuilderClassName: " + specifiedBuilderClassName);
        }
        return specifiedBuilderClassName;
    }

    protected String getAddBuilderSimpleClassName(final TypeElement typeElement, final String specifiedBuilderClassName) {
        final AddBuilder addBuilder = typeElement.getAnnotation(AddBuilder.class);
        if (specifiedBuilderClassName == null && addBuilder != null) {
            final String tmp = addBuilder.simpleBuilderClassName().trim();
            if (tmp.length() > 0) {
                return tmp;
            }
            logger.debug("###2|SimpleBuilderClassName: " + specifiedBuilderClassName);
        }
        return specifiedBuilderClassName;
    }

    protected String getFullQualifiedClassName(final TypeMirror typeMirror) {
        final String typeName;
        if (typeMirror instanceof DeclaredType) {
            final DeclaredType type = (DeclaredType) typeMirror;
            typeName = type.asElement().toString();
        } else {
            typeName = typeMirror.toString();
        }
        return typeName;
    }

    /**
     * Ermittelt die vollständige Typ Signatur - rekursiv!!!
     *
     * @param type TypeMirror des zu bestimmenden Datentypen
     * @return String Signatur des zu bestimmenden Datentypen
     */
    protected String getFullQualifiedTypeSignature(final TypeMirror type) {

        final StringBuffer typeSignature = new StringBuffer();

        if (type instanceof DeclaredType) {
            final DeclaredType declaredType = (DeclaredType) type;
            typeSignature.append(getFullQualifiedClassName(type));

            final List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
            if (!typeArguments.isEmpty()) {
                typeSignature.append("<");
                for (final ListIterator<? extends TypeMirror> it = typeArguments.listIterator(); it.hasNext(); ) {
                    typeSignature.append(getFullQualifiedTypeSignature((TypeMirror) it.next()));
                    if (it.hasNext()) {
                        typeSignature.append(",");
                    }
                }
                typeSignature.append(">");
            }
        } else {
            typeSignature.append(getFullQualifiedClassName(type));
        }

        return typeSignature.toString();
    }


}
