package spoon.reflect.visitor.filter;


public class ReturnOrThrowFilter implements spoon.reflect.visitor.Filter<spoon.reflect.code.CtCFlowBreak> {
    @java.lang.Override
    public boolean matches(spoon.reflect.code.CtCFlowBreak cflowBreak) {
        return (cflowBreak instanceof spoon.reflect.code.CtReturn) || (cflowBreak instanceof spoon.reflect.code.CtThrow);
    }
}

