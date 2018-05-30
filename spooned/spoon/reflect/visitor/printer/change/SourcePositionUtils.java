package spoon.reflect.visitor.printer.change;


public abstract class SourcePositionUtils {
    static spoon.reflect.visitor.printer.change.SourceFragment getSourceFragmentOfElement(spoon.reflect.declaration.CtElement element) {
        spoon.reflect.cu.SourcePosition sp = element.getPosition();
        if ((sp.getCompilationUnit()) != null) {
            spoon.reflect.cu.CompilationUnit cu = sp.getCompilationUnit();
            return cu.getSourceFragment(element);
        }
        return null;
    }

    private static class TypeToFragmentDescriptor {
        java.lang.Class<? extends spoon.reflect.declaration.CtElement> type;

        java.util.Map<spoon.reflect.visitor.printer.change.FragmentType, spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptor> fragmentToRoles = new java.util.HashMap<>();

        TypeToFragmentDescriptor(java.lang.Class<? extends spoon.reflect.declaration.CtElement> type) {
            this.type = type;
        }

        spoon.reflect.visitor.printer.change.SourcePositionUtils.TypeToFragmentDescriptor fragment(spoon.reflect.visitor.printer.change.FragmentType ft, java.util.function.Consumer<spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder> initializer) {
            spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptor fd = new spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptor();
            initializer.accept(new spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder(fd));
            fragmentToRoles.put(ft, fd);
            return this;
        }

        boolean matchesElement(spoon.reflect.declaration.CtElement element) {
            return type.isInstance(element);
        }
    }

    enum FragmentKind {
        NORMAL, LIST;}

    static class FragmentDescriptor {
        spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentKind kind = spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentKind.NORMAL;

        java.util.Set<spoon.reflect.path.CtRole> roles;

        private java.util.List<java.util.function.BiPredicate<java.lang.String, java.lang.String>> startTokenDetector = new java.util.ArrayList<>();

        private java.util.List<java.util.function.BiPredicate<java.lang.String, java.lang.String>> endTokenDetector = new java.util.ArrayList<>();

        private java.util.Set<spoon.reflect.path.CtRole> startScanRole = new java.util.HashSet<>();

        void applyTo(spoon.reflect.visitor.printer.change.SourceFragment fragment, java.util.Set<spoon.reflect.path.CtRole> changedRoles) {
            if ((roles) != null) {
                for (spoon.reflect.path.CtRole ctRole : roles) {
                    if (changedRoles.remove(ctRole)) {
                        fragment.setModified(true);
                    }
                }
            }
            fragment.fragmentDescriptor = this;
        }

        boolean isTriggeredByToken(boolean isStart, java.lang.String tokenWriterMethodName, java.lang.String token) {
            for (java.util.function.BiPredicate<java.lang.String, java.lang.String> predicate : isStart ? startTokenDetector : endTokenDetector) {
                if (predicate.test(tokenWriterMethodName, token)) {
                    return true;
                }
            }
            return false;
        }

        boolean isStartedByScanRole(spoon.reflect.path.CtRole role) {
            return startScanRole.contains(role);
        }

        spoon.reflect.path.CtRole getListRole() {
            if ((((kind) != (spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentKind.LIST)) || ((roles) == null)) || ((roles.size()) != 1)) {
                throw new spoon.SpoonException("This fragment does not have list role");
            }
            return roles.iterator().next();
        }
    }

    private static class FragmentDescriptorBuilder {
        spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptor descriptor;

        FragmentDescriptorBuilder(spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptor descriptor) {
            super();
            this.descriptor = descriptor;
        }

        spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder role(spoon.reflect.path.CtRole... roles) {
            descriptor.roles = new java.util.HashSet(java.util.Arrays.asList(roles));
            return this;
        }

        spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder list(spoon.reflect.path.CtRole role) {
            if ((descriptor.roles) != null) {
                throw new spoon.SpoonException("Cannot combine #role and #list");
            }
            descriptor.roles = java.util.Collections.singleton(role);
            descriptor.kind = spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentKind.LIST;
            return this;
        }

        spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder startWhenKeyword(java.lang.String... tokens) {
            descriptor.startTokenDetector.add(spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder.createTokenDetector("writeKeyword", tokens));
            return this;
        }

        spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder endWhenKeyword(java.lang.String... tokens) {
            descriptor.endTokenDetector.add(spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder.createTokenDetector("writeKeyword", tokens));
            return this;
        }

        spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder startWhenSeparator(java.lang.String... tokens) {
            descriptor.startTokenDetector.add(spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder.createTokenDetector("writeSeparator", tokens));
            return this;
        }

        spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder endWhenSeparator(java.lang.String... tokens) {
            descriptor.endTokenDetector.add(spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder.createTokenDetector("writeSeparator", tokens));
            return this;
        }

        spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder startWhenIdentifier() {
            descriptor.startTokenDetector.add(spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder.createTokenDetector("writeIdentifier"));
            return this;
        }

        spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder endWhenIdentifier() {
            descriptor.endTokenDetector.add(spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder.createTokenDetector("writeIdentifier"));
            return this;
        }

        spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder startWhenScan(spoon.reflect.path.CtRole... roles) {
            descriptor.startScanRole.addAll(java.util.Arrays.asList(roles));
            return this;
        }

        static java.util.function.BiPredicate<java.lang.String, java.lang.String> createTokenDetector(java.lang.String tokenWriterMethodName, java.lang.String... tokens) {
            java.util.function.BiPredicate<java.lang.String, java.lang.String> predicate;
            if ((tokens.length) == 0) {
                predicate = ( methodName, token) -> {
                    return methodName.equals(tokenWriterMethodName);
                };
            }else
                if ((tokens.length) == 1) {
                    java.lang.String expectedToken = tokens[0];
                    predicate = ( methodName, token) -> {
                        return (methodName.equals(tokenWriterMethodName)) && (expectedToken.equals(token));
                    };
                }else {
                    java.util.Set<java.lang.String> kw = new java.util.HashSet<>(java.util.Arrays.asList(tokens));
                    predicate = ( methodName, token) -> {
                        return (methodName.equals(tokenWriterMethodName)) && (kw.contains(token));
                    };
                }

            return predicate;
        }
    }

    private static spoon.reflect.visitor.printer.change.SourcePositionUtils.TypeToFragmentDescriptor type(java.lang.Class<? extends spoon.reflect.declaration.CtElement> type) {
        return new spoon.reflect.visitor.printer.change.SourcePositionUtils.TypeToFragmentDescriptor(type);
    }

    private static final java.util.List<spoon.reflect.visitor.printer.change.SourcePositionUtils.TypeToFragmentDescriptor> descriptors = java.util.Arrays.asList(spoon.reflect.visitor.printer.change.SourcePositionUtils.type(spoon.reflect.declaration.CtType.class).fragment(spoon.reflect.visitor.printer.change.FragmentType.MODIFIERS, ( i) -> i.role(spoon.reflect.path.CtRole.ANNOTATION, spoon.reflect.path.CtRole.MODIFIER)).fragment(spoon.reflect.visitor.printer.change.FragmentType.BEFORE_NAME, ( i) -> i.startWhenKeyword("class", "enum", "interface").startWhenSeparator("@")).fragment(spoon.reflect.visitor.printer.change.FragmentType.NAME, ( i) -> i.role(spoon.reflect.path.CtRole.NAME).startWhenIdentifier().endWhenIdentifier()).fragment(spoon.reflect.visitor.printer.change.FragmentType.AFTER_NAME, ( i) -> i.role(spoon.reflect.path.CtRole.SUPER_TYPE, spoon.reflect.path.CtRole.INTERFACE, spoon.reflect.path.CtRole.TYPE_PARAMETER)).fragment(spoon.reflect.visitor.printer.change.FragmentType.BODY, ( i) -> i.list(spoon.reflect.path.CtRole.TYPE_MEMBER).startWhenSeparator("{").endWhenSeparator("}")), spoon.reflect.visitor.printer.change.SourcePositionUtils.type(spoon.reflect.declaration.CtExecutable.class).fragment(spoon.reflect.visitor.printer.change.FragmentType.MODIFIERS, ( i) -> i.role(spoon.reflect.path.CtRole.ANNOTATION, spoon.reflect.path.CtRole.MODIFIER)).fragment(spoon.reflect.visitor.printer.change.FragmentType.BEFORE_NAME, ( i) -> i.role(spoon.reflect.path.CtRole.TYPE).startWhenScan(spoon.reflect.path.CtRole.TYPE_PARAMETER, spoon.reflect.path.CtRole.TYPE)).fragment(spoon.reflect.visitor.printer.change.FragmentType.NAME, ( i) -> i.role(spoon.reflect.path.CtRole.NAME).startWhenIdentifier().endWhenIdentifier()).fragment(spoon.reflect.visitor.printer.change.FragmentType.AFTER_NAME, ( i) -> i.role(spoon.reflect.path.CtRole.PARAMETER, spoon.reflect.path.CtRole.THROWN).startWhenSeparator("(")).fragment(spoon.reflect.visitor.printer.change.FragmentType.BODY, ( i) -> i.role(spoon.reflect.path.CtRole.BODY).startWhenSeparator("{").endWhenSeparator("}")), spoon.reflect.visitor.printer.change.SourcePositionUtils.type(spoon.reflect.declaration.CtVariable.class).fragment(spoon.reflect.visitor.printer.change.FragmentType.MODIFIERS, ( i) -> i.role(spoon.reflect.path.CtRole.ANNOTATION, spoon.reflect.path.CtRole.MODIFIER)).fragment(spoon.reflect.visitor.printer.change.FragmentType.BEFORE_NAME, ( i) -> i.role(spoon.reflect.path.CtRole.TYPE).startWhenScan(spoon.reflect.path.CtRole.TYPE)).fragment(spoon.reflect.visitor.printer.change.FragmentType.NAME, ( i) -> i.role(spoon.reflect.path.CtRole.NAME).startWhenIdentifier().endWhenIdentifier()).fragment(spoon.reflect.visitor.printer.change.FragmentType.AFTER_NAME, ( i) -> i.role(spoon.reflect.path.CtRole.DEFAULT_EXPRESSION)));

    static boolean markChangedFragments(spoon.reflect.declaration.CtElement element, spoon.reflect.visitor.printer.change.SourceFragment fragment, java.util.Set<spoon.reflect.path.CtRole> changedRoles) {
        for (spoon.reflect.visitor.printer.change.SourcePositionUtils.TypeToFragmentDescriptor descriptor : spoon.reflect.visitor.printer.change.SourcePositionUtils.descriptors) {
            if (descriptor.matchesElement(element)) {
                java.util.Set<spoon.reflect.path.CtRole> toBeAssignedRoles = new java.util.HashSet<>(changedRoles);
                while (fragment != null) {
                    spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptor fd = descriptor.fragmentToRoles.get(fragment.getFragmentType());
                    if (fd != null) {
                        fd.applyTo(fragment, toBeAssignedRoles);
                    }
                    fragment = fragment.getNextFragmentOfSameElement();
                } 
                if (toBeAssignedRoles.isEmpty()) {
                    return true;
                }
                element.getFactory().getEnvironment().debugMessage(((("The element of type " + (element.getClass().getName())) + " is not mapping these roles to SourceFragments: ") + toBeAssignedRoles));
                return false;
            }
        }
        element.getFactory().getEnvironment().debugMessage((("The element of type " + (element.getClass().getName())) + " has no printing descriptor"));
        return false;
    }
}

