package spoon.experimental.modelobs.action;


public class DeleteAllAction<T> extends spoon.experimental.modelobs.action.DeleteAction<T> {
    public DeleteAllAction(spoon.experimental.modelobs.context.Context context, java.util.Collection oldValue) {
        super(context, ((T) (oldValue)));
    }

    public DeleteAllAction(spoon.experimental.modelobs.context.Context context, java.util.Map oldValue) {
        super(context, ((T) (oldValue)));
    }
}

