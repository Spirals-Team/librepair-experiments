package spoon.reflect.visitor.printer.change;


public class SourceFragment {
    private final spoon.reflect.declaration.CtElement element;

    private final spoon.reflect.cu.SourcePosition sourcePosition;

    private final spoon.reflect.visitor.printer.change.FragmentType fragmentType;

    private boolean modified = false;

    spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptor fragmentDescriptor;

    private spoon.reflect.visitor.printer.change.SourceFragment nextSibling;

    private spoon.reflect.visitor.printer.change.SourceFragment firstChild;

    public SourceFragment(spoon.reflect.declaration.CtElement element) {
        this(element, element.getPosition(), spoon.reflect.visitor.printer.change.FragmentType.MAIN_FRAGMENT);
    }

    public SourceFragment(spoon.reflect.cu.SourcePosition sourcePosition) {
        this(null, sourcePosition, spoon.reflect.visitor.printer.change.FragmentType.MAIN_FRAGMENT);
    }

    private SourceFragment(spoon.reflect.declaration.CtElement element, spoon.reflect.cu.SourcePosition sourcePosition, spoon.reflect.visitor.printer.change.FragmentType fragmentType) {
        super();
        this.element = element;
        this.sourcePosition = sourcePosition;
        this.fragmentType = fragmentType;
        if (fragmentType == (spoon.reflect.visitor.printer.change.FragmentType.MAIN_FRAGMENT)) {
            createChildFragments();
        }
    }

    public spoon.reflect.visitor.printer.change.FragmentType getFragmentType() {
        return fragmentType;
    }

    public int getStart() {
        switch (fragmentType) {
            case MAIN_FRAGMENT :
                return sourcePosition.getSourceStart();
            case MODIFIERS :
                return ((spoon.reflect.cu.position.DeclarationSourcePosition) (sourcePosition)).getModifierSourceStart();
            case BEFORE_NAME :
                return (((spoon.reflect.cu.position.DeclarationSourcePosition) (sourcePosition)).getModifierSourceEnd()) + 1;
            case NAME :
                return ((spoon.reflect.cu.position.DeclarationSourcePosition) (sourcePosition)).getNameStart();
            case AFTER_NAME :
                return (((spoon.reflect.cu.position.DeclarationSourcePosition) (sourcePosition)).getNameEnd()) + 1;
            case BODY :
                return ((spoon.reflect.cu.position.BodyHolderSourcePosition) (sourcePosition)).getBodyStart();
        }
        throw new spoon.SpoonException(("Unsupported fragment type: " + (fragmentType)));
    }

    public int getEnd() {
        switch (fragmentType) {
            case MAIN_FRAGMENT :
                return (sourcePosition.getSourceEnd()) + 1;
            case MODIFIERS :
                return (((spoon.reflect.cu.position.DeclarationSourcePosition) (sourcePosition)).getModifierSourceEnd()) + 1;
            case BEFORE_NAME :
                return ((spoon.reflect.cu.position.DeclarationSourcePosition) (sourcePosition)).getNameStart();
            case NAME :
                return (((spoon.reflect.cu.position.DeclarationSourcePosition) (sourcePosition)).getNameEnd()) + 1;
            case AFTER_NAME :
                if ((sourcePosition) instanceof spoon.reflect.cu.position.BodyHolderSourcePosition) {
                    return ((spoon.reflect.cu.position.BodyHolderSourcePosition) (sourcePosition)).getBodyStart();
                }
                return (sourcePosition.getSourceEnd()) + 1;
            case BODY :
                return (((spoon.reflect.cu.position.BodyHolderSourcePosition) (sourcePosition)).getBodyEnd()) + 1;
        }
        throw new spoon.SpoonException(("Unsupported fragment type: " + (fragmentType)));
    }

    public spoon.reflect.cu.SourcePosition getSourcePosition() {
        return sourcePosition;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return ((((("|" + (getStart())) + ", ") + (getEnd())) + "|") + (getSourceCode())) + "|";
    }

    public java.lang.String getSourceCode() {
        return getSourceCode(getStart(), getEnd());
    }

    public java.lang.String getSourceCode(int start, int end) {
        spoon.reflect.cu.CompilationUnit cu = sourcePosition.getCompilationUnit();
        if (cu != null) {
            java.lang.String src = cu.getOriginalSourceCode();
            if (src != null) {
                return src.substring(start, end);
            }
        }
        return null;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    private boolean isFromSameSource(spoon.reflect.cu.SourcePosition position) {
        return sourcePosition.getCompilationUnit().equals(position.getCompilationUnit());
    }

    public void addTreeOfSourceFragmentsOfElement(spoon.reflect.declaration.CtElement element) {
        spoon.reflect.cu.SourcePosition sp = element.getPosition();
        java.util.Deque<spoon.reflect.visitor.printer.change.SourceFragment> parents = new java.util.ArrayDeque<>();
        parents.push(this);
        new spoon.reflect.visitor.CtScanner() {
            int noSource = 0;

            @java.lang.Override
            protected void enter(spoon.reflect.declaration.CtElement e) {
                spoon.reflect.visitor.printer.change.SourceFragment newFragment = addChild(parents.peek(), e);
                if (newFragment != null) {
                    parents.push(newFragment);
                }else {
                    (noSource)++;
                }
            }

            @java.lang.Override
            protected void exit(spoon.reflect.declaration.CtElement e) {
                if ((noSource) == 0) {
                    parents.pop();
                }else {
                    (noSource)--;
                }
            }
        }.scan(element);
    }

    private spoon.reflect.visitor.printer.change.SourceFragment addChild(spoon.reflect.visitor.printer.change.SourceFragment parentFragment, spoon.reflect.declaration.CtElement otherElement) {
        spoon.reflect.cu.SourcePosition otherSourcePosition = otherElement.getPosition();
        if ((otherSourcePosition instanceof spoon.support.reflect.cu.position.SourcePositionImpl) && ((otherSourcePosition.getCompilationUnit()) != null)) {
            spoon.support.reflect.cu.position.SourcePositionImpl childSPI = ((spoon.support.reflect.cu.position.SourcePositionImpl) (otherSourcePosition));
            if ((parentFragment.sourcePosition) != childSPI) {
                if (parentFragment.isFromSameSource(otherSourcePosition)) {
                    spoon.reflect.visitor.printer.change.SourceFragment otherFragment = new spoon.reflect.visitor.printer.change.SourceFragment(otherElement);
                    spoon.reflect.visitor.printer.change.SourceFragment.CMP cmp = parentFragment.compare(otherFragment);
                    if (cmp == (spoon.reflect.visitor.printer.change.SourceFragment.CMP.OTHER_IS_CHILD)) {
                        parentFragment.addChild(otherFragment);
                        return otherFragment;
                    }else {
                        if ((cmp == (spoon.reflect.visitor.printer.change.SourceFragment.CMP.OTHER_IS_AFTER)) || (cmp == (spoon.reflect.visitor.printer.change.SourceFragment.CMP.OTHER_IS_BEFORE))) {
                            if (otherElement instanceof spoon.reflect.code.CtComment) {
                                addChild(otherFragment);
                                return otherFragment;
                            }
                        }
                        throw new spoon.SpoonException(((("The SourcePosition of elements are not consistent\nparentFragment: " + parentFragment) + "\notherFragment: ") + otherFragment));
                    }
                }else {
                    throw new spoon.SpoonException(((("SourcePosition from unexpected compilation unit: " + otherSourcePosition) + " expected is: ") + (parentFragment.sourcePosition)));
                }
            }
            return null;
        }
        return null;
    }

    public spoon.reflect.visitor.printer.change.SourceFragment add(spoon.reflect.visitor.printer.change.SourceFragment other) {
        if ((this) == other) {
            throw new spoon.SpoonException("SourceFragment#add must not be called twice for the same SourceFragment");
        }
        spoon.reflect.visitor.printer.change.SourceFragment.CMP cmp = this.compare(other);
        switch (cmp) {
            case OTHER_IS_AFTER :
                addNextSibling(other);
                return this;
            case OTHER_IS_BEFORE :
                other.addNextSibling(this);
                return other;
            case OTHER_IS_CHILD :
                addChild(other);
                return this;
            case OTHER_IS_PARENT :
                other.addChild(this);
                other.mergeSiblingsOfChild(this);
                return other;
        }
        throw new spoon.SpoonException(("Unexpected compare result: " + cmp));
    }

    private void mergeSiblingsOfChild(spoon.reflect.visitor.printer.change.SourceFragment other) {
        spoon.reflect.visitor.printer.change.SourceFragment prevOther = other;
        other = prevOther.getNextSibling();
        while (other != null) {
            spoon.reflect.visitor.printer.change.SourceFragment.CMP cmp = compare(other);
            if (cmp == (spoon.reflect.visitor.printer.change.SourceFragment.CMP.OTHER_IS_CHILD)) {
                prevOther = other;
                other = other.getNextSibling();
                continue;
            }else
                if (cmp == (spoon.reflect.visitor.printer.change.SourceFragment.CMP.OTHER_IS_AFTER)) {
                    prevOther.nextSibling = null;
                    addNextSibling(other);
                    return;
                }

            throw new spoon.SpoonException("Unexpected child SourceFragment");
        } 
    }

    private void addChild(spoon.reflect.visitor.printer.change.SourceFragment child) {
        if ((firstChild) == null) {
            firstChild = child;
        }else {
            firstChild = firstChild.add(child);
        }
    }

    private void addNextSibling(spoon.reflect.visitor.printer.change.SourceFragment sibling) {
        if ((nextSibling) == null) {
            nextSibling = sibling;
        }else {
            nextSibling = nextSibling.add(sibling);
        }
    }

    private enum CMP {
        OTHER_IS_BEFORE, OTHER_IS_AFTER, OTHER_IS_CHILD, OTHER_IS_PARENT;}

    private spoon.reflect.visitor.printer.change.SourceFragment.CMP compare(spoon.reflect.visitor.printer.change.SourceFragment other) {
        if (other == (this)) {
            throw new spoon.SpoonException("SourcePositionImpl#addNextSibling must not be called twice for the same SourcePosition");
        }
        if ((getEnd()) <= (other.getStart())) {
            return spoon.reflect.visitor.printer.change.SourceFragment.CMP.OTHER_IS_AFTER;
        }
        if ((other.getEnd()) <= (getStart())) {
            return spoon.reflect.visitor.printer.change.SourceFragment.CMP.OTHER_IS_BEFORE;
        }
        if (((getStart()) <= (other.getStart())) && ((getEnd()) >= (other.getEnd()))) {
            return spoon.reflect.visitor.printer.change.SourceFragment.CMP.OTHER_IS_CHILD;
        }
        if (((getStart()) >= (other.getStart())) && ((getEnd()) <= (other.getEnd()))) {
            return spoon.reflect.visitor.printer.change.SourceFragment.CMP.OTHER_IS_PARENT;
        }
        throw new spoon.SpoonException((((((((("Cannot compare this: [" + (getStart())) + ", ") + (getEnd())) + "] with other: [\"") + (other.getStart())) + "\", \"") + (other.getEnd())) + "\"]"));
    }

    private void createChildFragments() {
        if ((sourcePosition) instanceof spoon.reflect.cu.position.DeclarationSourcePosition) {
            spoon.reflect.cu.position.DeclarationSourcePosition dsp = ((spoon.reflect.cu.position.DeclarationSourcePosition) (sourcePosition));
            int endOfLastFragment = dsp.getSourceStart();
            if (endOfLastFragment < (dsp.getModifierSourceStart())) {
                throw new spoon.SpoonException(("DeclarationSourcePosition#sourceStart < modifierSourceStart for: " + (sourcePosition)));
            }
            addChild(new spoon.reflect.visitor.printer.change.SourceFragment(element, sourcePosition, spoon.reflect.visitor.printer.change.FragmentType.MODIFIERS));
            if (endOfLastFragment < (dsp.getNameStart())) {
                addChild(new spoon.reflect.visitor.printer.change.SourceFragment(element, sourcePosition, spoon.reflect.visitor.printer.change.FragmentType.BEFORE_NAME));
            }
            addChild(new spoon.reflect.visitor.printer.change.SourceFragment(element, sourcePosition, spoon.reflect.visitor.printer.change.FragmentType.NAME));
            if (dsp instanceof spoon.reflect.cu.position.BodyHolderSourcePosition) {
                spoon.reflect.cu.position.BodyHolderSourcePosition bhsp = ((spoon.reflect.cu.position.BodyHolderSourcePosition) (dsp));
                if (endOfLastFragment < (bhsp.getBodyStart())) {
                    addChild(new spoon.reflect.visitor.printer.change.SourceFragment(element, sourcePosition, spoon.reflect.visitor.printer.change.FragmentType.AFTER_NAME));
                }
                spoon.reflect.visitor.printer.change.SourceFragment bodyFragment = new spoon.reflect.visitor.printer.change.SourceFragment(element, sourcePosition, spoon.reflect.visitor.printer.change.FragmentType.BODY);
                addChild(bodyFragment);
                if ((bodyFragment.getEnd()) != ((bhsp.getBodyEnd()) + 1)) {
                    throw new spoon.SpoonException(("Last bodyEnd is not equal to SourcePosition#sourceEnd: " + (sourcePosition)));
                }
            }else {
                if (endOfLastFragment < ((dsp.getSourceEnd()) + 1)) {
                    addChild(new spoon.reflect.visitor.printer.change.SourceFragment(element, sourcePosition, spoon.reflect.visitor.printer.change.FragmentType.AFTER_NAME));
                }
            }
        }
    }

    public spoon.reflect.visitor.printer.change.SourceFragment getNextSibling() {
        return nextSibling;
    }

    public spoon.reflect.visitor.printer.change.SourceFragment getFirstChild() {
        return firstChild;
    }

    public spoon.reflect.visitor.printer.change.SourceFragment getSourceFragmentOf(int start, int end) {
        int myEnd = getEnd();
        if (myEnd <= start) {
            if ((nextSibling) == null) {
                return null;
            }
            return getRootFragmentOfElement(nextSibling.getSourceFragmentOf(start, end));
        }
        int myStart = getStart();
        if (myStart <= start) {
            if (myEnd >= end) {
                if ((myStart == start) && (myEnd == end)) {
                    return this;
                }
                if ((firstChild) == null) {
                    return null;
                }
                return getRootFragmentOfElement(firstChild.getSourceFragmentOf(start, end));
            }
            throw new spoon.SpoonException("Invalid start/end interval. It overlaps multiple fragments.");
        }
        throw new spoon.SpoonException("Invalid start/end interval. It is before this fragment");
    }

    private spoon.reflect.visitor.printer.change.SourceFragment getRootFragmentOfElement(spoon.reflect.visitor.printer.change.SourceFragment childFragment) {
        if (((childFragment != null) && ((getElement()) != null)) && ((childFragment.getElement()) == (getElement()))) {
            return this;
        }
        return childFragment;
    }

    public spoon.reflect.declaration.CtElement getElement() {
        return element;
    }

    public spoon.reflect.visitor.printer.change.SourceFragment getChildFragmentOfSameElement() {
        if (((getFirstChild()) != null) && ((getFirstChild().getElement()) == (element))) {
            return getFirstChild();
        }
        return null;
    }

    public spoon.reflect.visitor.printer.change.SourceFragment getNextFragmentOfSameElement() {
        if (((getNextSibling()) != null) && ((getNextSibling().getElement()) == (element))) {
            return getNextSibling();
        }
        return null;
    }

    public java.lang.String getTextBeforeFirstChild() {
        if ((firstChild) != null) {
            return getSourceCode(getStart(), firstChild.getStart());
        }
        return null;
    }

    public java.lang.String getTextAfterLastChild() {
        spoon.reflect.visitor.printer.change.SourceFragment lastChild = firstChild;
        while (lastChild != null) {
            if ((lastChild.getNextSibling()) == null) {
                return getSourceCode(lastChild.getEnd(), getEnd());
            }
            lastChild = lastChild.getNextSibling();
        } 
        return null;
    }
}

