package spoon.experimental.modelobs;


public class ChangeCollector {
    private final java.util.Map<spoon.reflect.declaration.CtElement, java.util.Set<spoon.reflect.path.CtRole>> elementToChangeRole = new java.util.IdentityHashMap<>();

    private final spoon.experimental.modelobs.ChangeCollector.ChangeListener changeListener = new spoon.experimental.modelobs.ChangeCollector.ChangeListener();

    public static spoon.experimental.modelobs.ChangeCollector getChangeCollector(spoon.compiler.Environment env) {
        spoon.experimental.modelobs.FineModelChangeListener mcl = env.getModelChangeListener();
        if (mcl instanceof spoon.experimental.modelobs.ChangeCollector.ChangeListener) {
            return ((spoon.experimental.modelobs.ChangeCollector.ChangeListener) (mcl)).getChangeCollector();
        }
        return null;
    }

    public spoon.experimental.modelobs.ChangeCollector attachTo(spoon.compiler.Environment env) {
        env.setModelChangeListener(changeListener);
        return this;
    }

    public java.util.Set<spoon.reflect.path.CtRole> getDirectChanges(spoon.reflect.declaration.CtElement currentElement) {
        java.util.Set<spoon.reflect.path.CtRole> changes = elementToChangeRole.get(currentElement);
        if (changes == null) {
            return java.util.Collections.emptySet();
        }
        return java.util.Collections.unmodifiableSet(changes);
    }

    public java.util.Set<spoon.reflect.path.CtRole> getChanges(spoon.reflect.declaration.CtElement currentElement) {
        final java.util.Set<spoon.reflect.path.CtRole> changes = new java.util.HashSet<>(getDirectChanges(currentElement));
        final spoon.experimental.modelobs.ChangeCollector.Scanner scanner = new spoon.experimental.modelobs.ChangeCollector.Scanner();
        scanner.setListener(new spoon.reflect.visitor.chain.CtScannerListener() {
            int depth = 0;

            spoon.reflect.path.CtRole checkedRole;

            @java.lang.Override
            public spoon.reflect.visitor.chain.ScanningMode enter(spoon.reflect.declaration.CtElement element) {
                if ((depth) == 0) {
                    checkedRole = scanner.getScannedRole();
                }
                if (changes.contains(checkedRole)) {
                    return spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL;
                }
                if (elementToChangeRole.containsKey(element)) {
                    changes.add(checkedRole);
                    return spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL;
                }
                (depth)++;
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

