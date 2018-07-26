package com.github.powerlibraries.primitive.collections;

import java.util.List;
import java.util.ListIterator;


import com.github.powerlibraries.primitive.common.CharPointer;

public abstract class AbstractCharList extends AbstractCharCollection implements CharList {
	
	protected AbstractCharList() {}
	
	@Override
	public Character get(int index) {
		return getChar(index);
	}

	@Override
	public Character set(int index, Character element) {
		return setChar(index, element);
	}

	@Override
	public void add(int index, Character element) {
		addChar(index, element);
	}

	@Override
	public int indexOf(Object o) {
		if(!(o instanceof Character)) {
			return -1;
		}
		return indexOfChar((Character)o);
	}

	@Override
	public int lastIndexOf(Object o) {
		if(!(o instanceof Character)) {
			return -1;
		}
		return lastIndexOfChar((Character)o);
	}
	
	@Override
	public CharListIterator iterator() {
		return listIterator();
	}

	@Override
	public CharListIterator listIterator() {
		return listIterator(0);
	}
	
	/**
	 * Compares the specified object with this list for equality.  Returns
	 * {@code true} if and only if the specified object is also a list, both
	 * lists have the same size, and all corresponding pairs of elements in
	 * the two lists are <i>equal</i>.  (Two elements {@code e1} and
	 * {@code e2} are <i>equal</i> if {@code (e1==null ? e2==null :
	 * e1.equals(e2))}.)  In other words, two lists are defined to be
	 * equal if they contain the same elements in the same order.<p>
	 *
	 * This implementation first checks if the specified object is this
	 * list. If so, it returns {@code true}; if not, it checks if the
	 * specified object is a list. If not, it returns {@code false}; if so,
	 * it iterates over both lists, comparing corresponding pairs of elements.
	 * If any comparison returns {@code false}, this method returns
	 * {@code false}.  If either iterator runs out of elements before the
	 * other it returns {@code false} (as the lists are of unequal length);
	 * otherwise it returns {@code true} when the iterations complete.
	 *
	 * @param o the object to be compared for equality with this list
	 * @return {@code true} if the specified object is equal to this list
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof List)) {
			return false;
		}
		if (o instanceof CharList) {
			return equals((CharList) o);
		}

		CharListIterator e1 = listIterator();
		ListIterator<?> e2 = ((List<?>) o).listIterator();
		while (e1.hasNext() && e2.hasNext()) {
			Object o2 = e2.next();
			if(!(o2 instanceof Character)) {
				return false;
			}
			if (o2!=null || e1.nextChar() != (Character)o2) {
				return false;
			}
		}
		return !(e1.hasNext() || e2.hasNext());
	}
	
	public boolean equals(CharList o) {
		if (o == this) {
			return true;
		}
		if (o == null) {
			return false;
		}

		CharListIterator e1 = listIterator();
		CharListIterator e2 = o.listIterator();
		while (e1.hasNext() && e2.hasNext()) {
			char o1 = e1.nextChar();
			char o2 = e2.nextChar();
			if (o1 != o2) {
				return false;
			}
		}
		return !(e1.hasNext() || e2.hasNext());
	}

	/**
	 * Returns the hash code value for this list.
	 *
	 * <p>This implementation uses exactly the code that is used to define the
	 * list hash function in the documentation for the {@link List#hashCode}
	 * method.
	 *
	 * @return the hash code value for this list
	 */
	public int hashCode() {
		int hashCode = 1;
		for (CharPointer e : this.primitiveIterable()) {
			hashCode = 31*hashCode + (e==null ? 0 : Character.hashCode(e.get()));
		}
			
		return hashCode;
	}
	
	@Override
	public Character remove(int index) {
		return this.removeAt(index);
	}
}