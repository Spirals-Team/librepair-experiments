package spoon.experimental.modelobs.action;


public class AddAction<T> extends spoon.experimental.modelobs.action.Action {
    private T newValue;

    public AddAction(spoon.experimental.modelobs.context.Context context, T newValue) {
        super(context);
        this.newValue = newValue;
    }

    @java.lang.Override
    public T getChangedValue() {
        return getNewValue();
    }

    public T getNewValue() {
        return newValue;
    }
}

