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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import spoon.SpoonException;
import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.cu.CompilationUnit;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.cu.SourcePositionHolder;
import spoon.reflect.cu.position.BodyHolderSourcePosition;
import spoon.reflect.cu.position.DeclarationSourcePosition;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtModifiable;
import spoon.reflect.meta.ContainerKind;
import spoon.reflect.meta.RoleHandler;
import spoon.reflect.meta.impl.RoleHandlerHelper;
import spoon.reflect.path.CtRole;
import spoon.reflect.visitor.CtScanner;
import spoon.support.reflect.CtExtendedModifier;
import spoon.support.reflect.cu.position.SourcePositionImpl;

/**
 * Represents a part of source code of an {@link CtElement}
 * It is connected into a tree of {@link SourceFragment}s.
 * That tree can be build by {@link CompilationUnit#getRootSourceFragment()}
 * And the tree of {@link SourceFragment}s related to one element can be returned by {@link CompilationUnit#getSourceFragment(SourcePositionHolder)}
 */
public class SourceFragment implements ChildSourceFragment {

	private final SourcePositionHolder element;
	private final RoleHandler roleHandlerInParent;
	private final SourcePosition sourcePosition;
	private SourceFragment nextSibling;
	private SourceFragment firstChild;

	/**
	 * Creates a main fragment of {@link CtElement}
	 * Note: it automatically creates child fragments if `sourcePosition`
	 * is instance of {@link DeclarationSourcePosition} or {@link BodyHolderSourcePosition}
	 *
	 * @param element target {@link CtElement}
	 */
	public SourceFragment(SourcePositionHolder element, RoleHandler roleHandlerInParent) {
		this(element, element.getPosition(), roleHandlerInParent);
	}
	/**
	 * creates a main fragment for {@link SourcePosition}
	 * Note: it automatically creates child fragments if `sourcePosition`
	 * is instance of {@link DeclarationSourcePosition} or {@link BodyHolderSourcePosition}
	 *
	 * @param sourcePosition target {@link SourcePosition}
	 */
	public SourceFragment(SourcePosition sourcePosition) {
		this(null, sourcePosition, null);
	}

	private SourceFragment(SourcePositionHolder element, SourcePosition sourcePosition, RoleHandler roleHandlerInParent) {
		super();
		this.element = element;
		this.roleHandlerInParent = roleHandlerInParent;
		this.sourcePosition = sourcePosition;
	}

	/**
	 * @return offset of first character which belongs to this fragment
	 */
	public int getStart() {
		if (firstChild != null) {
			return Math.min(sourcePosition.getSourceStart(), firstChild.getStart());
		}
		return sourcePosition.getSourceStart();
	}

	/**
	 * @return offset of character after this fragment
	 */
	public int getEnd() {
		if (firstChild != null) {
			return Math.max(sourcePosition.getSourceEnd() + 1, firstChild.getLastSibling().getEnd());
		}
		return sourcePosition.getSourceEnd() + 1;
	}

	/**
	 * @return {@link SourcePosition} of this fragment
	 */
	public SourcePosition getSourcePosition() {
		return sourcePosition;
	}

	@Override
	public String toString() {
		return "|" + getStart() + ", " + getEnd() + "|" + getSourceCode() + "|";
	}

	/**
	 * @return origin source code of this fragment
	 */
	@Override
	public String getSourceCode() {
		return getSourceCode(getStart(), getEnd());
	}

	/**
	 * @param start start offset relative to compilation unit
	 * @param end end offset (after last character) relative to compilation unit
	 * @return source code of this Fragment between start/end offsets
	 */
	public String getSourceCode(int start, int end) {
		String src = getOriginalSourceCode();
		if (src != null) {
			return src.substring(start, end);
		}
		return null;
	}

	/**
	 * @return true if position points to same compilation unit (source file) as this SourceFragment
	 */
	private boolean isFromSameSource(SourcePosition position) {
		return sourcePosition.getCompilationUnit().equals(position.getCompilationUnit());
	}

	/**
	 * Builds tree of {@link SourcePosition}s of `element` and all it's children
	 * @param element the root element of the tree
	 */
	public void addTreeOfSourceFragmentsOfElement(CtElement element) {
		SourcePosition sp = element.getPosition();
		Deque<SourceFragment> parents = new ArrayDeque<>();
		parents.push(this);
		//scan all children of `element` and build tree of SourceFragments
		new CtScanner() {
			CtRole scannedRole;
			public void scan(CtRole role, CtElement element) {
				scannedRole = role;
				super.scan(role, element);
			}
			@Override
			protected void enter(CtElement e) {
				SourceFragment newFragment = addChild(parents.peek(), scannedRole, e);
				if (newFragment != null) {
					parents.push(newFragment);
					if (e instanceof CtModifiable) {
						CtModifiable modifiable = (CtModifiable) e;
						Set<CtExtendedModifier> modifiers = modifiable.getExtendedModifiers();
						for (CtExtendedModifier ctExtendedModifier : modifiers) {
							addChild(newFragment, CtRole.MODIFIER, ctExtendedModifier);
						}
					}
				}
			}
			@Override
			protected void exit(CtElement e) {
				SourceFragment topFragment = parents.peek();
				if (topFragment != null && topFragment.getElement() == e) {
					parents.pop();
				}
			}
		}.scan(element.getRoleInParent(), element);
	}
	/**
	 * @param parentFragment the parent {@link SourceFragment}, which will receive {@link SourceFragment} made for `otherElement`
	 * @param otherElement {@link CtElement} whose {@link SourceFragment} has to be added to `parentFragment`
	 * @return
	 */
	private SourceFragment addChild(SourceFragment parentFragment, CtRole roleInParent, SourcePositionHolder otherElement) {
		SourcePosition otherSourcePosition = otherElement.getPosition();
		if (otherSourcePosition instanceof SourcePositionImpl && otherSourcePosition.getCompilationUnit() != null) {
			SourcePositionImpl childSPI = (SourcePositionImpl) otherSourcePosition;
			if (parentFragment.isFromSameSource(otherSourcePosition)) {
				SourceFragment otherFragment = new SourceFragment(otherElement, parentFragment.getRoleHandler(roleInParent, otherElement));
				//parent and child are from the same file. So we can connect their positions into one tree
				CMP cmp = parentFragment.compare(otherFragment);
				if (cmp == CMP.OTHER_IS_CHILD) {
					//child belongs under parent - OK
					parentFragment.addChild(otherFragment);
					return otherFragment;
				} else {
					if (cmp == CMP.OTHER_IS_AFTER || cmp == CMP.OTHER_IS_BEFORE) {
						if (otherElement instanceof CtComment) {
							/*
							 * comments of elements are sometime not included in source position of element.
							 * because comments are ignored tokens for java compiler, which computes start/end of elements
							 * Example:
							 *
							 * 		//a comment
							 * 		aStatement();
							 *
							 * No problem. Simply add comment at correct position into SourceFragment tree, starting from root
							 */
							addChild(otherFragment);
							return otherFragment;
						}
					}
					//the source position of child element is not included in source position of parent element
					//I (Pavel) am not sure how to handle it, so let's wait until it happens...
//						if (otherElement instanceof CtAnnotation<?>) {
//							/*
//							 * it can happen for annotations of type TYPE_USE and FIELD
//							 * In such case the annotation belongs to 2 elements
//							 * And one of them cannot have matching source position - OK
//							 */
//							return null;
//						}
					//something is wrong ...
					throw new SpoonException("The SourcePosition of elements are not consistent\nparentFragment: " + parentFragment + "\notherFragment: " + otherFragment);
				}
			} else {
				throw new SpoonException("SourcePosition from unexpected compilation unit: " + otherSourcePosition + " expected is: " + parentFragment.sourcePosition);
			}
		}
		//do not connect that undefined source position
		return null;
	}

	private RoleHandler getRoleHandler(CtRole roleInParent, SourcePositionHolder otherElement) {
		SourcePositionHolder parent = element;
		if (parent == null) {
			if (otherElement instanceof CtElement) {
				parent = ((CtElement) otherElement).getParent();
			}
		}
		if (parent instanceof CtElement) {
			CtElement ele = (CtElement) parent;
			return RoleHandlerHelper.getRoleHandler(ele.getClass(), roleInParent);
		}
		return null;
	}
	/**
	 * adds `other` {@link SourceFragment} into tree of {@link SourceFragment}s represented by this root element
	 *
	 * @param other to be added {@link SourceFragment}
	 * @return new root of the tree of the {@link SourceFragment}s. It can be be this or `other`
	 */
	public SourceFragment add(SourceFragment other) {
		if (this == other) {
			throw new SpoonException("SourceFragment#add must not be called twice for the same SourceFragment");
			//optionally we might accept that and simply return this
		}
		CMP cmp = this.compare(other);
		switch (cmp) {
		case OTHER_IS_AFTER:
			//other is after this
			addNextSibling(other);
			return this;
		case OTHER_IS_BEFORE:
			//other is before this
			other.addNextSibling(this);
			return other;
		case OTHER_IS_CHILD:
			//other is child of this
			addChild(other);
			return this;
		case OTHER_IS_PARENT:
			//other is parent of this, merge this and all siblings of `this` as children and siblings of `other`
			other.merge(this);
			return other;
		}
		throw new SpoonException("Unexpected compare result: " + cmp);
	}

	private void merge(SourceFragment tobeMerged) {
		while (tobeMerged != null) {
			SourceFragment nextTobeMerged = tobeMerged.getNextSibling();
			//disconnect tobeMerged from nextSiblings before we add it. So it is added individually and not with wrong siblings too
			tobeMerged.nextSibling = null;
			add(tobeMerged);
			tobeMerged = nextTobeMerged;
		}
	}

	/**
	 * adds `fragment` as child fragment of this fragment. If child is located before or after this fragment,
	 * then start/end of this fragment is moved
	 * @param fragment to be add
	 */
	public void addChild(SourceFragment fragment) {
		if (firstChild == null) {
			firstChild = fragment;
		} else {
			firstChild = firstChild.add(fragment);
		}
	}

	private void addNextSibling(SourceFragment sibling) {
		if (nextSibling == null) {
			nextSibling = sibling;
		} else {
			nextSibling = nextSibling.add(sibling);
		}
	}

	private SourceFragment getLastSibling() {
		SourceFragment lastSibling = this;
		while (lastSibling.nextSibling != null) {
			lastSibling = lastSibling.nextSibling;
		}
		return lastSibling;
	}

	private enum CMP {
		OTHER_IS_BEFORE,
		OTHER_IS_AFTER,
		OTHER_IS_CHILD,
		OTHER_IS_PARENT
	}

	/**
	 * compares this and other
	 * @param other other {@link SourcePosition}
	 * @return CMP
	 * throws {@link SpoonException} if intervals overlap or start/end is negative
	 */
	private CMP compare(SourceFragment other) {
		if (other == this) {
			throw new SpoonException("SourcePositionImpl#addNextSibling must not be called twice for the same SourcePosition");
		}
		if (getEnd() <= other.getStart()) {
			//other is after this
			return CMP.OTHER_IS_AFTER;
		}
		if (other.getEnd() <= getStart()) {
			//other is before this
			return CMP.OTHER_IS_BEFORE;
		}
		if (getStart() <= other.getStart() && getEnd() >= other.getEnd()) {
			//other is child of this
			return CMP.OTHER_IS_CHILD;
		}
		if (getStart() >= other.getStart() && getEnd() <= other.getEnd()) {
			//other is parent of this
			return CMP.OTHER_IS_PARENT;
		}
		//the fragments overlap - it is not allowed
		throw new SpoonException("Cannot compare this: [" + getStart() + ", " + getEnd() + "] with other: [\"" + other.getStart() + "\", \"" + other.getEnd() + "\"]");
	}
/*
	public int getNameStart() {
		if (sourcePosition instanceof DeclarationSourcePosition) {
			DeclarationSourcePosition dsp = (DeclarationSourcePosition) sourcePosition;
			return dsp.getNameStart();
		}
		return -1;
	}

	public int getNameEnd() {
		if (sourcePosition instanceof DeclarationSourcePosition) {
			DeclarationSourcePosition dsp = (DeclarationSourcePosition) sourcePosition;
			return dsp.getNameEnd() + 1;
		}
		return -1;
	}
*/
	/**
	 * @return {@link SourceFragment} which belongs to the same parent and is next in the sources
	 */
	public SourceFragment getNextSibling() {
		return nextSibling;
	}

	/**
	 * @return {@link SourceFragment}, which is first child of this fragment
	 */
	public SourceFragment getFirstChild() {
		return firstChild;
	}

	/**
	 * Searches the tree of {@link SourceFragment}s
	 * It searches in siblings and children of this {@link SourceFragment} recursively.
	 * @param element the element of fragment it is looking for or null for any element
	 * @param start the start offset of this fragment
	 * @param end the offset of next character after the end of this fragment
	 *
	 * @return {@link SourceFragment} which represents the root of the CtElement whose sources are in interval [start, end]
	 */
	public SourceFragment getSourceFragmentOf(SourcePositionHolder element, int start, int end) {
		int myEnd = getEnd();
		if (myEnd <= start) {
			//search in next sibling
			if (nextSibling == null) {
				return null;
			}
			return getRootFragmentOfElement(nextSibling.getSourceFragmentOf(element, start, end));
		}
		int myStart = getStart();
		if (myStart <= start) {
			if (myEnd >= end) {
				if (myStart == start && myEnd == end) {
					//we have found exact match
					if (element != null && getElement() != element) {
						if (firstChild == null) {
							throw new SpoonException("There is no source fragment for element " + element.getClass() + ". There is one for class " + getElement().getClass());
						}
						return firstChild.getSourceFragmentOf(element, start, end);
					}
					return this;
				}
				//it is the child
				if (firstChild == null) {
					if (element != null && getElement() != element) {
						throw new SpoonException("There is no source fragment for element " + element.getClass() + ". There is one for class " + getElement().getClass());
					}
					return this;
				}
				SourceFragment child = getRootFragmentOfElement(firstChild.getSourceFragmentOf(element, start, end));
				if (child != null) {
					//all children are smaller then this element
					return child;
				}
				//so this fragment is last one which wraps whole element
				if (element != null && getElement() != element) {
					throw new SpoonException("There is no source fragment for element " + element.getClass() + ". There is one for class " + getElement().getClass());
				}
				return this;
			}
			//start - end overlaps over multiple fragments
			throw new SpoonException("Invalid start/end interval. It overlaps multiple fragments.");
		}
		return null;
	}

	private SourceFragment getRootFragmentOfElement(SourceFragment childFragment) {
		if (childFragment != null && getElement() != null && childFragment.getElement() == getElement()) {
			//child fragment and this fragment have same element. Return this fragment,
			//because we have to return root fragment of CtElement
			return this;
		}
		return childFragment;
	}
	/**
	 * @return {@link CtElement} whose source code is contained in this fragment.
	 * May be null
	 */
	public SourcePositionHolder getElement() {
		return element;
	}

	@Override
	public SourceFragment getSourceFragmentOfElement(SourcePositionHolder element) {
		if (this.element == element) {
			return this;
		}
		return null;
	}

	/**
	 * @return direct child {@link SourceFragment} if it has same element like this.
	 * It means that this {@link SourceFragment} knows the source parts of this element.
	 * E.g. {@link SourceFragment} of {@link CtClass} has fragments for modifiers, name, body, etc.
	 * So in this case it returns first child fragment, which are modifiers fragment of {@link CtClass}.
	 * Else it returns null.
	 */
	public SourceFragment getChildFragmentOfSameElement() {
		if (getFirstChild() != null && getFirstChild().getElement() == element) {
			return getFirstChild();
		}
		return null;
	}

	/**
	 * @return direct next sibling {@link SourceFragment} if it has same element like this.
	 * It means that this {@link SourceFragment} knows the next source part of this element
	 * Else it returns null.
	 */
	public SourceFragment getNextFragmentOfSameElement() {
		if (getNextSibling() != null && getNextSibling().getElement() == element) {
			return getNextSibling();
		}
		return null;
	}
	/**
	 * @return source code of this fragment before the first child of this fragment.
	 * null if there is no child
	 */
	public String getTextBeforeFirstChild() {
		if (firstChild != null) {
			return getSourceCode(getStart(), firstChild.getStart());
		}
		return null;
	}
	/**
	 * @return source code of this fragment after the last child of this fragment
	 * null if there is no child
	 */
	public String getTextAfterLastChild() {
		SourceFragment lastChild = firstChild;
		while (lastChild != null) {
			if (lastChild.getNextSibling() == null) {
				return getSourceCode(lastChild.getEnd(), getEnd());
			}
			lastChild = lastChild.getNextSibling();
		}
		return null;
	}
	/**
	 * Detects all child fragments of current SourceFragment.
	 * Note: the List of children is flat. The child fragments of collections (parameters, type members, ...) are next to each other.
	 */
	public List<ChildSourceFragment> getChildrenFragments() {
		if (element instanceof CtLiteral) {
			return Collections.singletonList(new ConstantSourceFragment(getSourceCode(), TokenType.LITERAL));
		}
		List<ChildSourceFragment> children = new ArrayList<>();
		int off = getStart();
		SourceFragment child = getFirstChild();
		while (child != null) {
			forEachConstantFragment(off, child.getStart(), cf -> children.add(cf));
			children.add(child);
			off = child.getEnd();
			child = child.getNextSibling();
		}
		forEachConstantFragment(off, getEnd(), cf -> children.add(cf));
		return children;
	}

	/**
	 * Detects all child fragments of current SourceFragment.
	 * Note: the List of children contains one {@link CollectionSourceFragment} for each collection of fragments (parameters, type members, ...).
	 * Note: the {@link CollectionSourceFragment} may contain a mix of fragments of different roles, when they overlap.
	 * For example this code contains mix of annotations and modifiers
	 * <code>public @Deprecated static @Ignored void method()</code>
	 * @return
	 */
	public List<ChildSourceFragment> getGroupedChildrenFragments() {
		List<ChildSourceFragment> flatChildren = getChildrenFragments();
		List<ChildSourceFragment> result = new ArrayList<>();
		int i = 0;
		while (i < flatChildren.size()) {
			ChildSourceFragment child = flatChildren.get(i);
			ContainerKind kind = getContainerKind(child);
			if (kind == null || kind == ContainerKind.SINGLE) {
				//it is root element or there is always only one child instance in parent
				result.add(child);
				i++;
				continue;
			}
			//there can be 0, 1 or more items of children of the same role
			//search for another element of the same role
			Set<RoleHandler> foundRoles = new HashSet<>();
			foundRoles.add(checkNotNull(child.getRoleHandlerInParent()));
			List<ChildSourceFragment> childrenInSameCollection = new ArrayList<>();
			//but first include prefix whitespace
			ChildSourceFragment spaceChild = removeLastChildIfType(result, TokenType.SPACE);
			if (spaceChild != null) {
				childrenInSameCollection.add(spaceChild);
			}
			childrenInSameCollection.add(child);
			int lastOfSameRole = findIndexOfLastChildTokenOfRoleHandler(flatChildren, i, child.getRoleHandlerInParent());
			//search for other roles in that interval
			i++;
			while (i <= lastOfSameRole) {
				child = flatChildren.get(i);
				childrenInSameCollection.add(child);
				RoleHandler role = child.getRoleHandlerInParent();
				if (role != null && role.getRole() != CtRole.COMMENT && foundRoles.add(role)) {
					//there is another role in same block, search for last one
					lastOfSameRole = Math.max(lastOfSameRole, findIndexOfLastChildTokenOfRoleHandler(flatChildren, i + 1, role));
				}
				i++;
			}
			//add suffix space
			if (i < flatChildren.size()) {
				ChildSourceFragment nextChild = flatChildren.get(i);
				if (nextChild.getType() == TokenType.SPACE) {
					childrenInSameCollection.add(nextChild);
					i++;
				}
			}
			result.add(new CollectionSourceFragment(getElement(), childrenInSameCollection, foundRoles));
		}
		return result;
	}

	private ChildSourceFragment removeLastChildIfType(List<ChildSourceFragment> list, TokenType type) {
		if (list.size() > 0) {
			ChildSourceFragment lastChild = list.get(list.size() - 1);
			if (lastChild.getType() == type) {
				list.remove(list.size() - 1);
				return lastChild;
			}
		}
		return null;
	}

	private <T> T checkNotNull(T o) {
		if (o == null) {
			throw new SpoonException("Unexpected null value");
		}
		return o;
	}
	private static ContainerKind getContainerKind(ChildSourceFragment csf) {
		RoleHandler rh = csf.getRoleHandlerInParent();
		if (rh != null) {
			if (rh.getRole() == CtRole.COMMENT) {
				//comments cannot be handled as normal collection of items. So handle them as single items
				return ContainerKind.SINGLE;
			}
			return rh.getContainerKind();
		}
		return null;
	}

	static CtRole getCtRole(ChildSourceFragment csf) {
		RoleHandler rh = csf.getRoleHandlerInParent();
		if (rh != null) {
			return rh.getRole();
		}
		return null;
	}

	/**
	 * looks next child token which has role `role`
	 * @param start - the index of first element, which is checked
	 * @return index of same token or -1 if not found
	 */
	static int findIndexOfNextChildToken(List<ChildSourceFragment> childFragments, int start, Predicate<ChildSourceFragment> test) {
		while (start < childFragments.size()) {
			if (test.test(childFragments.get(start))) {
				return start;
			}
			start++;
		}
		return -1;
	}
	/**
	 * @param start the index of element with lower index which is checked and may be returned
	 * @param role
	 * @return
	 */
	static int findIndexOfLastChildToken(List<ChildSourceFragment> childFragments, int start, Predicate<ChildSourceFragment> test) {
		int i = childFragments.size() - 1;
		while (i >= start) {
			if (test.test(childFragments.get(i))) {
				return i;
			}
			i--;
		}
		return -1;
	}

	private static int findIndexOfLastChildTokenOfRoleHandler(List<ChildSourceFragment> childFragments, int start, RoleHandler role) {
		return findIndexOfLastChildToken(childFragments, start, fragment -> fragment.hasRole(role.getRole()));
	}

	private enum CharType {
		SPACE,
		NON_SPACE;

		static CharType fromChar(char c) {
			return Character.isWhitespace(c) ? SPACE : NON_SPACE;
		}
	}

	private static final Set<String> separators = new HashSet<>(Arrays.asList("->", "::", "..."));
	static {
		"(){}[];,.:@=<>?&|".chars().forEach(c -> separators.add(new String(Character.toChars(c))));
	}
	private static final Set<String> operators = new HashSet<>(Arrays.asList(
			"=",
			">",
			"<",
			"!",
			"~",
			"?",
			":",
			"==",
			"<=",
			">=",
			"!=",
			"&&",
			"||",
			"++",
			"--",
			"+",
			"-",
			"*",
			"/",
			"&",
			"|",
			"^",
			"%",
			"<<", ">>", ">>>",

			"+=",
			"-=",
			"*=",
			"/=",
			"&=",
			"|=",
			"^=",
			"%=",
			"<<=",
			">>=",
			">>>="/*,
			it is handled as keyword here
			"instanceof"
			*/
	));

	private static final String[] javaKeywordsJoined = new String[] {
			"abstract continue for new switch",
			"assert default goto package synchronized",
			"boolean do if private this",
			"break double implements protected throw",
			"byte else import public throws",
			"case enum instanceof return transient",
			"catch extends int short try",
			"char final interface static void",
			"class finally long strictfp volatile",
			"const float native super while"};

	private static final Set<String> javaKeywords = new HashSet<>();
	static {
		for (String str : javaKeywordsJoined) {
			StringTokenizer st = new StringTokenizer(str, " ");
			while (st.hasMoreTokens()) {
				javaKeywords.add(st.nextToken());
			}
		}
	}

	private static final List<StringMatcher> matchers = new ArrayList<>();
	static {
		separators.forEach(s -> matchers.add(new StringMatcher(s, TokenType.SEPARATOR)));
		operators.forEach(s -> matchers.add(new StringMatcher(s, TokenType.OPERATOR)));
	}

	private void forEachConstantFragment(int start, int end, Consumer<ChildSourceFragment> consumer) {
		if (start == end) {
			return;
		}
		if (start > end) {
			throw new SpoonException("Inconsistent start/end. Start=" + start + " is greater then End=" + end);
		}
		String sourceCode = getOriginalSourceCode();
		StringBuffer buff = new StringBuffer();
		CharType lastType = null;
		int off = start;
		while (off < end) {
			char c = sourceCode.charAt(off);
			CharType type = CharType.fromChar(c);
			if (type != lastType) {
				if (lastType != null) {
					onCharSequence(lastType, buff, consumer);
					buff.setLength(0);
				}
				lastType = type;
			}
			buff.append(c);
			off++;
		}
		onCharSequence(lastType, buff, consumer);
	}

	private void onCharSequence(CharType type, StringBuffer buff, Consumer<ChildSourceFragment> consumer) {
		if (type == CharType.SPACE) {
			consumer.accept(new ConstantSourceFragment(buff.toString(), TokenType.SPACE));
			return;
		}
		char[] str = new char[buff.length()];
		buff.getChars(0, buff.length(), str, 0);
		int off = 0;
		while (off < str.length) {
			//detect java identifier or keyword
			int lenOfIdentifier = detectJavaIdentifier(str, off);
			if (lenOfIdentifier > 0) {
				String identifier = new String(str, off, lenOfIdentifier);
				if (javaKeywords.contains(identifier)) {
					//it is a java keyword
					consumer.accept(new ConstantSourceFragment(identifier, TokenType.KEYWORD));
				} else {
					//it is a java identifier
					consumer.accept(new ConstantSourceFragment(identifier, TokenType.IDENTIFIER));
				}
				off += lenOfIdentifier;
				continue;
			}
			//detect longest match in matchers
			StringMatcher longestMatcher = null;
			for (StringMatcher strMatcher : matchers) {
				if (strMatcher.isMatch(str, off)) {
					longestMatcher = strMatcher.getLonger(longestMatcher);
				}
			}
			if (longestMatcher == null) {
				throw new SpoonException("Unexpected source text: " + buff.toString());
			}
			consumer.accept(new ConstantSourceFragment(longestMatcher.toString(), longestMatcher.getType()));
			off += longestMatcher.getLength();
		}
	}

	/**
	 * @param buff
	 * @param start
	 * @return number of characters in buff starting from start which are java identifier
	 */
	private int detectJavaIdentifier(char[] buff, int start) {
		int len = buff.length;
		int o = start;
		if (start <= len) {
			char c = buff[o];
			if (Character.isJavaIdentifierStart(c)) {
				o++;
				while (o < len) {
					c = buff[o];
					if (Character.isJavaIdentifierPart(c) == false) {
						break;
					}
					o++;
				}
			}
		}
		return o - start;
	}

	private String getOriginalSourceCode() {
		CompilationUnit cu = sourcePosition.getCompilationUnit();
		if (cu != null) {
			return cu.getOriginalSourceCode();
		}
		return null;
	}

	private static final class StringMatcher {
		private final TokenType type;
		private final char[] chars;

		private StringMatcher(final String str, TokenType type) {
			super();
			this.type = type;
			chars = str.toCharArray();
		}

		public boolean isMatch(final char[] buffer, int pos) {
			final int len = chars.length;
			if (pos + len > buffer.length) {
				return false;
			}
			for (int i = 0; i < chars.length; i++, pos++) {
				if (chars[i] != buffer[pos]) {
					return false;
				}
			}
			return true;
		}

		@Override
		public String toString() {
			return new String(chars);
		}

		public int getLength() {
			return chars.length;
		}

		public StringMatcher getLonger(StringMatcher m) {
			if (m != null && m.getLength() > getLength()) {
				return m;
			}
			return this;
		}

		public TokenType getType() {
			return type;
		}
	}

	@Override
	public RoleHandler getRoleHandlerInParent() {
		return roleHandlerInParent;
	}

	@Override
	public boolean hasRole(CtRole role) {
		return roleHandlerInParent != null && roleHandlerInParent.getRole() == role;
	}

	@Override
	public TokenType getType() {
		if (hasRole(CtRole.COMMENT)) {
			return TokenType.COMMENT;
		}
		return null;
	}

	@Override
	public Boolean isModified(ChangeResolver changeResolver) {
		if (roleHandlerInParent != null) {
			return changeResolver.isRoleModified(roleHandlerInParent.getRole());
		}
		return false;
	}
}
