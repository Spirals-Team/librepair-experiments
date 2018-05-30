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
package spoon.template;


/**
 * This class defines the substitution API for templates (see {@link Template}).
 */
public abstract class Substitution {
    private Substitution() {
    }

    /**
     * Inserts all the methods, fields, constructors, initialization blocks (if
     * target is a class), inner types, and super interfaces (except
     * {@link Template}) from a given template by substituting all the template
     * parameters by their values. Members annotated with
     * {@link spoon.template.Local} or {@link Parameter} are not inserted.
     *
     * @param targetType
     * 		the target type
     * @param template
     * 		the source template
     */
    public static <T extends spoon.template.Template<?>> void insertAll(spoon.reflect.declaration.CtType<?> targetType, T template) {
        spoon.reflect.declaration.CtClass<T> templateClass = spoon.template.Substitution.getTemplateCtClass(targetType, template);
        // insert all the interfaces
        spoon.template.Substitution.insertAllSuperInterfaces(targetType, template);
        // insert all the methods
        spoon.template.Substitution.insertAllMethods(targetType, template);
        // insert all the constructors and all the initialization blocks (only for classes)
        spoon.template.Substitution.insertAllConstructors(targetType, template);
        for (spoon.reflect.declaration.CtTypeMember typeMember : templateClass.getTypeMembers()) {
            // insert all the inner types
            if (typeMember instanceof spoon.reflect.declaration.CtField) {
                // insert all the fields
                spoon.template.Substitution.insertGeneratedField(targetType, template, ((spoon.reflect.declaration.CtField<?>) (typeMember)));
            }// insert all the inner types
            else
                if (typeMember instanceof spoon.reflect.declaration.CtType) {
                    spoon.template.Substitution.insertGeneratedNestedType(targetType, template, ((spoon.reflect.declaration.CtType) (typeMember)));
                }

        }
    }

    /**
     * Generates a type (class, interface, enum, ...) from the template model `templateOfType`
     * by by substituting all the template parameters by their values.
     *
     * Inserts all the methods, fields, constructors, initialization blocks (if
     * target is a class), inner types, super class and super interfaces.
     *
     * Note!
     * This algorithm does NOT handle interfaces or annotations
     * {@link Template}, {@link spoon.template.Local}, {@link TemplateParameter} or {@link Parameter}
     * in a special way, it means they all will be added to the generated type too.
     * If you do not want to add them then clone your templateOfType and remove these nodes from that model before.
     *
     * @param qualifiedTypeName
     * 		the qualified name of the new type
     * @param templateOfType
     * 		the model used as source of generation.
     * @param templateParameters
     * 		the substitution parameters
     */
    @java.lang.SuppressWarnings("unchecked")
    public static <T extends spoon.reflect.declaration.CtType<?>> T createTypeFromTemplate(java.lang.String qualifiedTypeName, spoon.reflect.declaration.CtType<?> templateOfType, java.util.Map<java.lang.String, java.lang.Object> templateParameters) {
        final spoon.reflect.factory.Factory f = templateOfType.getFactory();
        spoon.reflect.reference.CtTypeReference<T> typeRef = f.Type().createReference(qualifiedTypeName);
        spoon.reflect.declaration.CtPackage targetPackage = f.Package().getOrCreate(typeRef.getPackage().getSimpleName());
        final java.util.Map<java.lang.String, java.lang.Object> extendedParams = new java.util.HashMap<java.lang.String, java.lang.Object>(templateParameters);
        extendedParams.put(templateOfType.getSimpleName(), typeRef);
        java.util.List<spoon.reflect.declaration.CtType<?>> generated = ((java.util.List) (new spoon.support.template.SubstitutionVisitor(f, extendedParams).substitute(templateOfType.clone())));
        for (spoon.reflect.declaration.CtType<?> ctType : generated) {
            targetPackage.addType(ctType);
        }
        return ((T) (typeRef.getTypeDeclaration()));
    }

    /**
     * Inserts all the super interfaces (except {@link Template}) from a given
     * template by substituting all the template parameters by their values.
     *
     * @param targetType
     * 		the target type
     * @param template
     * 		the source template
     */
    public static void insertAllSuperInterfaces(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template) {
        spoon.reflect.declaration.CtClass<? extends spoon.template.Template<?>> sourceClass = spoon.template.Substitution.getTemplateCtClass(targetType, template);
        spoon.template.Substitution.insertAllSuperInterfaces(targetType, template, sourceClass);
    }

    /**
     * Inserts all the super interfaces (except {@link Template}) from a given
     * template by substituting all the template parameters by their values.
     *
     * @param targetType
     * 		the target type
     * @param template
     * 		the source template
     * @param sourceClass
     * 		the model of source template
     */
    static void insertAllSuperInterfaces(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template, spoon.reflect.declaration.CtClass<? extends spoon.template.Template<?>> sourceClass) {
        // insert all the interfaces
        for (spoon.reflect.reference.CtTypeReference<?> t : sourceClass.getSuperInterfaces()) {
            if (!(t.equals(targetType.getFactory().Type().createReference(spoon.template.Template.class)))) {
                spoon.reflect.reference.CtTypeReference<?> t1 = t;
                // substitute ref if needed
                if (spoon.support.template.Parameters.getNames(sourceClass).contains(t.getSimpleName())) {
                    java.lang.Object o = spoon.support.template.Parameters.getValue(template, t.getSimpleName(), null);
                    if (o instanceof spoon.reflect.reference.CtTypeReference) {
                        t1 = ((spoon.reflect.reference.CtTypeReference<?>) (o));
                    }else
                        if (o instanceof java.lang.Class) {
                            t1 = targetType.getFactory().Type().createReference(((java.lang.Class<?>) (o)));
                        }else
                            if (o instanceof java.lang.String) {
                                t1 = targetType.getFactory().Type().createReference(((java.lang.String) (o)));
                            }


                }
                if (!(t1.equals(targetType.getReference()))) {
                    java.lang.Class<?> c = null;
                    try {
                        c = t1.getActualClass();
                    } catch (java.lang.Exception e) {
                        // swallow it
                    }
                    if ((c != null) && (c.isInterface())) {
                        targetType.addSuperInterface(t1);
                    }
                    if (c == null) {
                        targetType.addSuperInterface(t1);
                    }
                }
            }
        }
    }

    /**
     * Inserts all the methods from a given template by substituting all the
     * template parameters by their values. Members annotated with
     * {@link spoon.template.Local} or {@link Parameter} are not inserted.
     *
     * @param targetType
     * 		the target type
     * @param template
     * 		the source template
     */
    public static void insertAllMethods(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template) {
        spoon.reflect.declaration.CtClass<?> sourceClass = spoon.template.Substitution.getTemplateCtClass(targetType, template);
        spoon.template.Substitution.insertAllMethods(targetType, template, sourceClass);
    }

    /**
     * Inserts all the methods from a given template by substituting all the
     * template parameters by their values. Members annotated with
     * {@link spoon.template.Local} or {@link Parameter} are not inserted.
     *
     * @param targetType
     * 		the target type
     * @param template
     * 		the source template
     * @param sourceClass
     * 		the model of source template
     */
    static void insertAllMethods(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template, spoon.reflect.declaration.CtClass<?> sourceClass) {
        java.util.Set<spoon.reflect.declaration.CtMethod<?>> methodsOfTemplate = sourceClass.getFactory().Type().get(spoon.template.Template.class).getMethods();
        // insert all the methods
        for (spoon.reflect.declaration.CtMethod<?> m : sourceClass.getMethods()) {
            if ((m.getAnnotation(spoon.template.Local.class)) != null) {
                continue;
            }
            if ((m.getAnnotation(spoon.template.Parameter.class)) != null) {
                continue;
            }
            boolean isOverridingTemplateItf = false;
            for (spoon.reflect.declaration.CtMethod m2 : methodsOfTemplate) {
                if (m.isOverriding(m2)) {
                    isOverridingTemplateItf = true;
                }
            }
            if (isOverridingTemplateItf) {
                continue;
            }
            spoon.template.Substitution.insertMethod(targetType, template, m);
        }
    }

    /**
     * Inserts all the fields from a given template by substituting all the
     * template parameters by their values. Members annotated with
     * {@link spoon.template.Local} or {@link Parameter} are not inserted.
     *
     * @param targetType
     * 		the target type
     * @param template
     * 		the source template
     */
    public static void insertAllFields(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template) {
        spoon.reflect.declaration.CtClass<?> sourceClass = spoon.template.Substitution.getTemplateCtClass(targetType, template);
        // insert all the fields
        for (spoon.reflect.declaration.CtTypeMember typeMember : sourceClass.getTypeMembers()) {
            if (typeMember instanceof spoon.reflect.declaration.CtField) {
                spoon.template.Substitution.insertGeneratedField(targetType, template, ((spoon.reflect.declaration.CtField<?>) (typeMember)));
            }
        }
    }

    /**
     * Inserts the field by substituting all the
     * template parameters by their values. Field annotated with
     * {@link spoon.template.Local} or {@link Parameter} is not inserted.
     *
     * @param targetType
     * 		
     * @param template
     * 		
     * @param field
     * 		
     */
    static void insertGeneratedField(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template, spoon.reflect.declaration.CtField<?> field) {
        if ((field.getAnnotation(spoon.template.Local.class)) != null) {
            return;
        }
        if (spoon.support.template.Parameters.isParameterSource(field.getReference())) {
            return;
        }
        spoon.template.Substitution.insertField(targetType, template, field);
    }

    /**
     * Inserts all the nested types from a given template by substituting all the
     * template parameters by their values. Members annotated with
     * {@link spoon.template.Local} are not inserted.
     *
     * @param targetType
     * 		the target type
     * @param template
     * 		the source template
     */
    public static void insertAllNestedTypes(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template) {
        spoon.reflect.declaration.CtClass<?> sourceClass = spoon.template.Substitution.getTemplateCtClass(targetType, template);
        // insert all the fields
        for (spoon.reflect.declaration.CtTypeMember typeMember : sourceClass.getTypeMembers()) {
            if (typeMember instanceof spoon.reflect.declaration.CtType) {
                spoon.template.Substitution.insertGeneratedNestedType(targetType, template, ((spoon.reflect.declaration.CtType<?>) (typeMember)));
            }
        }
    }

    /**
     * Inserts the nestedType by substituting all the
     * template parameters by their values. Nested type annotated with
     * {@link spoon.template.Local} is not inserted.
     *
     * @param targetType
     * 		the target type
     * @param template
     * 		the source template
     * @param nestedType
     * 		to be insterted nested type
     */
    static void insertGeneratedNestedType(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template, spoon.reflect.declaration.CtType<?> nestedType) {
        if ((nestedType.getAnnotation(spoon.template.Local.class)) != null) {
            return;
        }
        spoon.reflect.declaration.CtType<?> result = spoon.template.Substitution.substitute(targetType, template, ((spoon.reflect.declaration.CtType) (nestedType)));
        targetType.addNestedType(result);
    }

    /**
     * Inserts all constructors and initialization blocks from a given template
     * by substituting all the template parameters by their values. Members
     * annotated with {@link spoon.template.Local} or {@link Parameter} are not
     * inserted.
     *
     * @param targetType
     * 		the target type
     * @param template
     * 		the source template
     */
    public static void insertAllConstructors(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template) {
        spoon.reflect.declaration.CtClass<?> sourceClass = spoon.template.Substitution.getTemplateCtClass(targetType, template);
        spoon.template.Substitution.insertAllConstructors(targetType, template, sourceClass);
    }

    /**
     * Inserts all constructors and initialization blocks from a given template
     * by substituting all the template parameters by their values. Members
     * annotated with {@link spoon.template.Local} or {@link Parameter} are not
     * inserted.
     *
     * @param targetType
     * 		the target type
     * @param template
     * 		the source template
     * @param sourceClass
     * 		the model of source template
     */
    static void insertAllConstructors(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template, spoon.reflect.declaration.CtClass<?> sourceClass) {
        // insert all the constructors
        if (targetType instanceof spoon.reflect.declaration.CtClass) {
            for (spoon.reflect.declaration.CtConstructor<?> c : sourceClass.getConstructors()) {
                if (c.isImplicit()) {
                    continue;
                }
                if ((c.getAnnotation(spoon.template.Local.class)) != null) {
                    continue;
                }
                spoon.template.Substitution.insertConstructor(((spoon.reflect.declaration.CtClass<?>) (targetType)), template, c);
            }
        }
        // insert all the initialization blocks (only for classes)
        if (targetType instanceof spoon.reflect.declaration.CtClass) {
            for (spoon.reflect.declaration.CtAnonymousExecutable e : sourceClass.getAnonymousExecutables()) {
                ((spoon.reflect.declaration.CtClass<?>) (targetType)).addAnonymousExecutable(spoon.template.Substitution.substitute(targetType, template, e));
            }
        }
    }

    /**
     * Generates a constructor from a template method by substituting all the
     * template parameters by their values.
     *
     * @param targetClass
     * 		the target class where to insert the generated constructor
     * @param template
     * 		the template instance that holds the source template method
     * 		and that defines the parameter values
     * @param sourceMethod
     * 		the source template method
     * @return the generated method
     */
    public static <T> spoon.reflect.declaration.CtConstructor<T> insertConstructor(spoon.reflect.declaration.CtClass<T> targetClass, spoon.template.Template<?> template, spoon.reflect.declaration.CtMethod<?> sourceMethod) {
        if (targetClass instanceof spoon.reflect.declaration.CtInterface) {
            return null;
        }
        spoon.reflect.declaration.CtConstructor<T> newConstructor = targetClass.getFactory().Constructor().create(targetClass, sourceMethod);
        newConstructor = spoon.template.Substitution.substitute(targetClass, template, newConstructor);
        targetClass.addConstructor(newConstructor);
        // newConstructor.setParent(targetClass);
        return newConstructor;
    }

    /**
     * Generates a method from a template method by substituting all the
     * template parameters by their values.
     *
     * @param targetType
     * 		the target type where to insert the generated method
     * @param template
     * 		the template instance that holds the source template method
     * 		and that defines the parameter values
     * @param sourceMethod
     * 		the source template method
     * @return the generated method
     */
    public static <T> spoon.reflect.declaration.CtMethod<T> insertMethod(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template, spoon.reflect.declaration.CtMethod<T> sourceMethod) {
        spoon.reflect.declaration.CtMethod<T> newMethod = spoon.template.Substitution.substitute(targetType, template, sourceMethod);
        if (targetType instanceof spoon.reflect.declaration.CtInterface) {
            newMethod.setBody(null);
        }
        targetType.addMethod(newMethod);
        // newMethod.setParent(targetType);
        return newMethod;
    }

    /**
     * Generates a constructor from a template constructor by substituting all
     * the template parameters by their values.
     *
     * @param targetClass
     * 		the target class where to insert the generated constructor
     * @param template
     * 		the template instance that holds the source template
     * 		constructor and that defines the parameter values
     * @param sourceConstructor
     * 		the source template constructor
     * @return the generated constructor
     */
    @java.lang.SuppressWarnings("unchecked")
    public static <T> spoon.reflect.declaration.CtConstructor<T> insertConstructor(spoon.reflect.declaration.CtClass<T> targetClass, spoon.template.Template<?> template, spoon.reflect.declaration.CtConstructor<?> sourceConstructor) {
        spoon.reflect.declaration.CtConstructor<T> newConstrutor = spoon.template.Substitution.substitute(targetClass, template, ((spoon.reflect.declaration.CtConstructor<T>) (sourceConstructor)));
        // remove the implicit constructor if clashing
        if (newConstrutor.getParameters().isEmpty()) {
            spoon.reflect.declaration.CtConstructor<?> c = targetClass.getConstructor();
            if ((c != null) && (c.isImplicit())) {
                targetClass.removeConstructor(((spoon.reflect.declaration.CtConstructor<T>) (c)));
            }
        }
        targetClass.addConstructor(newConstrutor);
        // newConstrutor.setParent(targetClass);
        return newConstrutor;
    }

    /**
     * Gets a body from a template executable with all the template parameters
     * substituted.
     *
     * @param targetClass
     * 		the target class
     * @param template
     * 		the template that holds the executable
     * @param executableName
     * 		the source executable template
     * @param parameterTypes
     * 		the parameter types of the source executable
     * @return the body expression of the source executable template with all
     * the template parameters substituted
     */
    public static spoon.reflect.code.CtBlock<?> substituteMethodBody(spoon.reflect.declaration.CtClass<?> targetClass, spoon.template.Template<?> template, java.lang.String executableName, spoon.reflect.reference.CtTypeReference<?>... parameterTypes) {
        spoon.reflect.declaration.CtClass<?> sourceClass = spoon.template.Substitution.getTemplateCtClass(targetClass, template);
        spoon.reflect.declaration.CtExecutable<?> sourceExecutable = (executableName.equals(template.getClass().getSimpleName())) ? sourceClass.getConstructor(parameterTypes) : sourceClass.getMethod(executableName, parameterTypes);
        return spoon.template.Substitution.substitute(targetClass, template, sourceExecutable.getBody());
    }

    /**
     * Gets a statement from a template executable with all the template
     * parameters substituted.
     *
     * @param targetClass
     * 		the target class
     * @param template
     * 		the template that holds the executable
     * @param statementIndex
     * 		the statement index in the executable's body
     * @param executableName
     * 		the source executable template
     * @param parameterTypes
     * 		the parameter types of the source executable
     * @return the body expression of the source executable template with all
     * the template parameters substituted
     */
    public static spoon.reflect.code.CtStatement substituteStatement(spoon.reflect.declaration.CtClass<?> targetClass, spoon.template.Template<?> template, int statementIndex, java.lang.String executableName, spoon.reflect.reference.CtTypeReference<?>... parameterTypes) {
        spoon.reflect.declaration.CtClass<?> sourceClass = spoon.template.Substitution.getTemplateCtClass(targetClass, template);
        spoon.reflect.declaration.CtExecutable<?> sourceExecutable = (executableName.equals(template.getClass().getSimpleName())) ? sourceClass.getConstructor(parameterTypes) : sourceClass.getMethod(executableName, parameterTypes);
        return spoon.template.Substitution.substitute(targetClass, template, sourceExecutable.getBody().getStatement(statementIndex));
    }

    /**
     * Gets a default expression from a template field with all the template
     * parameters substituted.
     *
     * @param targetType
     * 		the target type
     * @param template
     * 		the template that holds the field
     * @param fieldName
     * 		the template source field
     * @return the expression of the template source field with all the template
     * parameters substituted
     */
    public static spoon.reflect.code.CtExpression<?> substituteFieldDefaultExpression(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template, java.lang.String fieldName) {
        spoon.reflect.declaration.CtClass<?> sourceClass = spoon.template.Substitution.getTemplateCtClass(targetType, template);
        spoon.reflect.declaration.CtField<?> sourceField = sourceClass.getField(fieldName);
        return spoon.template.Substitution.substitute(targetType, template, sourceField.getDefaultExpression());
    }

    /**
     * Substitutes all the template parameters in a random piece of code.
     *
     * @param targetType
     * 		the target type
     * @param template
     * 		the template instance
     * @param code
     * 		the code
     * @return the code where all the template parameters has been substituted
     * by their values
     */
    public static <E extends spoon.reflect.declaration.CtElement> E substitute(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template, E code) {
        if (code == null) {
            return null;
        }
        if (targetType == null) {
            throw new java.lang.RuntimeException("target is null in substitution");
        }
        E result = ((E) (code.clone()));
        java.util.List<E> results = new spoon.support.template.SubstitutionVisitor(targetType.getFactory(), targetType, template).substitute(result);
        if ((results.size()) > 1) {
            throw new spoon.SpoonException("StatementTemplate cannot return more then one statement");
        }
        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * Substitutes all the template parameters in the first template element
     * annotated with an instance of the given annotation type.
     *
     * @param targetType
     * 		the target type
     * @param template
     * 		the template instance
     * @param annotationType
     * 		the annotation type
     * @return the element where all the template parameters has be substituted
     * by their values
     */
    // public static <E extends CtElement> E substitute(
    // CtSimpleType<?> targetType, Template template,
    // Class<? extends Annotation> annotationType) {
    // CtClass<? extends Template> c = targetType.getFactory().Class
    // .get(template.getClass());
    // E element = (E) c.getAnnotatedChildren(annotationType).get(0);
    // if (element == null)
    // return null;
    // if (targetType == null)
    // throw new RuntimeException("target is null in substitution");
    // E result = CtCloner.clone(element);
    // new SubstitutionVisitor(targetType.getFactory(), targetType, template)
    // .scan(result);
    // return result;
    // }
    /**
     * Substitutes all the template parameters in a given template type and
     * returns the resulting type.
     *
     * @param template
     * 		the template instance (holds the parameter values)
     * @param templateType
     * 		the template type
     * @return a copy of the template type where all the parameters has been
     * substituted
     */
    public static <T extends spoon.reflect.declaration.CtType<?>> T substitute(spoon.template.Template<?> template, T templateType) {
        T result = ((T) (templateType.clone()));
        result.setPositions(null);
        // result.setParent(templateType.getParent());
        new spoon.support.template.SubstitutionVisitor(templateType.getFactory(), result, template).substitute(result);
        return result;
    }

    /**
     * Generates a field (and its initialization expression) from a template
     * field by substituting all the template parameters by their values.
     *
     * @param <T>
     * 		the type of the field
     * @param targetType
     * 		the target type where the field is inserted
     * @param template
     * 		the template that defines the source template field
     * @param sourceField
     * 		the source template field
     * @return the inserted field
     */
    public static <T> spoon.reflect.declaration.CtField<T> insertField(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template, spoon.reflect.declaration.CtField<T> sourceField) {
        spoon.reflect.declaration.CtField<T> field = spoon.template.Substitution.substitute(targetType, template, sourceField);
        targetType.addField(field);
        // field.setParent(targetType);
        return field;
    }

    /**
     * A helper method that recursively redirects all the type references from a
     * source type to a target type in the given element.
     */
    public static void redirectTypeReferences(spoon.reflect.declaration.CtElement element, spoon.reflect.reference.CtTypeReference<?> source, spoon.reflect.reference.CtTypeReference<?> target) {
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> refs = spoon.reflect.visitor.Query.getReferences(element, new spoon.reflect.visitor.filter.ReferenceTypeFilter<spoon.reflect.reference.CtTypeReference<?>>(spoon.reflect.reference.CtTypeReference.class));
        java.lang.String srcName = source.getQualifiedName();
        java.lang.String targetName = target.getSimpleName();
        spoon.reflect.reference.CtPackageReference targetPackage = target.getPackage();
        for (spoon.reflect.reference.CtTypeReference<?> ref : refs) {
            if (ref.getQualifiedName().equals(srcName)) {
                ref.setSimpleName(targetName);
                ref.setPackage(targetPackage);
            }
        }
    }

    /**
     *
     *
     * @param targetType
     * 		- the element which is going to receive the model produced by the template.
     * 		It is needed here just to provide the spoon factory, which contains the model of the template
     * @param template
     * 		- java instance of the template
     * @return - CtClass from the already built spoon model, which represents the template
     */
    static <T> spoon.reflect.declaration.CtClass<T> getTemplateCtClass(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template) {
        spoon.reflect.factory.Factory factory;
        // we first need a factory
        if (targetType != null) {
            // if it's template with reference replacement
            factory = targetType.getFactory();
        }else {
            // else we have at least one template parameter with a factory
            factory = spoon.template.Substitution.getFactory(template);
        }
        return spoon.template.Substitution.getTemplateCtClass(factory, template);
    }

    /**
     *
     *
     * @param factory
     * 		- the factory, which contains the model of the template
     * @param template
     * 		- java instance of the template
     * @return - CtClass from the already built spoon model, which represents the template
     */
    static <T> spoon.reflect.declaration.CtClass<T> getTemplateCtClass(spoon.reflect.factory.Factory factory, spoon.template.Template<?> template) {
        spoon.reflect.declaration.CtClass<T> c = factory.Class().get(template.getClass());
        if (c.isShadow()) {
            throw new spoon.SpoonException((("The template " + (template.getClass().getName())) + " is not part of model. Add template sources to spoon template path."));
        }
        spoon.template.Substitution.checkTemplateContracts(c);
        return c;
    }

    private static <T> void checkTemplateContracts(spoon.reflect.declaration.CtClass<T> c) {
        for (spoon.reflect.declaration.CtField f : c.getFields()) {
            spoon.template.Parameter templateParamAnnotation = f.getAnnotation(spoon.template.Parameter.class);
            if ((templateParamAnnotation != null) && (!(templateParamAnnotation.value().equals("")))) {
                java.lang.String proxyName = templateParamAnnotation.value();
                // contract: if value, then the field type must be String or CtTypeReference
                java.lang.String fieldTypeQName = f.getType().getQualifiedName();
                // OK it is CtTypeReference
                if (fieldTypeQName.equals(java.lang.String.class.getName())) {
                    // contract: the name of the template parameter must correspond to the name of the field
                    // as found, by Pavel, this is not good contract because it prevents easy refactoring of templates
                    // we remove it but keep th commented code in case somebody would come up with this bad idae
                    // if (!f.getSimpleName().equals("_" + f.getAnnotation(Parameter.class).value())) {
                    // throw new TemplateException("the field name of a proxy template parameter must be called _" + f.getSimpleName());
                    // }
                    // contract: if a proxy parameter is declared and named "x" (@Parameter("x")), then a type member named "x" must exist.
                    boolean found = false;
                    for (spoon.reflect.declaration.CtTypeMember member : c.getTypeMembers()) {
                        if (member.getSimpleName().equals(proxyName)) {
                            found = true;
                        }
                    }
                    if (!found) {
                        throw new spoon.template.TemplateException((("if a proxy parameter is declared and named \"" + proxyName) + "\", then a type member named \"\" + proxyName + \"\" must exist."));
                    }
                }// OK it is CtTypeReference
                else
                    if (fieldTypeQName.equals(spoon.reflect.reference.CtTypeReference.class.getName())) {
                    }else {
                        throw new spoon.template.TemplateException(("proxy template parameter must be typed as String or CtTypeReference, but it is " + fieldTypeQName));
                    }

            }
        }
    }

    /**
     * returns a Spoon factory object from the first template parameter that contains one
     */
    static spoon.reflect.factory.Factory getFactory(spoon.template.Template<?> template) {
        try {
            for (java.lang.reflect.Field f : spoon.support.template.Parameters.getAllTemplateParameterFields(template.getClass())) {
                if (((f.get(template)) != null) && ((f.get(template)) instanceof spoon.processing.FactoryAccessor)) {
                    return ((spoon.processing.FactoryAccessor) (f.get(template))).getFactory();
                }
            }
        } catch (java.lang.Exception e) {
            throw new spoon.SpoonException(e);
        }
        throw new spoon.template.TemplateException(("no factory found in template " + (template.getClass().getName())));
    }
}

