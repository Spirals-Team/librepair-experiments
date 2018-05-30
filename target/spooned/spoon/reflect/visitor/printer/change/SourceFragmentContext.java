package spoon.reflect.visitor.printer.change;


/**
 * Knows how to handle actually printed {@link CtElement} or it's part
 */
abstract class SourceFragmentContext {
    /**
     * Called when TokenWriter token is sent by {@link DefaultJavaPrettyPrinter}
     *
     * @param tokenWriterMethodName
     * 		the name of token method
     * @param token
     * 		the value of token
     * @param printAction
     * 		the {@link Runnable}, which will send the token to the output
     */
    abstract void onTokenWriterToken(java.lang.String tokenWriterMethodName, java.lang.String token, java.lang.Runnable printAction);

    /**
     * Called when {@link DefaultJavaPrettyPrinter} starts scanning of `element` on the parent`s role `role`
     *
     * @param element
     * 		to be scanned element
     * @param role
     * 		the attribute where the element is in parent
     * @param printAction
     * 		the {@link Runnable}, which will scan that element in {@link DefaultJavaPrettyPrinter}
     */
    abstract void onScanElementOnRole(spoon.reflect.declaration.CtElement element, spoon.reflect.path.CtRole role, java.lang.Runnable printAction);

    /**
     * Called when this is child context and parent context is just going to finish it's printing
     */
    abstract void onParentFinished();
}

