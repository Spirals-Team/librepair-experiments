package spoon.experimental.modelobs;


public interface FineModelChangeListener {
    void onObjectUpdate(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement newValue, spoon.reflect.declaration.CtElement oldValue);

    void onObjectUpdate(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.lang.Object newValue, java.lang.Object oldValue);

    void onObjectDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement oldValue);

    void onListAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, spoon.reflect.declaration.CtElement newValue);

    void onListAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, int index, spoon.reflect.declaration.CtElement newValue);

    void onListDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, java.util.Collection<? extends spoon.reflect.declaration.CtElement> oldValue);

    void onListDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, int index, spoon.reflect.declaration.CtElement oldValue);

    void onListDeleteAll(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, java.util.List oldValue);

    <K, V> void onMapAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Map<K, V> field, K key, spoon.reflect.declaration.CtElement newValue);

    <K, V> void onMapDeleteAll(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Map<K, V> field, java.util.Map<K, V> oldValue);

    void onSetAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, spoon.reflect.declaration.CtElement newValue);

    <T extends java.lang.Enum> void onSetAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, T newValue);

    void onSetDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, spoon.reflect.declaration.CtElement oldValue);

    void onSetDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, java.util.Collection<spoon.reflect.declaration.ModifierKind> oldValue);

    void onSetDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, spoon.reflect.declaration.ModifierKind oldValue);

    void onSetDeleteAll(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, java.util.Set oldValue);
}

