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
package spoon.reflect.visitor.printer.change;


/**
 * source code position helper methods
 */
public abstract class SourcePositionUtils {
    /**
     *
     *
     * @param element
     * 		target {@link CtElement}
     * @return {@link SourceFragment}, which represents origin source code of the `element`
     */
    static spoon.reflect.visitor.printer.change.SourceFragment getSourceFragmentOfElement(spoon.reflect.declaration.CtElement element) {
        spoon.reflect.cu.SourcePosition sp = element.getPosition();
        if ((sp.getCompilationUnit()) != null) {
            spoon.reflect.cu.CompilationUnit cu = sp.getCompilationUnit();
            return cu.getSourceFragment(element);
        }
        return null;
    }

    /**
     * Maps {@link FragmentType} to spoon model concept {@link #type} specific {@link FragmentDescriptor}
     */
    private static class TypeToFragmentDescriptor {
        java.lang.Class<? extends spoon.reflect.declaration.CtElement> type;

        java.util.Map<spoon.reflect.visitor.printer.change.FragmentType, spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptor> fragmentToRoles = new java.util.HashMap<>();

        TypeToFragmentDescriptor(java.lang.Class<? extends spoon.reflect.declaration.CtElement> type) {
            this.type = type;
        }

        /**
         * Creates a {@link FragmentDescriptor} of defined {@link FragmentType}
         * and initializes it using `initializer`
         *
         * @param ft
         * 		{@link FragmentType}
         * @param initializer
         * 		a code which defines behavior of that fragment
         * @return this to support fluent API
         */
        spoon.reflect.visitor.printer.change.SourcePositionUtils.TypeToFragmentDescriptor fragment(spoon.reflect.visitor.printer.change.FragmentType ft, java.util.function.Consumer<spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder> initializer) {
            spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptor fd = new spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptor();
            initializer.accept(new spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder(fd));
            fragmentToRoles.put(ft, fd);
            return this;
        }

        /**
         *
         *
         * @param element
         * 		target {@link CtElement}
         * @return true if `element` is handled by this {@link TypeToFragmentDescriptor}
         */
        boolean matchesElement(spoon.reflect.declaration.CtElement element) {
            return type.isInstance(element);
        }
    }

    enum FragmentKind {
        NORMAL, LIST;}

    /**
     * Defines how to handle printing of related fragment
     */
    static class FragmentDescriptor {
        spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentKind kind = spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentKind.NORMAL;

        /**
         * set of {@link CtRole}s, whose source code is contained in related {@link SourceFragment}.
         * For example when printing {@link CtClass}, then {@link SourceFragment} with roles=[CtRole.ANNOTATION, CtRole.MODIFIER]
         * has type {@link FragmentType#MODIFIERS} and contains source code of annotations and modifiers of printed {@link CtClass}
         */
        java.util.Set<spoon.reflect.path.CtRole> roles;

        /**
         * List of predicates, which detects whether linked {@link SourceFragment}
         * is going to be printed NOW (before the token of {@link TokenWriter} is written)
         */
        private java.util.List<java.util.function.BiPredicate<java.lang.String, java.lang.String>> startTokenDetector = new java.util.ArrayList<>();

        /**
         * List of predicates, which detects whether linked {@link SourceFragment}
         * is going to finish it's printing NOW (after the token of {@link TokenWriter} is written)
         */
        private java.util.List<java.util.function.BiPredicate<java.lang.String, java.lang.String>> endTokenDetector = new java.util.ArrayList<>();

        /**
         * Detects whether linked {@link SourceFragment}
         * is going to be printed NOW (before the CtElement on the role with respect to parent is scanned by {@link DefaultJavaPrettyPrinter})
         *
         * For example when printing CtMethod the printing of method modifiers is finished
         * when CtElement with role {@link CtRole#TYPE} enters the {@link DefaultJavaPrettyPrinter} scanner
         */
        private java.util.Set<spoon.reflect.path.CtRole> startScanRole = new java.util.HashSet<>();

        /**
         * 1) initializes fragmentDescriptors of the {@link SourceFragment}
         * 2) detects whether `fragment` contains source code of modified element attribute/role
         *
         * @param fragment
         * 		target {@link SourceFragment}
         * @param changedRoles
         * 		the modifiable {@link Set} of {@link CtRole}s, whose attributes values are modified in element of the fragment
         */
        void applyTo(spoon.reflect.visitor.printer.change.SourceFragment fragment, java.util.Set<spoon.reflect.path.CtRole> changedRoles) {
            if ((roles) != null) {
                for (spoon.reflect.path.CtRole ctRole : roles) {
                    if (changedRoles.remove(ctRole)) {
                        // the role of this fragment is modified -> fragment is modified
                        fragment.setModified(true);
                        // and continue to remove other roles if they are changed too
                        // so at the end we can detect whether all changed roles
                        // are covered by this algorithm. If not then change in such role is not supported
                        // and standard printing must be used
                    }
                }
            }
            fragment.fragmentDescriptor = this;
        }

        /**
         * Detects whether {@link TokenWriter} token just printed by {@link DefaultJavaPrettyPrinter}
         * triggers start/end usage of {@link SourceFragment} linked to this {@link FragmentDescriptor}
         *
         * @param isStart
         * 		if true then it checks start trigger. if false then it checks end trigger
         * @param tokenWriterMethodName
         * 		the name of {@link TokenWriter} method whose token is fired
         * @param token
         * 		the value of the {@link TokenWriter} token. May be null, depending on the `tokenWriterMethodName`
         * @return true this token triggers start/end of usage of {@link SourceFragment}
         */
        boolean isTriggeredByToken(boolean isStart, java.lang.String tokenWriterMethodName, java.lang.String token) {
            for (java.util.function.BiPredicate<java.lang.String, java.lang.String> predicate : isStart ? startTokenDetector : endTokenDetector) {
                if (predicate.test(tokenWriterMethodName, token)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Detects whether {@link DefaultJavaPrettyPrinter} scanning of element on `role`  triggers start usage of {@link SourceFragment}
         * linked to this {@link FragmentDescriptor}
         *
         * @param role
         * 		the role (with respect to parent) of scanned element
         * @return true is scanning of this element triggers start of usage of {@link SourceFragment}
         */
        boolean isStartedByScanRole(spoon.reflect.path.CtRole role) {
            return startScanRole.contains(role);
        }

        /**
         *
         *
         * @return {@link CtRole} of the list attribute handled by this fragment
         */
        spoon.reflect.path.CtRole getListRole() {
            if ((((kind) != (spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentKind.LIST)) || ((roles) == null)) || ((roles.size()) != 1)) {
                throw new spoon.SpoonException("This fragment does not have list role");
            }
            return roles.iterator().next();
        }
    }

    /**
     * Used to build {@link FragmentDescriptor}s of spoon model concepts in a maintenable and readable way
     */
    private static class FragmentDescriptorBuilder {
        spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptor descriptor;

        FragmentDescriptorBuilder(spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptor descriptor) {
            super();
            this.descriptor = descriptor;
        }

        /**
         *
         *
         * @param roles
         * 		the one or more roles, whose values are contained in the linked
         * 		{@link SourceFragment}. Used to detect whether {@link SourceFragment} is modified or not.
         */
        spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder role(spoon.reflect.path.CtRole... roles) {
            descriptor.roles = new java.util.HashSet(java.util.Arrays.asList(roles));
            return this;
        }

        /**
         *
         *
         * @param role
         * 		defines {@link FragmentDescriptor} of {@link SourceFragment},
         * 		which represents a list of values. E.g. list of type members.
         */
        spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder list(spoon.reflect.path.CtRole role) {
            if ((descriptor.roles) != null) {
                throw new spoon.SpoonException("Cannot combine #role and #list");
            }
            descriptor.roles = java.util.Collections.singleton(role);
            descriptor.kind = spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentKind.LIST;
            return this;
        }

        /**
         *
         *
         * @param tokens
         * 		list of {@link TokenWriter} keywords which triggers start of {@link SourceFragment}
         * 		linked by built {@link FragmentDescriptor}
         */
        spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder startWhenKeyword(java.lang.String... tokens) {
            descriptor.startTokenDetector.add(spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder.createTokenDetector("writeKeyword", tokens));
            return this;
        }

        /**
         *
         *
         * @param tokens
         * 		list of {@link TokenWriter} keywords which triggers end of {@link SourceFragment}
         * 		linked by built {@link FragmentDescriptor}
         */
        spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder endWhenKeyword(java.lang.String... tokens) {
            descriptor.endTokenDetector.add(spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder.createTokenDetector("writeKeyword", tokens));
            return this;
        }

        /**
         *
         *
         * @param tokens
         * 		list of {@link TokenWriter} separators, which triggers start of {@link SourceFragment}
         * 		linked by built {@link FragmentDescriptor}
         */
        spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder startWhenSeparator(java.lang.String... tokens) {
            descriptor.startTokenDetector.add(spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder.createTokenDetector("writeSeparator", tokens));
            return this;
        }

        /**
         *
         *
         * @param tokens
         * 		list of {@link TokenWriter} separators, which triggers end of {@link SourceFragment}
         * 		linked by built {@link FragmentDescriptor}
         */
        spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder endWhenSeparator(java.lang.String... tokens) {
            descriptor.endTokenDetector.add(spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder.createTokenDetector("writeSeparator", tokens));
            return this;
        }

        /**
         * Defines that any {@link TokenWriter} identifier triggers start of {@link SourceFragment}
         * linked by built {@link FragmentDescriptor}
         */
        spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder startWhenIdentifier() {
            descriptor.startTokenDetector.add(spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder.createTokenDetector("writeIdentifier"));
            return this;
        }

        /**
         * Defines that any {@link TokenWriter} identifier triggers end of {@link SourceFragment}
         * linked by built {@link FragmentDescriptor}
         */
        spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder endWhenIdentifier() {
            descriptor.endTokenDetector.add(spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder.createTokenDetector("writeIdentifier"));
            return this;
        }

        /**
         *
         *
         * @param roles
         * 		list of {@link CtRole}s, whose scanning triggers start of {@link SourceFragment}
         * 		linked by built {@link FragmentDescriptor}
         */
        spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptorBuilder startWhenScan(spoon.reflect.path.CtRole... roles) {
            descriptor.startScanRole.addAll(java.util.Arrays.asList(roles));
            return this;
        }

        /**
         * Creates a {@link Predicate} which detects occurrence of the {@link TokenWriter} token
         *
         * @param tokenWriterMethodName
         * 		
         * @param tokens
         * 		
         */
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

    /**
     *
     *
     * @param type
     * 		target spoon model concept Class
     * @return new {@link TypeToFragmentDescriptor} which describes the printing behavior `type`
     */
    private static spoon.reflect.visitor.printer.change.SourcePositionUtils.TypeToFragmentDescriptor type(java.lang.Class<? extends spoon.reflect.declaration.CtElement> type) {
        return new spoon.reflect.visitor.printer.change.SourcePositionUtils.TypeToFragmentDescriptor(type);
    }

    /**
     * Defines the know-how of printing of spoon model concepts
     */
    private static final java.util.List<spoon.reflect.visitor.printer.change.SourcePositionUtils.TypeToFragmentDescriptor> descriptors = // when printing any CtType
    // type(CtBlock.class)
    // .fragment(FragmentType.MAIN_FRAGMENT,
    // i -> i.list(CtRole.STATEMENT)),
    java.util.Arrays.asList(// and source fragment BODY
    // contains source code of elements on role TYPE_MEMBER, which is the list of values
    // and starts when TokenWriter is going to print separator '{'
    // and ends when TokenWriter printed separator '}'
    // and source fragment located after NAME and before BODY
    // contains source code of elements on roles SUPER_TYPE, INTERFACE, TYPE_PARAMETER
    // and source fragment NAME
    // contains source code of attribute value NAME
    // and starts when TokenWriter is going to print any identifier
    // and ends when TokenWriter printed that identifier
    // and source fragment which is located after modifiers and before the name
    // starts to be active when one of these keywords or separator '@' is fired by TokenWriter
    // then source fragment of type MODIFIERS
    // contains source code of elements on roles ANNOTATION and MODIFIER
    spoon.reflect.visitor.printer.change.SourcePositionUtils.type(spoon.reflect.declaration.CtType.class).fragment(spoon.reflect.visitor.printer.change.FragmentType.MODIFIERS, ( i) -> i.role(spoon.reflect.path.CtRole.ANNOTATION, spoon.reflect.path.CtRole.MODIFIER)).fragment(spoon.reflect.visitor.printer.change.FragmentType.BEFORE_NAME, ( i) -> i.startWhenKeyword("class", "enum", "interface").startWhenSeparator("@")).fragment(spoon.reflect.visitor.printer.change.FragmentType.NAME, ( i) -> i.role(spoon.reflect.path.CtRole.NAME).startWhenIdentifier().endWhenIdentifier()).fragment(spoon.reflect.visitor.printer.change.FragmentType.AFTER_NAME, ( i) -> i.role(spoon.reflect.path.CtRole.SUPER_TYPE, spoon.reflect.path.CtRole.INTERFACE, spoon.reflect.path.CtRole.TYPE_PARAMETER)).fragment(spoon.reflect.visitor.printer.change.FragmentType.BODY, ( i) -> i.list(spoon.reflect.path.CtRole.TYPE_MEMBER).startWhenSeparator("{").endWhenSeparator("}")), spoon.reflect.visitor.printer.change.SourcePositionUtils.type(spoon.reflect.declaration.CtExecutable.class).fragment(spoon.reflect.visitor.printer.change.FragmentType.MODIFIERS, ( i) -> i.role(spoon.reflect.path.CtRole.ANNOTATION, spoon.reflect.path.CtRole.MODIFIER)).fragment(spoon.reflect.visitor.printer.change.FragmentType.BEFORE_NAME, ( i) -> i.role(spoon.reflect.path.CtRole.TYPE).startWhenScan(spoon.reflect.path.CtRole.TYPE_PARAMETER, spoon.reflect.path.CtRole.TYPE)).fragment(spoon.reflect.visitor.printer.change.FragmentType.NAME, ( i) -> i.role(spoon.reflect.path.CtRole.NAME).startWhenIdentifier().endWhenIdentifier()).fragment(spoon.reflect.visitor.printer.change.FragmentType.AFTER_NAME, ( i) -> i.role(spoon.reflect.path.CtRole.PARAMETER, spoon.reflect.path.CtRole.THROWN).startWhenSeparator("(")).fragment(spoon.reflect.visitor.printer.change.FragmentType.BODY, ( i) -> i.role(spoon.reflect.path.CtRole.BODY).startWhenSeparator("{").endWhenSeparator("}")), spoon.reflect.visitor.printer.change.SourcePositionUtils.type(spoon.reflect.declaration.CtVariable.class).fragment(spoon.reflect.visitor.printer.change.FragmentType.MODIFIERS, ( i) -> i.role(spoon.reflect.path.CtRole.ANNOTATION, spoon.reflect.path.CtRole.MODIFIER)).fragment(spoon.reflect.visitor.printer.change.FragmentType.BEFORE_NAME, ( i) -> i.role(spoon.reflect.path.CtRole.TYPE).startWhenScan(spoon.reflect.path.CtRole.TYPE)).fragment(spoon.reflect.visitor.printer.change.FragmentType.NAME, ( i) -> i.role(spoon.reflect.path.CtRole.NAME).startWhenIdentifier().endWhenIdentifier()).fragment(spoon.reflect.visitor.printer.change.FragmentType.AFTER_NAME, ( i) -> i.role(spoon.reflect.path.CtRole.DEFAULT_EXPRESSION)));

    /**
     * Marks the {@link SourceFragment}s, which contains source code of `changedRoles` of `element`
     *
     * @param element
     * 		the {@link CtElement} which belongs to the `fragment`
     * @param fragment
     * 		the chain of sibling {@link SourceFragment}s, which represent source code fragments of `element`
     * @param changedRoles
     * 		the set of roles whose values are actually modified in `element` (so we cannot use origin source code, but have to print them normally)
     * @return true if {@link SourceFragment}s matches all changed roles, so we can use them
     * false if current  `descriptors` is insufficient and we cannot use origin source code of any fragment.
     * It happens because only few spoon model concepts have a {@link FragmentDescriptor}s
     */
    static boolean markChangedFragments(spoon.reflect.declaration.CtElement element, spoon.reflect.visitor.printer.change.SourceFragment fragment, java.util.Set<spoon.reflect.path.CtRole> changedRoles) {
        for (spoon.reflect.visitor.printer.change.SourcePositionUtils.TypeToFragmentDescriptor descriptor : spoon.reflect.visitor.printer.change.SourcePositionUtils.descriptors) {
            if (descriptor.matchesElement(element)) {
                java.util.Set<spoon.reflect.path.CtRole> toBeAssignedRoles = new java.util.HashSet<>(changedRoles);
                while (fragment != null) {
                    // check if this fragment is modified
                    spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptor fd = descriptor.fragmentToRoles.get(fragment.getFragmentType());
                    if (fd != null) {
                        // detect if `fragment` is modified and setup fragment start/end detectors
                        fd.applyTo(fragment, toBeAssignedRoles);
                    }
                    fragment = fragment.getNextFragmentOfSameElement();
                } 
                if (toBeAssignedRoles.isEmpty()) {
                    // we can use it if all changed roles are matching to some fragment
                    return true;
                }
                // check this log if you need to make printing of changes more precise
                element.getFactory().getEnvironment().debugMessage(((("The element of type " + (element.getClass().getName())) + " is not mapping these roles to SourceFragments: ") + toBeAssignedRoles));
                return false;
            }
        }
        // check this log if you need to make printing of changes more precise
        element.getFactory().getEnvironment().debugMessage((("The element of type " + (element.getClass().getName())) + " has no printing descriptor"));
        return false;
    }
}

