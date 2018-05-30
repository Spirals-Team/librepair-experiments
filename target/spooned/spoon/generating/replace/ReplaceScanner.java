package spoon.generating.replace;


public class ReplaceScanner extends spoon.reflect.visitor.CtScanner {
    public static final java.lang.String TARGET_REPLACE_PACKAGE = "spoon.support.visitor.replace";

    public static final java.lang.String GENERATING_REPLACE_PACKAGE = "spoon.generating.replace";

    public static final java.lang.String GENERATING_REPLACE_VISITOR = (spoon.generating.replace.ReplaceScanner.GENERATING_REPLACE_PACKAGE) + ".ReplacementVisitor";

    private final java.util.Map<java.lang.String, spoon.reflect.declaration.CtClass> listeners = new java.util.HashMap<>();

    private final spoon.reflect.declaration.CtClass<java.lang.Object> target;

    private final spoon.reflect.reference.CtExecutableReference<?> element;

    private final spoon.reflect.reference.CtExecutableReference<?> list;

    private final spoon.reflect.reference.CtExecutableReference<?> map;

    private final spoon.reflect.reference.CtExecutableReference<?> set;

    public ReplaceScanner(spoon.reflect.declaration.CtClass<java.lang.Object> target) {
        this.target = target;
        this.element = target.getMethodsByName("replaceElementIfExist").get(0).getReference();
        this.list = target.getMethodsByName("replaceInListIfExist").get(0).getReference();
        this.map = target.getMethodsByName("replaceInMapIfExist").get(0).getReference();
        this.set = target.getMethodsByName("replaceInSetIfExist").get(0).getReference();
    }

    @java.lang.Override
    public <T> void visitCtMethod(spoon.reflect.declaration.CtMethod<T> element) {
        if (!(element.getSimpleName().startsWith("visitCt"))) {
            return;
        }
        spoon.reflect.factory.Factory factory = element.getFactory();
        spoon.reflect.declaration.CtMethod<T> clone = element.clone();
        factory.Annotation().annotate(clone, java.lang.Override.class);
        clone.getBody().getStatements().clear();
        for (int i = 1; i < ((element.getBody().getStatements().size()) - 1); i++) {
            spoon.reflect.code.CtInvocation inv = element.getBody().getStatement(i);
            java.util.List<spoon.reflect.code.CtExpression<?>> invArgs = new java.util.ArrayList(inv.getArguments());
            if ((invArgs.size()) <= 1) {
                throw new java.lang.RuntimeException(((((("You forget the role argument in line " + i) + " of method ") + (element.getSimpleName())) + " from ") + (element.getDeclaringType().getQualifiedName())));
            }
            invArgs.remove(0);
            spoon.reflect.code.CtInvocation getter = ((spoon.reflect.code.CtInvocation) (invArgs.get(0)));
            if ((clone.getComments().size()) == 0) {
                final spoon.reflect.code.CtComment comment = factory.Core().createComment();
                comment.setCommentType(spoon.reflect.code.CtComment.CommentType.INLINE);
                comment.setContent(("auto-generated, see " + (spoon.generating.ReplacementVisitorGenerator.class.getName())));
                clone.addComment(comment);
            }
            java.lang.Class actualClass = getter.getType().getActualClass();
            spoon.reflect.code.CtInvocation<?> invocation = createInvocation(factory, element, invArgs, getter, actualClass);
            clone.getBody().addStatement(invocation);
        }
        target.addMethod(clone);
    }

    private <T> spoon.reflect.code.CtInvocation<?> createInvocation(spoon.reflect.factory.Factory factory, spoon.reflect.declaration.CtMethod<T> candidate, java.util.List<spoon.reflect.code.CtExpression<?>> invArgs, spoon.reflect.code.CtInvocation getter, java.lang.Class getterTypeClass) {
        spoon.reflect.code.CtInvocation<?> invocation;
        spoon.generating.replace.ReplaceScanner.Type type;
        if ((getterTypeClass.equals(java.util.Collection.class)) || (getterTypeClass.equals(java.util.List.class))) {
            invocation = factory.Code().createInvocation(null, this.list, invArgs);
            type = spoon.generating.replace.ReplaceScanner.Type.LIST;
        }else
            if (getterTypeClass.equals(java.util.Map.class)) {
                invocation = factory.Code().createInvocation(null, this.map, invArgs);
                type = spoon.generating.replace.ReplaceScanner.Type.MAP;
            }else
                if (getterTypeClass.equals(java.util.Set.class)) {
                    invocation = factory.Code().createInvocation(null, this.set, invArgs);
                    type = spoon.generating.replace.ReplaceScanner.Type.SET;
                }else {
                    invocation = factory.Code().createInvocation(null, this.element, invArgs);
                    type = spoon.generating.replace.ReplaceScanner.Type.ELEMENT;
                }


        final java.lang.String name = getter.getExecutable().getSimpleName().substring(3);
        final java.lang.String listenerName = ((getter.getExecutable().getDeclaringType().getSimpleName()) + name) + "ReplaceListener";
        spoon.reflect.declaration.CtClass listener;
        if (listeners.containsKey(listenerName)) {
            listener = listeners.get(listenerName);
        }else {
            final spoon.reflect.reference.CtTypeReference getterType = getGetterType(factory, getter);
            listener = createListenerClass(factory, listenerName, getterType, type);
            final spoon.reflect.declaration.CtMethod setter = getSetter(name, getter.getTarget().getType().getDeclaration());
            final spoon.reflect.declaration.CtField field = updateField(listener, setter.getDeclaringType().getReference());
            updateConstructor(listener, setter.getDeclaringType().getReference());
            updateSetter(factory, ((spoon.reflect.declaration.CtMethod<?>) (listener.getMethodsByName("set").get(0))), getterType, field, setter);
            final spoon.reflect.code.CtComment comment = factory.Core().createComment();
            comment.setCommentType(spoon.reflect.code.CtComment.CommentType.INLINE);
            comment.setContent(("auto-generated, see " + (spoon.generating.ReplacementVisitorGenerator.class.getName())));
            listener.addComment(comment);
            listeners.put(listenerName, listener);
        }
        invocation.addArgument(getConstructorCall(listener, factory.Code().createVariableRead(candidate.getParameters().get(0).getReference(), false)));
        return invocation;
    }

    private spoon.reflect.reference.CtTypeReference getGetterType(spoon.reflect.factory.Factory factory, spoon.reflect.code.CtInvocation getter) {
        spoon.reflect.reference.CtTypeReference getterType;
        final spoon.reflect.reference.CtTypeReference type = getter.getType();
        if (type instanceof spoon.reflect.reference.CtTypeParameterReference) {
            getterType = getTypeFromTypeParameterReference(((spoon.reflect.reference.CtTypeParameterReference) (getter.getExecutable().getDeclaration().getType())));
        }else {
            getterType = type.clone();
        }
        getterType.getActualTypeArguments().clear();
        return getterType;
    }

    private spoon.reflect.reference.CtTypeReference getTypeFromTypeParameterReference(spoon.reflect.reference.CtTypeParameterReference ctTypeParameterRef) {
        final spoon.reflect.declaration.CtMethod parentMethod = ctTypeParameterRef.getParent(spoon.reflect.declaration.CtMethod.class);
        for (spoon.reflect.declaration.CtTypeParameter formal : parentMethod.getFormalCtTypeParameters()) {
            if (formal.getSimpleName().equals(ctTypeParameterRef.getSimpleName())) {
                return ((spoon.reflect.reference.CtTypeParameterReference) (formal)).getBoundingType();
            }
        }
        final spoon.reflect.declaration.CtInterface parentInterface = ctTypeParameterRef.getParent(spoon.reflect.declaration.CtInterface.class);
        for (spoon.reflect.declaration.CtTypeParameter formal : parentInterface.getFormalCtTypeParameters()) {
            if (formal.getSimpleName().equals(ctTypeParameterRef.getSimpleName())) {
                return formal.getReference().getBoundingType();
            }
        }
        throw new spoon.SpoonException(("Can't get the type of the CtTypeParameterReference " + ctTypeParameterRef));
    }

    private spoon.reflect.declaration.CtClass createListenerClass(spoon.reflect.factory.Factory factory, java.lang.String listenerName, spoon.reflect.reference.CtTypeReference getterType, spoon.generating.replace.ReplaceScanner.Type type) {
        spoon.reflect.declaration.CtClass listener;
        listener = factory.Class().get(((spoon.generating.replace.ReplaceScanner.TARGET_REPLACE_PACKAGE) + ".CtListener")).clone();
        listener.setSimpleName(listenerName);
        target.addNestedType(listener);
        final java.util.List<spoon.reflect.reference.CtTypeReference> references = listener.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.reference.CtTypeReference>(spoon.reflect.reference.CtTypeReference.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.reference.CtTypeReference reference) {
                return ((spoon.generating.replace.ReplaceScanner.TARGET_REPLACE_PACKAGE) + ".CtListener").equals(reference.getQualifiedName());
            }
        });
        for (spoon.reflect.reference.CtTypeReference reference : references) {
            reference.setPackage(listener.getPackage().getReference());
        }
        final spoon.reflect.reference.CtTypeReference<java.lang.Object> theInterface = factory.Class().createReference((((spoon.generating.replace.ReplaceScanner.TARGET_REPLACE_PACKAGE) + ".") + (type.name)));
        theInterface.addActualTypeArgument(getterType);
        final java.util.Set<spoon.reflect.reference.CtTypeReference<?>> interfaces = new java.util.HashSet<>();
        interfaces.add(theInterface);
        listener.setSuperInterfaces(interfaces);
        return listener;
    }

    private spoon.reflect.declaration.CtParameter<?> updateConstructor(spoon.reflect.declaration.CtClass listener, spoon.reflect.reference.CtTypeReference type) {
        final spoon.reflect.declaration.CtConstructor ctConstructor = ((spoon.reflect.declaration.CtConstructor) (listener.getConstructors().toArray(new spoon.reflect.declaration.CtConstructor[listener.getConstructors().size()])[0]));
        spoon.reflect.code.CtAssignment assign = ((spoon.reflect.code.CtAssignment) (ctConstructor.getBody().getStatement(1)));
        spoon.reflect.code.CtThisAccess fieldAccess = ((spoon.reflect.code.CtThisAccess) (((spoon.reflect.code.CtFieldAccess) (assign.getAssigned())).getTarget()));
        ((spoon.reflect.code.CtTypeAccess) (fieldAccess.getTarget())).getAccessedType().setImplicit(true);
        final spoon.reflect.declaration.CtParameter<?> aParameter = ((spoon.reflect.declaration.CtParameter<?>) (ctConstructor.getParameters().get(0)));
        aParameter.setType(type);
        return aParameter;
    }

    private spoon.reflect.declaration.CtField updateField(spoon.reflect.declaration.CtClass listener, spoon.reflect.reference.CtTypeReference<?> type) {
        final spoon.reflect.declaration.CtField field = listener.getField("element");
        field.setType(type);
        return field;
    }

    private void updateSetter(spoon.reflect.factory.Factory factory, spoon.reflect.declaration.CtMethod<?> setListener, spoon.reflect.reference.CtTypeReference getterType, spoon.reflect.declaration.CtField<?> field, spoon.reflect.declaration.CtMethod setter) {
        setListener.getParameters().get(0).setType(getterType);
        spoon.reflect.code.CtInvocation ctInvocation = factory.Code().createInvocation(factory.Code().createVariableRead(field.getReference(), false), setter.getReference(), factory.Code().createVariableRead(setListener.getParameters().get(0).getReference(), false));
        spoon.reflect.code.CtBlock ctBlock = factory.Code().createCtBlock(ctInvocation);
        setListener.setBody(ctBlock);
    }

    private spoon.reflect.declaration.CtMethod getSetter(java.lang.String name, spoon.reflect.declaration.CtType declaration) {
        java.util.Set<spoon.reflect.declaration.CtMethod> allMethods = declaration.getAllMethods();
        spoon.reflect.declaration.CtMethod setter = null;
        for (spoon.reflect.declaration.CtMethod aMethod : allMethods) {
            if (("set" + name).equals(aMethod.getSimpleName())) {
                setter = aMethod;
                break;
            }
        }
        return setter;
    }

    private spoon.reflect.code.CtConstructorCall<?> getConstructorCall(spoon.reflect.declaration.CtClass listener, spoon.reflect.code.CtExpression argument) {
        return listener.getFactory().Code().createConstructorCall(listener.getReference(), argument);
    }

    enum Type {
        ELEMENT("ReplaceListener"), LIST("ReplaceListListener"), SET("ReplaceSetListener"), MAP("ReplaceMapListener");
        java.lang.String name;

        Type(java.lang.String name) {
            this.name = name;
        }
    }
}

