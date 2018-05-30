package spoon.experimental.modelobs;


public class EmptyModelChangeListener implements spoon.experimental.modelobs.FineModelChangeListener {
    @java.lang.Override
    public void onObjectUpdate(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement newValue, spoon.reflect.declaration.CtElement oldValue) {
    }

    @java.lang.Override
    public void onObjectUpdate(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.lang.Object newValue, java.lang.Object oldValue) {
    }

    @java.lang.Override
    public void onObjectDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement oldValue) {
    }

    @java.lang.Override
    public void onListAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, spoon.reflect.declaration.CtElement newValue) {
    }

    @java.lang.Override
    public void onListAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, int index, spoon.reflect.declaration.CtElement newValue) {
    }

    @java.lang.Override
    public void onListDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, java.util.Collection<? extends spoon.reflect.declaration.CtElement> oldValue) {
        for (spoon.reflect.declaration.CtElement ctElement : oldValue) {
            onListDelete(currentElement, role, field, field.indexOf(ctElement), ctElement);
        }
    }

    @java.lang.Override
    public void onListDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, int index, spoon.reflect.declaration.CtElement oldValue) {
    }

    @java.lang.Override
    public void onListDeleteAll(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, java.util.List oldValue) {
    }

    @java.lang.Override
    public <K, V> void onMapAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Map<K, V> field, K key, spoon.reflect.declaration.CtElement newValue) {
    }

    @java.lang.Override
    public <K, V> void onMapDeleteAll(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Map<K, V> field, java.util.Map<K, V> oldValue) {
    }

    @java.lang.Override
    public void onSetAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, spoon.reflect.declaration.CtElement newValue) {
    }

    @java.lang.Override
    public <T extends java.lang.Enum> void onSetAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, T newValue) {
    }

    @java.lang.Override
    public void onSetDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, spoon.reflect.declaration.CtElement oldValue) {
    }

    @java.lang.Override
    public void onSetDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, java.util.Collection<spoon.reflect.declaration.ModifierKind> oldValue) {
        for (spoon.reflect.declaration.ModifierKind modifierKind : oldValue) {
            onSetDelete(currentElement, role, field, modifierKind);
        }
    }

    @java.lang.Override
    public void onSetDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, spoon.reflect.declaration.ModifierKind oldValue) {
    }

    @java.lang.Override
    public void onSetDeleteAll(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, java.util.Set oldValue) {
    }
}

