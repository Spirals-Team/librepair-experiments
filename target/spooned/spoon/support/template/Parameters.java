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
package spoon.support.template;


/**
 * This class defines an API to manipulate template parameters.
 */
public abstract class Parameters {
    private Parameters() {
    }

    /**
     * The prefix "_FIELD_" for a parameter that represents a fields in order to
     * avoid name clashes.
     */
    protected static final java.lang.String fieldPrefix = "_FIELD_";

    /**
     * Gets the index of a one-dimension array (helper).
     */
    @java.lang.SuppressWarnings("unchecked")
    public static java.lang.Integer getIndex(spoon.reflect.code.CtExpression<?> e) {
        if ((e.getParent()) instanceof spoon.reflect.code.CtArrayAccess) {
            spoon.reflect.code.CtExpression<java.lang.Integer> indexExpression = ((spoon.reflect.code.CtArrayAccess<?, spoon.reflect.code.CtExpression<java.lang.Integer>>) (e.getParent())).getIndexExpression();
            return ((spoon.reflect.code.CtLiteral<java.lang.Integer>) (indexExpression)).getValue();
        }
        return null;
    }

    /**
     * Gets a template field parameter value.
     */
    public static java.lang.Object getValue(spoon.template.Template<?> template, java.lang.String parameterName, java.lang.Integer index) {
        java.lang.reflect.Field rtField = null;
        try {
            for (java.lang.reflect.Field f : spoon.support.util.RtHelper.getAllFields(template.getClass())) {
                if (spoon.support.template.Parameters.isParameterSource(f)) {
                    if (parameterName.equals(spoon.support.template.Parameters.getParameterName(f))) {
                        rtField = f;
                        break;
                    }
                }
            }
        } catch (java.lang.Exception e) {
            throw new spoon.support.template.UndefinedParameterException(e);
        }
        java.lang.Object tparamValue = spoon.support.template.Parameters.getValue(template, parameterName, rtField);
        if ((rtField.getType().isArray()) && (index != null)) {
            tparamValue = ((java.lang.Object[]) (tparamValue))[index];
        }
        return tparamValue;
    }

    private static java.lang.Object getValue(spoon.template.Template<?> template, java.lang.String parameterName, java.lang.reflect.Field rtField) {
        if (rtField == null) {
            throw new spoon.support.template.UndefinedParameterException();
        }
        try {
            if (java.lang.reflect.Modifier.isFinal(rtField.getModifiers())) {
                java.util.Map<java.lang.String, java.lang.Object> m = spoon.support.template.Parameters.finals.get(template);
                if (m == null) {
                    return null;
                }
                return m.get(parameterName);
            }
            rtField.setAccessible(true);
            return rtField.get(template);
        } catch (java.lang.Exception e) {
            throw new spoon.support.template.UndefinedParameterException(e);
        }
    }

    static java.util.Map<spoon.template.Template<?>, java.util.Map<java.lang.String, java.lang.Object>> finals = new java.util.HashMap<>();

    public static spoon.reflect.declaration.CtField<?> getParameterField(spoon.reflect.declaration.CtClass<? extends spoon.template.Template<?>> templateClass, java.lang.String parameterName) {
        for (spoon.reflect.declaration.CtTypeMember typeMember : templateClass.getTypeMembers()) {
            if (!(typeMember instanceof spoon.reflect.declaration.CtField)) {
                continue;
            }
            spoon.reflect.declaration.CtField<?> f = ((spoon.reflect.declaration.CtField<?>) (typeMember));
            spoon.template.Parameter p = f.getAnnotation(spoon.template.Parameter.class);
            if (p == null) {
                continue;
            }
            if (f.getSimpleName().equals(parameterName)) {
                return f;
            }
            if (parameterName.equals(p.value())) {
                return f;
            }
        }
        return null;
    }

    /**
     * Sets a template field parameter value.
     */
    @java.lang.SuppressWarnings("null")
    public static void setValue(spoon.template.Template<?> template, java.lang.String parameterName, java.lang.Integer index, java.lang.Object value) {
        java.lang.Object tparamValue = null;
        try {
            java.lang.reflect.Field rtField = null;
            for (java.lang.reflect.Field f : spoon.support.util.RtHelper.getAllFields(template.getClass())) {
                if (spoon.support.template.Parameters.isParameterSource(f)) {
                    if (parameterName.equals(spoon.support.template.Parameters.getParameterName(f))) {
                        rtField = f;
                        break;
                    }
                }
            }
            if (rtField == null) {
                return;
            }
            if (java.lang.reflect.Modifier.isFinal(rtField.getModifiers())) {
                java.util.Map<java.lang.String, java.lang.Object> m = spoon.support.template.Parameters.finals.get(template);
                if (m == null) {
                    spoon.support.template.Parameters.finals.put(template, (m = new java.util.HashMap<>()));
                }
                m.put(parameterName, value);
                return;
            }
            rtField.setAccessible(true);
            rtField.set(template, value);
            if (rtField.getType().isArray()) {
                // TODO: RP: THIS IS WRONG!!!! tparamValue is never used or
                // set!!
                tparamValue = ((java.lang.Object[]) (tparamValue))[index];
            }
        } catch (java.lang.Exception e) {
            throw new spoon.support.template.UndefinedParameterException();
        }
    }

    private static java.lang.String getParameterName(java.lang.reflect.Field f) {
        java.lang.String name = f.getName();
        spoon.template.Parameter p = f.getAnnotation(spoon.template.Parameter.class);
        if ((p != null) && (!(p.value().equals("")))) {
            name = p.value();
        }
        return name;
    }

    private static java.lang.String getParameterName(spoon.reflect.reference.CtFieldReference<?> f) {
        java.lang.String name = f.getSimpleName();
        spoon.template.Parameter p = f.getDeclaration().getAnnotation(spoon.template.Parameter.class);
        if ((p != null) && (!(p.value().equals("")))) {
            name = p.value();
        }
        return name;
    }

    /**
     * Gets the names of all the template parameters of a given template type
     * (including the ones defined by the super types).
     */
    public static java.util.List<java.lang.String> getNames(spoon.reflect.declaration.CtClass<? extends spoon.template.Template<?>> templateType) {
        java.util.List<java.lang.String> params = new java.util.ArrayList<>();
        try {
            for (spoon.reflect.reference.CtFieldReference<?> f : templateType.getAllFields()) {
                if (spoon.support.template.Parameters.isParameterSource(f)) {
                    params.add(spoon.support.template.Parameters.getParameterName(f));
                }
            }
        } catch (java.lang.Exception e) {
            throw new spoon.SpoonException("Getting of template parameters failed", e);
        }
        return params;
    }

    /**
     * Gets the Map of names to template parameter value for all the template parameters of a given template type
     * (including the ones defined by the super types).
     */
    public static java.util.Map<java.lang.String, java.lang.Object> getNamesToValues(spoon.template.Template<?> template, spoon.reflect.declaration.CtClass<? extends spoon.template.Template<?>> templateType) {
        // use linked hash map to assure same order of parameter names. There are cases during substitution of parameters when substitution order matters. E.g. SubstitutionVisitor#substituteName(...)
        java.util.Map<java.lang.String, java.lang.Object> params = new java.util.LinkedHashMap<>();
        try {
            for (spoon.reflect.reference.CtFieldReference<?> f : templateType.getAllFields()) {
                if (spoon.support.template.Parameters.isParameterSource(f)) {
                    java.lang.String parameterName = spoon.support.template.Parameters.getParameterName(f);
                    params.put(parameterName, spoon.support.template.Parameters.getValue(template, parameterName, ((java.lang.reflect.Field) (f.getActualField()))));
                }
            }
        } catch (java.lang.Exception e) {
            throw new spoon.SpoonException("Getting of template parameters failed", e);
        }
        return params;
    }

    /**
     * Gets the Map of names to template parameter values for all the template parameters of a given template type
     * + adds mapping of template model reference to target type as parameter too
     *
     * @param f
     * 		the factory
     * @param targetType
     * 		the target type of the substitution (can be null), which will be done with result parameters
     * @param template
     * 		the template that holds the parameter values
     */
    public static java.util.Map<java.lang.String, java.lang.Object> getTemplateParametersAsMap(spoon.reflect.factory.Factory f, spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template) {
        java.util.Map<java.lang.String, java.lang.Object> params = new java.util.HashMap(spoon.support.template.Parameters.getNamesToValues(template, ((spoon.reflect.declaration.CtClass) (f.Class().get(template.getClass())))));
        if (targetType != null) {
            /* there is required to replace all template model references by target type reference.
            Handle that request as template parameter too
             */
            params.put(template.getClass().getSimpleName(), targetType.getReference());
        }
        return params;
    }

    /**
     * Tells if a given field is a template parameter.
     */
    public static boolean isParameterSource(spoon.reflect.reference.CtFieldReference<?> ref) {
        spoon.reflect.declaration.CtField<?> field = ref.getDeclaration();
        if (field == null) {
            // we must have the source of this fieldref, otherwise we cannot use it as template parameter
            return false;
        }
        if ((field.getAnnotation(spoon.template.Parameter.class)) != null) {
            // it is the template field which represents template parameter, because of "Parameter" annotation
            return true;
        }
        if ((ref.getType()) instanceof spoon.reflect.reference.CtTypeParameterReference) {
            // the template fields, which are using generic type like <T>, are not template parameters
            return false;
        }
        if (ref.getSimpleName().equals("this")) {
            // the reference to this is not template parameter
            return false;
        }
        if (ref.getType().isSubtypeOf(spoon.support.template.Parameters.getTemplateParameterType(ref.getFactory()))) {
            // the type of template field is TemplateParameter.
            return true;
        }
        return false;
    }

    /**
     * Tells if a given field is a template parameter.
     */
    public static boolean isParameterSource(java.lang.reflect.Field field) {
        return ((field.getAnnotation(spoon.template.Parameter.class)) != null) || (spoon.template.TemplateParameter.class.isAssignableFrom(field.getType()));
    }

    static spoon.reflect.reference.CtTypeReference<spoon.template.TemplateParameter<?>> templateParameterType;

    @java.lang.SuppressWarnings({ "rawtypes", "unchecked" })
    private static synchronized spoon.reflect.reference.CtTypeReference<spoon.template.TemplateParameter<?>> getTemplateParameterType(spoon.reflect.factory.Factory factory) {
        if ((spoon.support.template.Parameters.templateParameterType) == null) {
            spoon.support.template.Parameters.templateParameterType = ((spoon.reflect.reference.CtTypeReference) (factory.Type().createReference(spoon.template.TemplateParameter.class)));
        }
        return spoon.support.template.Parameters.templateParameterType;
    }

    /**
     * Creates an empty template parameter of the <code>T</code> type where
     * {@link TemplateParameter#S()} does not return <code>null</code> in case
     * the template code needs to be executed such as in static initializers.
     */
    @java.lang.SuppressWarnings("unchecked")
    public static <T> spoon.template.TemplateParameter<T> NIL(java.lang.Class<? extends T> type) {
        if (java.lang.Number.class.isAssignableFrom(type)) {
            return ((spoon.template.TemplateParameter<T>) (new spoon.template.TemplateParameter<java.lang.Number>() {
                public java.lang.Number S() {
                    return 0;
                }
            }));
        }
        return new spoon.template.TemplateParameter<T>() {
            public T S() {
                return null;
            }
        };
    }

    /**
     * returns all the runtime fields of a template representing a template parameter
     */
    public static java.util.List<java.lang.reflect.Field> getAllTemplateParameterFields(java.lang.Class<? extends spoon.template.Template> clazz) {
        if (!(spoon.template.Template.class.isAssignableFrom(clazz))) {
            throw new java.lang.IllegalArgumentException();
        }
        java.util.List<java.lang.reflect.Field> result = new java.util.ArrayList<>();
        for (java.lang.reflect.Field f : spoon.support.util.RtHelper.getAllFields(clazz)) {
            if (spoon.support.template.Parameters.isParameterSource(f)) {
                result.add(f);
            }
        }
        return result;
    }

    /**
     * returns all the compile_time fields of a template representing a template parameter
     */
    public static java.util.List<spoon.reflect.declaration.CtField<?>> getAllTemplateParameterFields(java.lang.Class<? extends spoon.template.Template<?>> clazz, spoon.reflect.factory.Factory factory) {
        spoon.reflect.declaration.CtClass<?> c = factory.Class().get(clazz);
        if (c == null) {
            throw new java.lang.IllegalArgumentException("Template not in template classpath");
        }
        java.util.List<spoon.reflect.declaration.CtField<?>> result = new java.util.ArrayList<>();
        for (java.lang.reflect.Field f : spoon.support.template.Parameters.getAllTemplateParameterFields(clazz)) {
            result.add(c.getField(f.getName()));
        }
        return result;
    }
}

