package spoon.experimental.modelobs;


public abstract class ActionBasedChangeListenerImpl implements spoon.experimental.modelobs.ActionBasedChangeListener , spoon.experimental.modelobs.FineModelChangeListener {
    private void propagateModelChange(final spoon.experimental.modelobs.action.Action action) {
        this.onAction(action);
        if (action instanceof spoon.experimental.modelobs.action.DeleteAllAction) {
            this.onDeleteAll(((spoon.experimental.modelobs.action.DeleteAllAction) (action)));
        }else
            if (action instanceof spoon.experimental.modelobs.action.DeleteAction) {
                this.onDelete(((spoon.experimental.modelobs.action.DeleteAction) (action)));
            }else
                if (action instanceof spoon.experimental.modelobs.action.AddAction) {
                    this.onAdd(((spoon.experimental.modelobs.action.AddAction) (action)));
                }else
                    if (action instanceof spoon.experimental.modelobs.action.UpdateAction) {
                        this.onUpdate(((spoon.experimental.modelobs.action.UpdateAction) (action)));
                    }



    }

    @java.lang.Override
    public void onObjectUpdate(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement newValue, spoon.reflect.declaration.CtElement oldValue) {
        propagateModelChange(new spoon.experimental.modelobs.action.UpdateAction<>(new spoon.experimental.modelobs.context.ObjectContext(currentElement, role), newValue, oldValue));
    }

    @java.lang.Override
    public void onObjectUpdate(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.lang.Object newValue, java.lang.Object oldValue) {
        propagateModelChange(new spoon.experimental.modelobs.action.UpdateAction<>(new spoon.experimental.modelobs.context.ObjectContext(currentElement, role), newValue, oldValue));
    }

    @java.lang.Override
    public void onObjectDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement oldValue) {
        propagateModelChange(new spoon.experimental.modelobs.action.DeleteAction<>(new spoon.experimental.modelobs.context.ObjectContext(currentElement, role), oldValue));
    }

    @java.lang.Override
    public void onListAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, spoon.reflect.declaration.CtElement newValue) {
        propagateModelChange(new spoon.experimental.modelobs.action.AddAction<>(new spoon.experimental.modelobs.context.ListContext(currentElement, role, field), newValue));
    }

    @java.lang.Override
    public void onListAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, int index, spoon.reflect.declaration.CtElement newValue) {
        propagateModelChange(new spoon.experimental.modelobs.action.AddAction<>(new spoon.experimental.modelobs.context.ListContext(currentElement, role, field, index), newValue));
    }

    @java.lang.Override
    public void onListDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, java.util.Collection<? extends spoon.reflect.declaration.CtElement> oldValue) {
        for (spoon.reflect.declaration.CtElement ctElement : oldValue) {
            onListDelete(currentElement, role, field, field.indexOf(ctElement), ctElement);
        }
    }

    @java.lang.Override
    public void onListDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, int index, spoon.reflect.declaration.CtElement oldValue) {
        propagateModelChange(new spoon.experimental.modelobs.action.DeleteAction<>(new spoon.experimental.modelobs.context.ListContext(currentElement, role, field, index), oldValue));
    }

    @java.lang.Override
    public void onListDeleteAll(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, java.util.List oldValue) {
        propagateModelChange(new spoon.experimental.modelobs.action.DeleteAllAction(new spoon.experimental.modelobs.context.ListContext(currentElement, role, field), oldValue));
    }

    @java.lang.Override
    public <K, V> void onMapAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Map<K, V> field, K key, spoon.reflect.declaration.CtElement newValue) {
        propagateModelChange(new spoon.experimental.modelobs.action.AddAction<>(new spoon.experimental.modelobs.context.MapContext<>(currentElement, role, field, key), newValue));
    }

    @java.lang.Override
    public <K, V> void onMapDeleteAll(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Map<K, V> field, java.util.Map<K, V> oldValue) {
        propagateModelChange(new spoon.experimental.modelobs.action.DeleteAllAction(new spoon.experimental.modelobs.context.MapContext<>(currentElement, role, field), oldValue));
    }

    @java.lang.Override
    public void onSetAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, spoon.reflect.declaration.CtElement newValue) {
        propagateModelChange(new spoon.experimental.modelobs.action.AddAction<>(new spoon.experimental.modelobs.context.SetContext(currentElement, role, field), newValue));
    }

    @java.lang.Override
    public <T extends java.lang.Enum> void onSetAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, T newValue) {
        propagateModelChange(new spoon.experimental.modelobs.action.AddAction<>(new spoon.experimental.modelobs.context.SetContext(currentElement, role, field), newValue));
    }

    @java.lang.Override
    public void onSetDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, spoon.reflect.declaration.CtElement oldValue) {
        propagateModelChange(new spoon.experimental.modelobs.action.DeleteAction<>(new spoon.experimental.modelobs.context.SetContext(currentElement, role, field), oldValue));
    }

    @java.lang.Override
    public void onSetDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, java.util.Collection<spoon.reflect.declaration.ModifierKind> oldValue) {
        for (spoon.reflect.declaration.ModifierKind modifierKind : oldValue) {
            onSetDelete(currentElement, role, field, modifierKind);
        }
    }

    @java.lang.Override
    public void onSetDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, spoon.reflect.declaration.ModifierKind oldValue) {
        propagateModelChange(new spoon.experimental.modelobs.action.DeleteAction<>(new spoon.experimental.modelobs.context.SetContext(currentElement, role, field), oldValue));
    }

    @java.lang.Override
    public void onSetDeleteAll(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, java.util.Set oldValue) {
        propagateModelChange(new spoon.experimental.modelobs.action.DeleteAllAction(new spoon.experimental.modelobs.context.SetContext(currentElement, role, field), oldValue));
    }

    @java.lang.Override
    public void onDelete(spoon.experimental.modelobs.action.DeleteAction action) {
    }

    @java.lang.Override
    public void onDeleteAll(spoon.experimental.modelobs.action.DeleteAllAction action) {
    }

    @java.lang.Override
    public void onAdd(spoon.experimental.modelobs.action.AddAction action) {
    }

    @java.lang.Override
    public void onUpdate(spoon.experimental.modelobs.action.UpdateAction action) {
    }

    @java.lang.Override
    public void onAction(spoon.experimental.modelobs.action.Action action) {
    }
}

