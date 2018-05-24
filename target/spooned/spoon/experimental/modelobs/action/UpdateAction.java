package spoon.experimental.modelobs.action;


public class UpdateAction<T> extends spoon.experimental.modelobs.action.Action {
    private final T oldValue;

    private final T newValue;

    public UpdateAction(spoon.experimental.modelobs.context.Context context, T newValue, T oldValue) {
        super(context);
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @java.lang.Override
    public T getChangedValue() {
        return getNewValue();
    }

    public T getNewValue() {
        return newValue;
    }

    public T getOldValue() {
        return oldValue;
    }
}

