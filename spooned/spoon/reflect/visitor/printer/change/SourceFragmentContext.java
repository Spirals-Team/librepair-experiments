package spoon.reflect.visitor.printer.change;


abstract class SourceFragmentContext {
    abstract void onTokenWriterToken(java.lang.String tokenWriterMethodName, java.lang.String token, java.lang.Runnable printAction);

    abstract void onScanElementOnRole(spoon.reflect.declaration.CtElement element, spoon.reflect.path.CtRole role, java.lang.Runnable printAction);

    abstract void onParentFinished();
}

