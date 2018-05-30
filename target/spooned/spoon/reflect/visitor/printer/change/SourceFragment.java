/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.reflect.visitor.printer.change;


/**
 * Represents a part of source code of an {@link CtElement}
 * It is connected into a tree of {@link SourceFragment}s.
 * That tree can be build by {@link CompilationUnit#getRootSourceFragment()}
 * And the tree of {@link SourceFragment}s related to one element can be returned by {@link CompilationUnit#getSourceFragment(CtElement)}
 */
public class SourceFragment {
    private final spoon.reflect.declaration.CtElement element;

    private final spoon.reflect.cu.SourcePosition sourcePosition;

    private final spoon.reflect.visitor.printer.change.FragmentType fragmentType;

    private boolean modified = false;

    spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptor fragmentDescriptor;

    private spoon.reflect.visitor.printer.change.SourceFragment nextSibling;

    private spoon.reflect.visitor.printer.change.SourceFragment firstChild;

    /**
     * Creates a main fragment of {@link CtElement}
     * Note: it automatically creates child fragments if `sourcePosition`
     * is instance of {@link DeclarationSourcePosition} or {@link BodyHolderSourcePosition}
     *
     * @param element
     * 		target {@link CtElement}
     */
    public SourceFragment(spoon.reflect.declaration.CtElement element) {
        this(element, element.getPosition(), spoon.reflect.visitor.printer.change.FragmentType.MAIN_FRAGMENT);
    }

    /**
     * creates a main fragment for {@link SourcePosition}
     * Note: it automatically creates child fragments if `sourcePosition`
     * is instance of {@link DeclarationSourcePosition} or {@link BodyHolderSourcePosition}
     *
     * @param sourcePosition
     * 		target {@link SourcePosition}
     */
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

    /**
     *
     *
     * @return type of fragment
     */
    public spoon.reflect.visitor.printer.change.FragmentType getFragmentType() {
        return fragmentType;
    }

    /**
     *
     *
     * @return offset of first character which belongs to this fragment
     */
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

    /**
     *
     *
     * @return offset of character after this fragment
     */
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

    /**
     *
     *
     * @return {@link SourcePosition} of this fragment
     */
    public spoon.reflect.cu.SourcePosition getSourcePosition() {
        return sourcePosition;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return ((((("|" + (getStart())) + ", ") + (getEnd())) + "|") + (getSourceCode())) + "|";
    }

    /**
     *
     *
     * @return origin source code of this fragment
     */
    public java.lang.String getSourceCode() {
        return getSourceCode(getStart(), getEnd());
    }

    /**
     *
     *
     * @param start
     * 		start offset relative to compilation unit
     * @param end
     * 		end offset (after last character) relative to compilation unit
     * @return source code of this Fragment between start/end offsets
     */
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

    /**
     *
     *
     * @return true if the attribute of {@link CtElement} whose source code is in this fragment is modified
     */
    public boolean isModified() {
        return modified;
    }

    /**
     *
     *
     * @param modified
     * 		true if the attribute of {@link CtElement} whose source code is in this fragment is modified
     */
    public void setModified(boolean modified) {
        this.modified = modified;
    }

    /**
     *
     *
     * @return true if position points to same compilation unit (source file) as this SourceFragment
     */
    private boolean isFromSameSource(spoon.reflect.cu.SourcePosition position) {
        return sourcePosition.getCompilationUnit().equals(position.getCompilationUnit());
    }

    /**
     * Builds tree of {@link SourcePosition}s of `element` and all it's children
     *
     * @param element
     * 		the root element of the tree
     */
    public void addTreeOfSourceFragmentsOfElement(spoon.reflect.declaration.CtElement element) {
        spoon.reflect.cu.SourcePosition sp = element.getPosition();
        java.util.Deque<spoon.reflect.visitor.printer.change.SourceFragment> parents = new java.util.ArrayDeque<>();
        parents.push(this);
        // scan all children of `element` and build tree of SourceFragments
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

    /**
     *
     *
     * @param parentFragment
     * 		the parent {@link SourceFragment}, which will receive {@link SourceFragment} made for `otherElement`
     * @param otherElement
     * 		{@link CtElement} whose {@link SourceFragment} has to be added to `parentFragment`
     * @return 
     */
    private spoon.reflect.visitor.printer.change.SourceFragment addChild(spoon.reflect.visitor.printer.change.SourceFragment parentFragment, spoon.reflect.declaration.CtElement otherElement) {
        spoon.reflect.cu.SourcePosition otherSourcePosition = otherElement.getPosition();
        if ((otherSourcePosition instanceof spoon.support.reflect.cu.position.SourcePositionImpl) && ((otherSourcePosition.getCompilationUnit()) != null)) {
            spoon.support.reflect.cu.position.SourcePositionImpl childSPI = ((spoon.support.reflect.cu.position.SourcePositionImpl) (otherSourcePosition));
            if ((parentFragment.sourcePosition) != childSPI) {
                if (parentFragment.isFromSameSource(otherSourcePosition)) {
                    spoon.reflect.visitor.printer.change.SourceFragment otherFragment = new spoon.reflect.visitor.printer.change.SourceFragment(otherElement);
                    // parent and child are from the same file. So we can connect their positions into one tree
                    spoon.reflect.visitor.printer.change.SourceFragment.CMP cmp = parentFragment.compare(otherFragment);
                    if (cmp == (spoon.reflect.visitor.printer.change.SourceFragment.CMP.OTHER_IS_CHILD)) {
                        // child belongs under parent - OK
                        parentFragment.addChild(otherFragment);
                        return otherFragment;
                    }else {
                        if ((cmp == (spoon.reflect.visitor.printer.change.SourceFragment.CMP.OTHER_IS_AFTER)) || (cmp == (spoon.reflect.visitor.printer.change.SourceFragment.CMP.OTHER_IS_BEFORE))) {
                            if (otherElement instanceof spoon.reflect.code.CtComment) {
                                /* comments of elements are sometime not included in source position of element.
                                because comments are ignored tokens for java compiler, which computes start/end of elements
                                Example:

                                		//a comment
                                		aStatement();

                                No problem. Simply add comment at correct position into SourceFragment tree, starting from root
                                 */
                                addChild(otherFragment);
                                return otherFragment;
                            }
                        }
                        // the source position of child element is not included in source position of parent element
                        // I (Pavel) am not sure how to handle it, so let's wait until it happens...
                        // if (otherElement instanceof CtAnnotation<?>) {
                        // /*
                        // * it can happen for annotations of type TYPE_USE and FIELD
                        // * In such case the annotation belongs to 2 elements
                        // * And one of them cannot have matching source position - OK
                        // */
                        // return null;
                        // }
                        // something is wrong ...
                        throw new spoon.SpoonException(((("The SourcePosition of elements are not consistent\nparentFragment: " + parentFragment) + "\notherFragment: ") + otherFragment));
                    }
                }else {
                    throw new spoon.SpoonException(((("SourcePosition from unexpected compilation unit: " + otherSourcePosition) + " expected is: ") + (parentFragment.sourcePosition)));
                }
            }
            // else these two elements has same instance of SourcePosition.
            // It is probably OK. Do not created new SourceFragment for that
            return null;
        }
        // do not connect that undefined source position
        return null;
    }

    /**
     * adds `other` {@link SourceFragment} into tree of {@link SourceFragment}s represented by this root element
     *
     * @param other
     * 		to be added {@link SourceFragment}
     * @return new root of the tree of the {@link SourceFragment}s. It can be be this or `other`
     */
    public spoon.reflect.visitor.printer.change.SourceFragment add(spoon.reflect.visitor.printer.change.SourceFragment other) {
        if ((this) == other) {
            throw new spoon.SpoonException("SourceFragment#add must not be called twice for the same SourceFragment");
            // optionally we might accept that and simply return this
        }
        spoon.reflect.visitor.printer.change.SourceFragment.CMP cmp = this.compare(other);
        switch (cmp) {
            case OTHER_IS_AFTER :
                // other is after this
                addNextSibling(other);
                return this;
            case OTHER_IS_BEFORE :
                // other is before this
                other.addNextSibling(this);
                return other;
            case OTHER_IS_CHILD :
                // other is child of this
                addChild(other);
                return this;
            case OTHER_IS_PARENT :
                // other is parent of this
                other.addChild(this);
                // merge siblings of `this` as children and siblings of `other`
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
            // the next sibling of child is after `this`
            // disconnect it from prevOther and connect it as sibling of this
            // and we are done, because other.nextSibling is already OK
            if (cmp == (spoon.reflect.visitor.printer.change.SourceFragment.CMP.OTHER_IS_CHILD)) {
                // ok, it is child too. Keep it as sibling of children
                prevOther = other;
                other = other.getNextSibling();
                continue;
            }// the next sibling of child is after `this`
            // disconnect it from prevOther and connect it as sibling of this
            // and we are done, because other.nextSibling is already OK
            else
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

    /**
     * compares this and other
     *
     * @param other
     * 		other {@link SourcePosition}
     * @return CMP
     * throws {@link SpoonException} if intervals overlap or start/end is negative
     */
    private spoon.reflect.visitor.printer.change.SourceFragment.CMP compare(spoon.reflect.visitor.printer.change.SourceFragment other) {
        if (other == (this)) {
            throw new spoon.SpoonException("SourcePositionImpl#addNextSibling must not be called twice for the same SourcePosition");
        }
        if ((getEnd()) <= (other.getStart())) {
            // other is after this
            return spoon.reflect.visitor.printer.change.SourceFragment.CMP.OTHER_IS_AFTER;
        }
        if ((other.getEnd()) <= (getStart())) {
            // other is before this
            return spoon.reflect.visitor.printer.change.SourceFragment.CMP.OTHER_IS_BEFORE;
        }
        if (((getStart()) <= (other.getStart())) && ((getEnd()) >= (other.getEnd()))) {
            // other is child of this
            return spoon.reflect.visitor.printer.change.SourceFragment.CMP.OTHER_IS_CHILD;
        }
        if (((getStart()) >= (other.getStart())) && ((getEnd()) <= (other.getEnd()))) {
            // other is parent of this
            return spoon.reflect.visitor.printer.change.SourceFragment.CMP.OTHER_IS_PARENT;
        }
        // the fragments overlap - it is not allowed
        throw new spoon.SpoonException((((((((("Cannot compare this: [" + (getStart())) + ", ") + (getEnd())) + "] with other: [\"") + (other.getStart())) + "\", \"") + (other.getEnd())) + "\"]"));
    }

    /**
     * creates child fragments for {@link DeclarationSourcePosition} and {@link BodyHolderSourcePosition}
     */
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

    /**
     *
     *
     * @return {@link SourceFragment} which belongs to the same parent and is next in the sources
     */
    public spoon.reflect.visitor.printer.change.SourceFragment getNextSibling() {
        return nextSibling;
    }

    /**
     *
     *
     * @return {@link SourceFragment}, which is first child of this fragment
     */
    public spoon.reflect.visitor.printer.change.SourceFragment getFirstChild() {
        return firstChild;
    }

    /**
     * Searches the tree of {@link SourceFragment}s
     * It searches in siblings and children of this {@link SourceFragment} recursively.
     *
     * @param start
     * 		the start offset of this fragment
     * @param end
     * 		the offset of next character after the end of this fragment
     * @return {@link SourceFragment} which represents the root of the CtElement whose sources are in interval [start, end]
     */
    public spoon.reflect.visitor.printer.change.SourceFragment getSourceFragmentOf(int start, int end) {
        int myEnd = getEnd();
        if (myEnd <= start) {
            // search in next sibling
            if ((nextSibling) == null) {
                return null;
            }
            return getRootFragmentOfElement(nextSibling.getSourceFragmentOf(start, end));
        }
        int myStart = getStart();
        if (myStart <= start) {
            if (myEnd >= end) {
                if ((myStart == start) && (myEnd == end)) {
                    // we have found exact match
                    return this;
                }
                // it is the child
                if ((firstChild) == null) {
                    return null;
                }
                return getRootFragmentOfElement(firstChild.getSourceFragmentOf(start, end));
            }
            // start - end overlaps over multiple fragments
            throw new spoon.SpoonException("Invalid start/end interval. It overlaps multiple fragments.");
        }
        throw new spoon.SpoonException("Invalid start/end interval. It is before this fragment");
    }

    private spoon.reflect.visitor.printer.change.SourceFragment getRootFragmentOfElement(spoon.reflect.visitor.printer.change.SourceFragment childFragment) {
        if (((childFragment != null) && ((getElement()) != null)) && ((childFragment.getElement()) == (getElement()))) {
            // child fragment and this fragment have same element. Return this fragment,
            // because we have to return root fragment of CtElement
            return this;
        }
        return childFragment;
    }

    /**
     *
     *
     * @return {@link CtElement} whose source code is contained in this fragment.
     * May be null
     */
    public spoon.reflect.declaration.CtElement getElement() {
        return element;
    }

    /**
     *
     *
     * @return direct child {@link SourceFragment} if it has same element like this.
     * It means that this {@link SourceFragment} knows the source parts of this element.
     * E.g. {@link SourceFragment} of {@link CtClass} has fragments for modifiers, name, body, etc.
     * So in this case it returns first child fragment, which are modifiers fragment of {@link CtClass}.
     * Else it returns null.
     */
    public spoon.reflect.visitor.printer.change.SourceFragment getChildFragmentOfSameElement() {
        if (((getFirstChild()) != null) && ((getFirstChild().getElement()) == (element))) {
            return getFirstChild();
        }
        return null;
    }

    /**
     *
     *
     * @return direct next sibling {@link SourceFragment} if it has same element like this.
     * It means that this {@link SourceFragment} knows the next source part of this element
     * Else it returns null.
     */
    public spoon.reflect.visitor.printer.change.SourceFragment getNextFragmentOfSameElement() {
        if (((getNextSibling()) != null) && ((getNextSibling().getElement()) == (element))) {
            return getNextSibling();
        }
        return null;
    }

    /**
     *
     *
     * @return source code of this fragment before the first child of this fragment.
     * null if there is no child
     */
    public java.lang.String getTextBeforeFirstChild() {
        if ((firstChild) != null) {
            return getSourceCode(getStart(), firstChild.getStart());
        }
        return null;
    }

    /**
     *
     *
     * @return source code of this fragment after the last child of this fragment
     * null if there is no child
     */
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

