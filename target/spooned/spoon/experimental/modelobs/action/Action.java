package spoon.experimental.modelobs.action;


public abstract class Action {
    private final spoon.experimental.modelobs.context.Context context;

    Action(spoon.experimental.modelobs.context.Context context) {
        this.context = context;
    }

    public abstract <T> T getChangedValue();

    public spoon.experimental.modelobs.context.Context getContext() {
        return context;
    }
}

