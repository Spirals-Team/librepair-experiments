package spoon.reflect.visitor.printer.change;


class SourceFragmentContextList extends spoon.reflect.visitor.printer.change.SourceFragmentContext {
    private final spoon.reflect.visitor.printer.change.MutableTokenWriter mutableTokenWriter;

    private final spoon.reflect.visitor.printer.change.SourceFragment rootFragment;

    private final java.util.Map<spoon.reflect.declaration.CtElement, java.lang.String> listElementToPrefixSpace = new java.util.IdentityHashMap<>();

    private final java.util.List<java.lang.Runnable> separatorActions = new java.util.ArrayList<>();

    private int elementIndex = -1;

    SourceFragmentContextList(spoon.reflect.visitor.printer.change.MutableTokenWriter mutableTokenWriter, spoon.reflect.declaration.CtElement element, spoon.reflect.visitor.printer.change.SourceFragment rootFragment) {
        super();
        this.mutableTokenWriter = mutableTokenWriter;
        this.rootFragment = rootFragment;
        spoon.reflect.path.CtRole listRole = rootFragment.fragmentDescriptor.getListRole();
        java.util.List<spoon.reflect.declaration.CtElement> listElements = spoon.reflect.meta.impl.RoleHandlerHelper.getRoleHandler(element.getClass(), listRole).asList(element);
        for (spoon.reflect.declaration.CtElement ctElement : listElements) {
            spoon.reflect.visitor.printer.change.SourceFragment elementFragment = spoon.reflect.visitor.printer.change.SourcePositionUtils.getSourceFragmentOfElement(ctElement);
            if (elementFragment != null) {
                java.lang.String prefixSpace = getPrefixSpace(rootFragment, elementFragment);
                if (prefixSpace != null) {
                    listElementToPrefixSpace.put(ctElement, prefixSpace);
                }
            }
        }
    }

    private java.lang.String getPrefixSpace(spoon.reflect.visitor.printer.change.SourceFragment rootFragment, spoon.reflect.visitor.printer.change.SourceFragment elementFragment) {
        spoon.reflect.visitor.printer.change.SourceFragment child = rootFragment.getFirstChild();
        spoon.reflect.visitor.printer.change.SourceFragment lastChild = null;
        while (child != null) {
            if (child == elementFragment) {
                return lastChild == null ? null : rootFragment.getSourceCode(lastChild.getEnd(), elementFragment.getStart());
            }
            lastChild = child;
            child = child.getNextSibling();
        } 
        return null;
    }

    @java.lang.Override
    void onTokenWriterToken(java.lang.String tokenWriterMethodName, java.lang.String token, java.lang.Runnable printAction) {
        if ((elementIndex) == (-1)) {
            if ((mutableTokenWriter.isMuted()) == false) {
                java.lang.String prefix = rootFragment.getTextBeforeFirstChild();
                if (prefix != null) {
                    mutableTokenWriter.getPrinterHelper().directPrint(prefix);
                    mutableTokenWriter.setMuted(true);
                }
            }
            printAction.run();
        }else {
            separatorActions.add(printAction);
        }
    }

    @java.lang.Override
    void onScanElementOnRole(spoon.reflect.declaration.CtElement element, spoon.reflect.path.CtRole role, java.lang.Runnable printAction) {
        mutableTokenWriter.setMuted(false);
        (elementIndex)++;
        if ((elementIndex) > 0) {
            java.lang.String prefixSpaces = listElementToPrefixSpace.get(element);
            if (prefixSpaces != null) {
                if (prefixSpaces != null) {
                    mutableTokenWriter.getPrinterHelper().directPrint(prefixSpaces);
                    separatorActions.clear();
                }
            }
        }
        printStandardSpaces();
        printAction.run();
        mutableTokenWriter.setMuted(true);
    }

    @java.lang.Override
    void onParentFinished() {
        mutableTokenWriter.setMuted(false);
        java.lang.String suffix = rootFragment.getTextAfterLastChild();
        if (suffix != null) {
            mutableTokenWriter.getPrinterHelper().directPrint(suffix);
            separatorActions.clear();
        }else {
            printStandardSpaces();
        }
    }

    private void printStandardSpaces() {
        for (java.lang.Runnable runnable : separatorActions) {
            runnable.run();
        }
        separatorActions.clear();
    }
}

