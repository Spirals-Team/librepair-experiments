package spoon.reflect.visitor.printer.change;


class SourceFragmentContextNormal extends spoon.reflect.visitor.printer.change.SourceFragmentContext {
    static final spoon.reflect.visitor.printer.change.SourceFragmentContextNormal EMPTY_FRAGMENT_CONTEXT = new spoon.reflect.visitor.printer.change.SourceFragmentContextNormal();

    private final spoon.reflect.visitor.printer.change.MutableTokenWriter mutableTokenWriter;

    private spoon.reflect.visitor.printer.change.SourceFragment currentFragment;

    private spoon.reflect.declaration.CtElement element;

    private spoon.reflect.visitor.printer.change.SourceFragmentContext childContext;

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

    private spoon.reflect.visitor.printer.change.SourceFragment getNextFragment() {
        if ((currentFragment) != null) {
            return currentFragment.getNextFragmentOfSameElement();
        }
        return null;
    }

    private void nextFragment() {
        currentFragment = getNextFragment();
        handlePrinting();
    }

    private void handlePrinting() {
        if ((currentFragment) != null) {
            if ((currentFragment.isModified()) == false) {
                mutableTokenWriter.getPrinterHelper().directPrint(currentFragment.getSourceCode());
                mutableTokenWriter.setMuted(true);
            }else {
                mutableTokenWriter.setMuted(false);
                switch (currentFragment.fragmentDescriptor.kind) {
                    case NORMAL :
                        break;
                    case LIST :
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
            nextFragment();
        }
        if ((childContext) != null) {
            childContext.onTokenWriterToken(tokenWriterMethodName, token, printAction);
        }else {
            printAction.run();
        }
        if (testFagmentDescriptor(currentFragment, ( fd) -> fd.isTriggeredByToken(false, tokenWriterMethodName, token))) {
            if ((childContext) != null) {
                childContext.onParentFinished();
                childContext = null;
            }
            nextFragment();
        }
    }

    @java.lang.Override
    void onScanElementOnRole(spoon.reflect.declaration.CtElement element, spoon.reflect.path.CtRole role, java.lang.Runnable printAction) {
        if (testFagmentDescriptor(getNextFragment(), ( fd) -> fd.isStartedByScanRole(role))) {
            nextFragment();
        }
        if ((childContext) != null) {
            childContext.onScanElementOnRole(element, role, printAction);
        }else {
            printAction.run();
        }
    }

    @java.lang.Override
    void onParentFinished() {
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

