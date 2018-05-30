package spoon.reflect.visitor.printer.change;


/**
 * Knows how to handle actually printed {@link CtElement}.
 * It drives printing of main element and single value attributes,
 * while {@link SourceFragmentContextList} is used to drive printing of lists of attribute values
 */
class SourceFragmentContextNormal extends spoon.reflect.visitor.printer.change.SourceFragmentContext {
    static final spoon.reflect.visitor.printer.change.SourceFragmentContextNormal EMPTY_FRAGMENT_CONTEXT = new spoon.reflect.visitor.printer.change.SourceFragmentContextNormal();

    private final spoon.reflect.visitor.printer.change.MutableTokenWriter mutableTokenWriter;

    private spoon.reflect.visitor.printer.change.SourceFragment currentFragment;

    private spoon.reflect.declaration.CtElement element;

    /**
     * handles printing of lists of elements. E.g. type members of type.
     */
    private spoon.reflect.visitor.printer.change.SourceFragmentContext childContext;

    /**
     *
     *
     * @param mutableTokenWriter
     * 		{@link MutableTokenWriter}, which is used for printing
     * @param element
     * 		the {@link CtElement} which is printed
     * @param rootFragment
     * 		the {@link SourceFragment}, which represents whole elements. E.g. whole type or method
     */
    SourceFragmentContextNormal(spoon.reflect.visitor.printer.change.MutableTokenWriter mutableTokenWriter, spoon.reflect.declaration.CtElement element, spoon.reflect.visitor.printer.change.SourceFragment rootFragment) {
        super();
        this.mutableTokenWriter = mutableTokenWriter;
        this.element = element;
        this.currentFragment = rootFragment;
        handlePrinting();
    }

    private SourceFragmentContextNormal() {
        mutableTokenWriter = null;
        currentFragment = null;
    }

    /**
     *
     *
     * @return next {@link SourceFragment} of the actually printed element.
     */
    private spoon.reflect.visitor.printer.change.SourceFragment getNextFragment() {
        if ((currentFragment) != null) {
            return currentFragment.getNextFragmentOfSameElement();
        }
        return null;
    }

    /**
     * Called when next fragment is going to be printed
     */
    private void nextFragment() {
        currentFragment = getNextFragment();
        handlePrinting();
    }

    /**
     * checks if current fragment is modified
     * If not modified, then origin source code is directly printed and token writer is muted
     * If modified, then token writer is enabled and code is printed normally
     */
    private void handlePrinting() {
        if ((currentFragment) != null) {
            if ((currentFragment.isModified()) == false) {
                // we are going to print not modified fragment
                // print origin sources of this fragment directly
                mutableTokenWriter.getPrinterHelper().directPrint(currentFragment.getSourceCode());
                mutableTokenWriter.setMuted(true);
            }else {
                // we are printing modified fragment.
                mutableTokenWriter.setMuted(false);
                switch (currentFragment.fragmentDescriptor.kind) {
                    case NORMAL :
                        // Let it print normally
                        break;
                    case LIST :
                        // we are printing list, create a child context for the list
                        childContext = new spoon.reflect.visitor.printer.change.SourceFragmentContextList(mutableTokenWriter, element, currentFragment);
                        break;
                    default :
                        throw new spoon.SpoonException(("Unexpected fragment kind " + (currentFragment.fragmentDescriptor.kind)));
                }
            }
        }
    }

    @java.lang.Override
    void onTokenWriterToken(java.lang.String tokenWriterMethodName, java.lang.String token, java.lang.Runnable printAction) {
        if (testFagmentDescriptor(getNextFragment(), ( fd) -> fd.isTriggeredByToken(true, tokenWriterMethodName, token))) {
            // yes, the next fragment should be activated before printAction
            nextFragment();
        }
        if ((childContext) != null) {
            childContext.onTokenWriterToken(tokenWriterMethodName, token, printAction);
        }else {
            // run the print action, which we are listening for
            printAction.run();
        }
        if (testFagmentDescriptor(currentFragment, ( fd) -> fd.isTriggeredByToken(false, tokenWriterMethodName, token))) {
            // yes, the next fragment should be activated before printAction
            if ((childContext) != null) {
                // notify childContext that it finished
                childContext.onParentFinished();
                childContext = null;
            }
            nextFragment();
        }
    }

    @java.lang.Override
    void onScanElementOnRole(spoon.reflect.declaration.CtElement element, spoon.reflect.path.CtRole role, java.lang.Runnable printAction) {
        if (testFagmentDescriptor(getNextFragment(), ( fd) -> fd.isStartedByScanRole(role))) {
            // yes, the next fragment should be activated before printAction
            nextFragment();
        }
        // run the print action, which we are listening for
        if ((childContext) != null) {
            childContext.onScanElementOnRole(element, role, printAction);
        }else {
            printAction.run();
        }
        // {
        // //check if current fragment has to be finished after this action
        // if (currentFragment.fragmentDescriptor.isFinishedByScanRole(role)) {
        // nextFragment();
        // }
        // }
    }

    @java.lang.Override
    void onParentFinished() {
        // we will see if it is true... I (Pavel) am not sure yet
        throw new spoon.SpoonException("SourceFragmentContextNormal shouldn't be used as child context");
    }

    private boolean testFagmentDescriptor(spoon.reflect.visitor.printer.change.SourceFragment sourceFragment, java.util.function.Predicate<spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptor> predicate) {
        if (sourceFragment != null) {
            if ((sourceFragment.fragmentDescriptor) != null) {
                return predicate.test(sourceFragment.fragmentDescriptor);
            }
        }
        return false;
    }
}

