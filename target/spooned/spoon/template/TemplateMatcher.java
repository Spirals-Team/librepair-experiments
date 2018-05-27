package spoon.template;


public class TemplateMatcher implements spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtElement> {
    private java.util.List<spoon.reflect.code.CtInvocation<?>> getMethods(spoon.reflect.declaration.CtClass<? extends spoon.template.Template<?>> root) {
        spoon.reflect.reference.CtExecutableReference<?> methodRef = root.getFactory().Executable().createReference(root.getFactory().Type().createReference(spoon.template.TemplateParameter.class), root.getFactory().Type().createTypeParameterReference("T"), "S");
        java.util.List<spoon.reflect.code.CtInvocation<?>> meths = spoon.reflect.visitor.Query.getElements(root, new spoon.reflect.visitor.filter.InvocationFilter(methodRef));
        return meths;
    }

    private java.util.List<java.lang.String> getTemplateNameParameters(spoon.reflect.declaration.CtClass<? extends spoon.template.Template<?>> templateType) {
        return spoon.support.template.Parameters.getNames(templateType);
    }

    private java.util.List<spoon.reflect.reference.CtTypeReference<?>> getTemplateTypeParameters(final spoon.reflect.declaration.CtClass<? extends spoon.template.Template<?>> templateType) {
        final java.util.List<spoon.reflect.reference.CtTypeReference<?>> ts = new java.util.ArrayList<>();
        final java.util.Collection<java.lang.String> c = spoon.support.template.Parameters.getNames(templateType);
        new spoon.reflect.visitor.CtScanner() {
            @java.lang.Override
            public void visitCtTypeParameterReference(spoon.reflect.reference.CtTypeParameterReference reference) {
                if (c.contains(reference.getSimpleName())) {
                    ts.add(reference);
                }
            }

            @java.lang.Override
            public <T> void visitCtTypeReference(spoon.reflect.reference.CtTypeReference<T> reference) {
                if (c.contains(reference.getSimpleName())) {
                    ts.add(reference);
                }
            }
        }.scan(templateType);
        return ts;
    }

    private java.util.List<spoon.reflect.reference.CtFieldReference<?>> getVarargs(spoon.reflect.declaration.CtClass<? extends spoon.template.Template<?>> root, java.util.List<spoon.reflect.code.CtInvocation<?>> variables) {
        java.util.List<spoon.reflect.reference.CtFieldReference<?>> fields = new java.util.ArrayList<>();
        for (spoon.reflect.reference.CtFieldReference<?> field : root.getAllFields()) {
            if ((field.getType().getActualClass()) == (spoon.reflect.code.CtStatementList.class)) {
                boolean alreadyAdded = false;
                for (spoon.reflect.code.CtInvocation<?> invocation : variables) {
                    alreadyAdded |= ((spoon.reflect.code.CtFieldAccess<?>) (invocation.getTarget())).getVariable().getDeclaration().equals(field);
                }
                if (!alreadyAdded) {
                    fields.add(field);
                }
            }
        }
        return fields;
    }

    private spoon.reflect.declaration.CtElement templateRoot;

    private java.util.Map<java.lang.Object, java.lang.Object> matches = new java.util.HashMap<>();

    private java.util.List<java.lang.String> names;

    private spoon.reflect.declaration.CtClass<? extends spoon.template.Template<?>> templateType;

    private java.util.List<spoon.reflect.reference.CtTypeReference<?>> typeVariables;

    private java.util.List<spoon.reflect.reference.CtFieldReference<?>> varArgs;

    private java.util.List<spoon.reflect.code.CtInvocation<?>> variables;

    @java.lang.SuppressWarnings("unchecked")
    public TemplateMatcher(spoon.reflect.declaration.CtElement templateRoot) {
        this.templateType = templateRoot.getParent(spoon.reflect.declaration.CtClass.class);
        this.templateRoot = templateRoot;
        variables = getMethods(templateType);
        typeVariables = getTemplateTypeParameters(templateType);
        names = getTemplateNameParameters(templateType);
        varArgs = getVarargs(templateType, variables);
        if ((helperMatch(this.templateRoot, this.templateRoot)) == false) {
            throw new spoon.SpoonException("TemplateMatcher was unable to find itself, it certainly indicates a bug. Please revise your template or report an issue.");
        }
    }

    private boolean addMatch(java.lang.Object template, java.lang.Object target) {
        java.lang.Object inv = matches.get(template);
        java.lang.Object o = matches.put(template, target);
        return (null == inv) || (inv.equals(o));
    }

    private spoon.reflect.declaration.CtElement checkListStatements(java.util.List<?> teList) {
        for (java.lang.Object tem : teList) {
            if ((variables.contains(tem)) && (tem instanceof spoon.reflect.code.CtInvocation)) {
                spoon.reflect.code.CtInvocation<?> listCand = ((spoon.reflect.code.CtInvocation<?>) (tem));
                boolean ok = listCand.getFactory().Type().createReference(spoon.template.TemplateParameter.class).isSubtypeOf(listCand.getTarget().getType());
                return ok ? listCand : null;
            }
            if (tem instanceof spoon.reflect.declaration.CtVariable) {
                spoon.reflect.declaration.CtVariable<?> var = ((spoon.reflect.declaration.CtVariable<?>) (tem));
                java.lang.String name = var.getSimpleName();
                for (spoon.reflect.reference.CtFieldReference<?> f : varArgs) {
                    if (f.getSimpleName().equals(name)) {
                        return f.getDeclaration();
                    }
                }
            }
        }
        return null;
    }

    public <T extends spoon.reflect.declaration.CtElement> java.util.List<T> find(final spoon.reflect.declaration.CtElement targetRoot) {
        return targetRoot.filterChildren(this).list();
    }

    private spoon.support.template.ParameterMatcher findParameterMatcher(spoon.reflect.declaration.CtNamedElement templateDeclaration) throws java.lang.IllegalAccessException, java.lang.InstantiationException {
        if (templateDeclaration == null) {
            return new spoon.support.template.DefaultParameterMatcher();
        }
        java.lang.String name = templateDeclaration.getSimpleName();
        spoon.reflect.declaration.CtClass<?> clazz = null;
        try {
            clazz = templateDeclaration.getParent(spoon.reflect.declaration.CtClass.class);
        } catch (spoon.reflect.declaration.ParentNotInitializedException e) {
            spoon.Launcher.LOGGER.error(e.getMessage(), e);
        }
        if (clazz == null) {
            return new spoon.support.template.DefaultParameterMatcher();
        }
        java.util.Collection<spoon.reflect.reference.CtFieldReference<?>> fields = clazz.getAllFields();
        spoon.reflect.reference.CtFieldReference<?> param = null;
        for (spoon.reflect.reference.CtFieldReference<?> fieldRef : fields) {
            spoon.template.Parameter p = fieldRef.getDeclaration().getAnnotation(spoon.template.Parameter.class);
            if (p == null) {
                continue;
            }
            java.lang.String proxy = p.value();
            if (!("".equals(proxy))) {
                if (name.contains(proxy)) {
                    param = fieldRef;
                    break;
                }
            }
            if (name.contains(fieldRef.getSimpleName())) {
                param = fieldRef;
                break;
            }
        }
        return getParameterInstance(param);
    }

    @java.lang.SuppressWarnings("unused")
    private java.lang.String getBindedParameter(java.lang.String pname) {
        final java.lang.String[] x = new java.lang.String[1];
        x[0] = pname;
        new spoon.reflect.visitor.CtScanner() {
            @java.lang.Override
            public <T> void visitCtField(spoon.reflect.declaration.CtField<T> f) {
                spoon.template.Parameter p = f.getAnnotation(spoon.template.Parameter.class);
                if ((p != null) && (p.value().equals(x[0]))) {
                    x[0] = f.getSimpleName();
                    return;
                }
                super.visitCtField(f);
            }
        }.scan(templateType);
        return x[0];
    }

    private java.util.Map<java.lang.Object, java.lang.Object> getMatches() {
        return matches;
    }

    private spoon.support.template.ParameterMatcher getParameterInstance(spoon.reflect.reference.CtFieldReference<?> param) throws java.lang.IllegalAccessException, java.lang.InstantiationException {
        if (param == null) {
            return new spoon.support.template.DefaultParameterMatcher();
        }
        spoon.template.Parameter anParam = param.getDeclaration().getAnnotation(spoon.template.Parameter.class);
        if (anParam == null) {
            return new spoon.support.template.DefaultParameterMatcher();
        }
        java.lang.Class<? extends spoon.support.template.ParameterMatcher> pm = anParam.match();
        spoon.support.template.ParameterMatcher instance = pm.newInstance();
        return instance;
    }

    private boolean helperMatch(java.lang.Object target, java.lang.Object template) {
        if ((target == null) && (template == null)) {
            return true;
        }
        if ((target == null) || (template == null)) {
            return false;
        }
        if ((spoon.template.TemplateMatcher.containsSame(variables, template)) || (spoon.template.TemplateMatcher.containsSame(typeVariables, template))) {
            boolean add = invokeCallBack(target, template);
            if (add) {
                return addMatch(template, target);
            }
            return false;
        }
        if ((target.getClass()) != (template.getClass())) {
            return false;
        }
        if ((template instanceof spoon.reflect.reference.CtTypeReference) && (template.equals(templateType.getReference()))) {
            return true;
        }
        if ((template instanceof spoon.reflect.reference.CtPackageReference) && (template.equals(templateType.getPackage()))) {
            return true;
        }
        if (template instanceof spoon.reflect.reference.CtReference) {
            spoon.reflect.reference.CtReference tRef = ((spoon.reflect.reference.CtReference) (template));
            boolean ok = matchNames(tRef.getSimpleName(), ((spoon.reflect.reference.CtReference) (target)).getSimpleName());
            if (ok && (!(template.equals(target)))) {
                boolean remove = !(invokeCallBack(target, template));
                if (remove) {
                    matches.remove(tRef.getSimpleName());
                    return false;
                }
                return true;
            }
        }
        if (template instanceof spoon.reflect.declaration.CtNamedElement) {
            spoon.reflect.declaration.CtNamedElement named = ((spoon.reflect.declaration.CtNamedElement) (template));
            boolean ok = matchNames(named.getSimpleName(), ((spoon.reflect.declaration.CtNamedElement) (target)).getSimpleName());
            if (ok && (!(template.equals(target)))) {
                boolean remove = !(invokeCallBack(target, template));
                if (remove) {
                    matches.remove(named.getSimpleName());
                    return false;
                }
            }
        }
        if (template instanceof java.util.Collection) {
            return matchCollections(((java.util.Collection<?>) (target)), ((java.util.Collection<?>) (template)));
        }
        if (template instanceof java.util.Map) {
            if (template.equals(target)) {
                return true;
            }
            java.util.Map<?, ?> temMap = ((java.util.Map<?, ?>) (template));
            java.util.Map<?, ?> tarMap = ((java.util.Map<?, ?>) (target));
            if (!(temMap.keySet().equals(tarMap.keySet()))) {
                return false;
            }
            return matchCollections(tarMap.values(), temMap.values());
        }
        if (template instanceof spoon.reflect.code.CtBlock<?>) {
            final java.util.List<spoon.reflect.code.CtStatement> statements = ((spoon.reflect.code.CtBlock) (template)).getStatements();
            if (((statements.size()) == 1) && ((statements.get(0)) instanceof spoon.reflect.code.CtInvocation)) {
                final spoon.reflect.code.CtInvocation ctStatement = ((spoon.reflect.code.CtInvocation) (statements.get(0)));
                if (("S".equals(ctStatement.getExecutable().getSimpleName())) && (spoon.reflect.code.CtBlock.class.equals(ctStatement.getType().getActualClass()))) {
                    return true;
                }
            }
        }
        if (target instanceof spoon.reflect.declaration.CtElement) {
            for (java.lang.reflect.Field f : spoon.support.util.RtHelper.getAllFields(target.getClass())) {
                f.setAccessible(true);
                if (java.lang.reflect.Modifier.isStatic(f.getModifiers())) {
                    continue;
                }
                if (f.getName().equals("parent")) {
                    continue;
                }
                if (f.getName().equals("position")) {
                    continue;
                }
                if (f.getName().equals("docComment")) {
                    continue;
                }
                if (f.getName().equals("factory")) {
                    continue;
                }
                if (f.getName().equals("comments")) {
                    continue;
                }
                if (f.getName().equals("metadata")) {
                    continue;
                }
                try {
                    if (!(helperMatch(f.get(target), f.get(template)))) {
                        return false;
                    }
                } catch (java.lang.IllegalAccessException ignore) {
                }
            }
            return true;
        }else
            if (target instanceof java.lang.String) {
                return matchNames(((java.lang.String) (template)), ((java.lang.String) (target)));
            }else {
                return target.equals(template);
            }

    }

    private boolean invokeCallBack(java.lang.Object target, java.lang.Object template) {
        try {
            if (template instanceof spoon.reflect.code.CtInvocation) {
                spoon.reflect.code.CtFieldAccess<?> param = ((spoon.reflect.code.CtFieldAccess<?>) (((spoon.reflect.code.CtInvocation<?>) (template)).getTarget()));
                spoon.support.template.ParameterMatcher instance = getParameterInstance(param.getVariable());
                return instance.match(this, ((spoon.reflect.code.CtInvocation<?>) (template)), ((spoon.reflect.declaration.CtElement) (target)));
            }else
                if (template instanceof spoon.reflect.reference.CtReference) {
                    spoon.reflect.reference.CtReference ref = ((spoon.reflect.reference.CtReference) (template));
                    spoon.support.template.ParameterMatcher instance;
                    if (((ref.getDeclaration()) == null) || ((ref.getDeclaration().getAnnotation(spoon.template.Parameter.class)) == null)) {
                        instance = new spoon.support.template.DefaultParameterMatcher();
                    }else {
                        spoon.template.Parameter param = ref.getDeclaration().getAnnotation(spoon.template.Parameter.class);
                        instance = param.match().newInstance();
                    }
                    return instance.match(this, ((spoon.reflect.reference.CtReference) (template)), ((spoon.reflect.reference.CtReference) (target)));
                }else
                    if (template instanceof spoon.reflect.declaration.CtNamedElement) {
                        spoon.reflect.declaration.CtNamedElement named = ((spoon.reflect.declaration.CtNamedElement) (template));
                        spoon.support.template.ParameterMatcher instance = findParameterMatcher(named);
                        return instance.match(this, ((spoon.reflect.declaration.CtElement) (template)), ((spoon.reflect.declaration.CtElement) (target)));
                    }else {
                        throw new java.lang.RuntimeException();
                    }


        } catch (java.lang.InstantiationException e) {
            spoon.Launcher.LOGGER.error(e.getMessage(), e);
            return true;
        } catch (java.lang.IllegalAccessException e) {
            spoon.Launcher.LOGGER.error(e.getMessage(), e);
            return true;
        }
    }

    private boolean isCurrentTemplate(java.lang.Object object, spoon.reflect.declaration.CtElement inMulti) {
        if (object instanceof spoon.reflect.code.CtInvocation<?>) {
            return object.equals(inMulti);
        }
        if (object instanceof spoon.reflect.declaration.CtParameter) {
            spoon.reflect.declaration.CtParameter<?> param = ((spoon.reflect.declaration.CtParameter<?>) (object));
            for (spoon.reflect.reference.CtFieldReference<?> varArg : varArgs) {
                if (param.getSimpleName().equals(varArg.getSimpleName())) {
                    return varArg.equals(inMulti);
                }
            }
        }
        return false;
    }

    @java.lang.Override
    public boolean matches(spoon.reflect.declaration.CtElement targetRoot) {
        if (targetRoot == (templateRoot)) {
            return false;
        }
        return helperMatch(targetRoot, templateRoot);
    }

    @java.lang.SuppressWarnings("unchecked")
    private boolean matchCollections(java.util.Collection<?> target, java.util.Collection<?> template) {
        final java.util.List<java.lang.Object> teList = new java.util.ArrayList<>(template);
        final java.util.List<java.lang.Object> taList = new java.util.ArrayList<>(target);
        spoon.reflect.declaration.CtElement inMulti = nextListStatement(teList, null);
        java.util.List<java.lang.Object> multi = new java.util.ArrayList<>();
        if (null == inMulti) {
            if ((teList.size()) != (taList.size())) {
                return false;
            }
            for (int te = 0, ta = 0; (te < (teList.size())) && (ta < (taList.size())); te++ , ta++) {
                if (!(helperMatch(taList.get(ta), teList.get(te)))) {
                    return false;
                }
            }
            return true;
        }
        for (int te = 0, ta = 0; (te < (teList.size())) && (ta < (taList.size())); te++ , ta++) {
            if (isCurrentTemplate(teList.get(te), inMulti)) {
                if ((te + 1) >= (teList.size())) {
                    multi.addAll(taList.subList(te, taList.size()));
                    spoon.reflect.code.CtStatementList tpl = templateType.getFactory().Core().createStatementList();
                    tpl.setStatements(((java.util.List<spoon.reflect.code.CtStatement>) ((java.util.List<?>) (multi))));
                    if (!(invokeCallBack(tpl, inMulti))) {
                        return false;
                    }
                    boolean ret = addMatch(inMulti, multi);
                    return ret;
                }
                te++;
                while (((te < (teList.size())) && (ta < (taList.size()))) && (!(helperMatch(taList.get(ta), teList.get(te))))) {
                    multi.add(taList.get(ta));
                    ta++;
                } 
                spoon.reflect.code.CtStatementList tpl = templateType.getFactory().Core().createStatementList();
                tpl.setStatements(((java.util.List<spoon.reflect.code.CtStatement>) ((java.util.List<?>) (multi))));
                if (!(invokeCallBack(tpl, inMulti))) {
                    return false;
                }
                addMatch(inMulti, tpl);
                inMulti = nextListStatement(teList, inMulti);
                multi = new java.util.ArrayList<>();
            }else {
                if (!(helperMatch(taList.get(ta), teList.get(te)))) {
                    return false;
                }
                if ((!((ta + 1) < (taList.size()))) && (inMulti != null)) {
                    spoon.reflect.code.CtStatementList tpl = templateType.getFactory().Core().createStatementList();
                    for (java.lang.Object o : multi) {
                        tpl.addStatement(((spoon.reflect.code.CtStatement) (o)));
                    }
                    if (!(invokeCallBack(tpl, inMulti))) {
                        return false;
                    }
                    addMatch(inMulti, tpl);
                    inMulti = nextListStatement(teList, inMulti);
                    multi = new java.util.ArrayList<>();
                }
            }
        }
        return true;
    }

    private boolean matchNames(java.lang.String templateName, java.lang.String elementName) {
        for (java.lang.String templateParameterName : names) {
            if (templateName.contains(templateParameterName)) {
                java.lang.String newName = templateName.replace(templateParameterName, "(.*)");
                java.util.regex.Pattern p = java.util.regex.Pattern.compile(newName);
                java.util.regex.Matcher m = p.matcher(elementName);
                if (!(m.matches())) {
                    return false;
                }
                boolean ok = addMatch(templateParameterName, m.group(1));
                if (!ok) {
                    spoon.Launcher.LOGGER.debug("incongruent match");
                    return false;
                }
                return true;
            }
        }
        return templateName.equals(elementName);
    }

    private spoon.reflect.declaration.CtElement nextListStatement(java.util.List<?> teList, spoon.reflect.declaration.CtElement inMulti) {
        if (inMulti == null) {
            return checkListStatements(teList);
        }
        java.util.List<?> teList2 = new java.util.ArrayList<java.lang.Object>(teList);
        if (inMulti instanceof spoon.reflect.code.CtInvocation) {
            teList2.remove(inMulti);
        }else
            if (inMulti instanceof spoon.reflect.declaration.CtVariable) {
                spoon.reflect.declaration.CtVariable<?> var = ((spoon.reflect.declaration.CtVariable<?>) (inMulti));
                for (java.util.Iterator<?> iter = teList2.iterator(); iter.hasNext();) {
                    spoon.reflect.declaration.CtVariable<?> teVar = ((spoon.reflect.declaration.CtVariable<?>) (iter.next()));
                    if (teVar.getSimpleName().equals(var.getSimpleName())) {
                        iter.remove();
                    }
                }
            }

        return checkListStatements(teList2);
    }

    private static boolean containsSame(java.lang.Iterable<? extends java.lang.Object> collection, java.lang.Object item) {
        for (java.lang.Object object : collection) {
            if (object == item) {
                return true;
            }
        }
        return false;
    }
}

