package spoon.experimental.modelobs;


public interface ActionBasedChangeListener {
    void onDelete(spoon.experimental.modelobs.action.DeleteAction action);

    void onDeleteAll(spoon.experimental.modelobs.action.DeleteAllAction action);

    void onAdd(spoon.experimental.modelobs.action.AddAction action);

    void onUpdate(spoon.experimental.modelobs.action.UpdateAction action);

    void onAction(spoon.experimental.modelobs.action.Action action);
}

