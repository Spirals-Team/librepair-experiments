package spoon.template;


class TemplateBuilder {
    public static spoon.template.TemplateBuilder createPattern(spoon.reflect.declaration.CtElement templateRoot, spoon.template.Template<?> template) {
        spoon.reflect.declaration.CtClass<? extends spoon.template.Template<?>> templateType = spoon.template.Substitution.getTemplateCtClass(templateRoot.getFactory(), template);
        return spoon.template.TemplateBuilder.createPattern(templateRoot, templateType, template);
    }

    public static spoon.template.TemplateBuilder createPattern(spoon.reflect.declaration.CtElement templateRoot, spoon.reflect.declaration.CtClass<?> templateType, spoon.template.Template<?> template) {
        spoon.reflect.factory.Factory f = templateRoot.getFactory();
        if ((template != null) && ((templateType.getQualifiedName().equals(template.getClass().getName())) == false)) {
            throw new spoon.SpoonException(((("Unexpected template instance " + (template.getClass().getName())) + ". Expects ") + (templateType.getQualifiedName())));
        }
        spoon.pattern.PatternBuilder pb;
        @java.lang.SuppressWarnings("rawtypes")
        spoon.reflect.reference.CtTypeReference<spoon.template.TemplateParameter> templateParamRef = f.Type().createReference(spoon.template.TemplateParameter.class);
        if (templateType == templateRoot) {
            spoon.pattern.PatternBuilderHelper tv = new spoon.pattern.PatternBuilderHelper(templateType);
            {
                tv.keepTypeMembers(( typeMember) -> {
                    if ((typeMember.getAnnotation(spoon.template.Parameter.class)) != null) {
                        return false;
                    }
                    if ((typeMember.getAnnotation(spoon.template.Local.class)) != null) {
                        return false;
                    }
                    if ((typeMember instanceof spoon.reflect.declaration.CtField<?>) && (((spoon.reflect.declaration.CtField<?>) (typeMember)).getType().isSubtypeOf(templateParamRef))) {
                        return false;
                    }
                    return true;
                });
                tv.removeSuperClass();
            }
            pb = spoon.pattern.PatternBuilder.create(tv.getPatternElements());
        }else {
            pb = spoon.pattern.PatternBuilder.create(templateRoot);
        }
        java.util.Map<java.lang.String, java.lang.Object> templateParameters = (template == null) ? null : spoon.support.template.Parameters.getTemplateParametersAsMap(f, null, template);
        pb.setAutoSimplifySubstitutions(true);
        pb.configurePatternParameters(( pc) -> {
            pc.byTemplateParameter(templateParameters);
            pc.byParameterValues(templateParameters);
        });
        return new spoon.template.TemplateBuilder(templateType, pb, template);
    }

    private spoon.template.Template<?> template;

    private spoon.pattern.PatternBuilder patternBuilder;

    private spoon.reflect.declaration.CtClass<?> templateType;

    private TemplateBuilder(spoon.reflect.declaration.CtClass<?> templateType, spoon.pattern.PatternBuilder patternBuilder, spoon.template.Template<?> template) {
        this.template = template;
        this.patternBuilder = patternBuilder;
        this.templateType = templateType;
    }

    public spoon.pattern.Pattern build() {
        return patternBuilder.build();
    }

    public spoon.template.TemplateBuilder setAddGeneratedBy(boolean addGeneratedBy) {
        patternBuilder.setAddGeneratedBy(addGeneratedBy);
        return this;
    }

    public java.util.Map<java.lang.String, java.lang.Object> getTemplateParameters() {
        return getTemplateParameters(null);
    }

    public java.util.Map<java.lang.String, java.lang.Object> getTemplateParameters(spoon.reflect.declaration.CtType<?> targetType) {
        spoon.reflect.factory.Factory f = templateType.getFactory();
        return spoon.support.template.Parameters.getTemplateParametersAsMap(f, targetType, template);
    }

    public <T extends spoon.reflect.declaration.CtElement> T substituteSingle(spoon.reflect.declaration.CtType<?> targetType, java.lang.Class<T> itemType) {
        return build().generator().generate(itemType, new spoon.support.util.ImmutableMapImpl(getTemplateParameters(targetType))).get(0);
    }

    public <T extends spoon.reflect.declaration.CtElement> java.util.List<T> substituteList(spoon.reflect.factory.Factory factory, spoon.reflect.declaration.CtType<?> targetType, java.lang.Class<T> itemType) {
        return build().generator().generate(itemType, getTemplateParameters(targetType));
    }
}

