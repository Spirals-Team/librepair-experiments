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
package spoon.experimental.modelobs;


/**
 * Listens on changes  on the spoon model and remembers them
 */
public class ChangeCollector {
    private final java.util.Map<spoon.reflect.declaration.CtElement, java.util.Set<spoon.reflect.path.CtRole>> elementToChangeRole = new java.util.IdentityHashMap<>();

    private final spoon.experimental.modelobs.ChangeCollector.ChangeListener changeListener = new spoon.experimental.modelobs.ChangeCollector.ChangeListener();

    /**
     *
     *
     * @param env
     * 		to be checked {@link Environment}
     * @return {@link ChangeCollector} attached to the `env` or null if there is none
     */
    public static spoon.experimental.modelobs.ChangeCollector getChangeCollector(spoon.compiler.Environment env) {
        spoon.experimental.modelobs.FineModelChangeListener mcl = env.getModelChangeListener();
        if (mcl instanceof spoon.experimental.modelobs.ChangeCollector.ChangeListener) {
            return ((spoon.experimental.modelobs.ChangeCollector.ChangeListener) (mcl)).getChangeCollector();
        }
        return null;
    }

    /**
     * Attaches itself to {@link CtModel} to listen to all changes of it's child elements
     * TODO: it would be nicer if we might listen on changes on {@link CtElement}
     *
     * @param env
     * 		to be attached to {@link Environment}
     * @return this to support fluent API
     */
    public spoon.experimental.modelobs.ChangeCollector attachTo(spoon.compiler.Environment env) {
        env.setModelChangeListener(changeListener);
        return this;
    }

    /**
     *
     *
     * @param currentElement
     * 		the {@link CtElement} whose changes has to be checked
     * @return set of {@link CtRole}s whose attribute was directly changed on `currentElement` since this {@link ChangeCollector} was attached
     * The 'directly' means that value of attribute of `currentElement` was changed.
     * Use {@link #getChanges(CtElement)} to detect changes in child elements too
     */
    public java.util.Set<spoon.reflect.path.CtRole> getDirectChanges(spoon.reflect.declaration.CtElement currentElement) {
        java.util.Set<spoon.reflect.path.CtRole> changes = elementToChangeRole.get(currentElement);
        if (changes == null) {
            return java.util.Collections.emptySet();
        }
        return java.util.Collections.unmodifiableSet(changes);
    }

    /**
     *
     *
     * @param currentElement
     * 		the {@link CtElement} whose changes has to be checked
     * @return set of {@link CtRole}s whose attribute was changed on `currentElement`
     * or any child of this attribute was changed
     * since this {@link ChangeCollector} was attached
     */
    public java.util.Set<spoon.reflect.path.CtRole> getChanges(spoon.reflect.declaration.CtElement currentElement) {
        final java.util.Set<spoon.reflect.path.CtRole> changes = new java.util.HashSet<>(getDirectChanges(currentElement));
        final spoon.experimental.modelobs.ChangeCollector.Scanner scanner = new spoon.experimental.modelobs.ChangeCollector.Scanner();
        scanner.setListener(new spoon.reflect.visitor.chain.CtScannerListener() {
            int depth = 0;

            spoon.reflect.path.CtRole checkedRole;

            @java.lang.Override
            public spoon.reflect.visitor.chain.ScanningMode enter(spoon.reflect.declaration.CtElement element) {
                if ((depth) == 0) {
                    // we are checking children of role checkedRole
                    checkedRole = scanner.getScannedRole();
                }
                if (changes.contains(checkedRole)) {
                    // we already know that som echild of `checkedRole` attribute is modified. Skip others
                    return spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL;
                }
                if (elementToChangeRole.containsKey(element)) {
                    // we have found a modified element in children of `checkedRole`
                    changes.add(checkedRole);
                    return spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL;
                }
                (depth)++;
                // continue searching for an modification
                return spoon.reflect.visitor.chain.ScanningMode.NORMAL;
            }

            @java.lang.Override
            public void exit(spoon.reflect.declaration.CtElement element) {
                (depth)--;
            }
        });
        currentElement.accept(scanner);
        return java.util.Collections.unmodifiableSet(changes);
    }

    private static class Scanner extends spoon.reflect.visitor.EarlyTerminatingScanner<java.lang.Void> {
        spoon.reflect.path.CtRole getScannedRole() {
            return scannedRole;
        }
    }

    /**
     * Called whenever anything changes in the spoon model
     *
     * @param currentElement
     * 		the modified element
     * @param role
     * 		the modified attribute of that element
     */
    protected void onChange(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role) {
        java.util.Set<spoon.reflect.path.CtRole> roles = elementToChangeRole.get(currentElement);
        if (roles == null) {
            roles = new java.util.HashSet<>();
            elementToChangeRole.put(currentElement, roles);
        }
        if ((role.getSuperRole()) != null) {
            role = role.getSuperRole();
        }
        roles.add(role);
    }

    private class ChangeListener implements spoon.experimental.modelobs.FineModelChangeListener {
        private spoon.experimental.modelobs.ChangeCollector getChangeCollector() {
            return spoon.experimental.modelobs.ChangeCollector.this;
        }

        @java.lang.Override
        public void onObjectUpdate(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement newValue, spoon.reflect.declaration.CtElement oldValue) {
            onChange(currentElement, role);
        }

        @java.lang.Override
        public void onObjectUpdate(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.lang.Object newValue, java.lang.Object oldValue) {
            onChange(currentElement, role);
        }

        @java.lang.Override
        public void onObjectDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement oldValue) {
            onChange(currentElement, role);
        }

        @java.lang.Override
        public void onListAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, spoon.reflect.declaration.CtElement newValue) {
            onChange(currentElement, role);
        }

        @java.lang.Override
        public void onListAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, int index, spoon.reflect.declaration.CtElement newValue) {
            onChange(currentElement, role);
        }

        @java.lang.Override
        public void onListDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, java.util.Collection<? extends spoon.reflect.declaration.CtElement> oldValue) {
            onChange(currentElement, role);
        }

        @java.lang.Override
        public void onListDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, int index, spoon.reflect.declaration.CtElement oldValue) {
            onChange(currentElement, role);
        }

        @java.lang.Override
        public void onListDeleteAll(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, java.util.List oldValue) {
            onChange(currentElement, role);
        }

        @java.lang.Override
        public <K, V> void onMapAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Map<K, V> field, K key, spoon.reflect.declaration.CtElement newValue) {
            onChange(currentElement, role);
        }

        @java.lang.Override
        public <K, V> void onMapDeleteAll(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Map<K, V> field, java.util.Map<K, V> oldValue) {
            onChange(currentElement, role);
        }

        @java.lang.Override
        public void onSetAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, spoon.reflect.declaration.CtElement newValue) {
            onChange(currentElement, role);
        }

        @java.lang.Override
        public <T extends java.lang.Enum> void onSetAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, T newValue) {
            onChange(currentElement, role);
        }

        @java.lang.Override
        public void onSetDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, spoon.reflect.declaration.CtElement oldValue) {
            onChange(currentElement, role);
        }

        @java.lang.Override
        public void onSetDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, java.util.Collection<spoon.reflect.declaration.ModifierKind> oldValue) {
            onChange(currentElement, role);
        }

        @java.lang.Override
        public void onSetDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, spoon.reflect.declaration.ModifierKind oldValue) {
            onChange(currentElement, role);
        }

        @java.lang.Override
        public void onSetDeleteAll(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, java.util.Set oldValue) {
            onChange(currentElement, role);
        }
    }
}

