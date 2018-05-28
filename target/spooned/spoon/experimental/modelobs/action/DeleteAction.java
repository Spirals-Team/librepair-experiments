package spoon.experimental.modelobs.action;


public class DeleteAction<T> extends spoon.experimental.modelobs.action.Action {
    private T oldValue;

    public DeleteAction(spoon.experimental.modelobs.context.Context context, T oldValue) {
        super(context);
        this.oldValue = oldValue;
    }

    @java.lang.Override
    public T getChangedValue() {
        return getRemovedValue();
    }

    public T getRemovedValue() {
        return oldValue;
    }
}

