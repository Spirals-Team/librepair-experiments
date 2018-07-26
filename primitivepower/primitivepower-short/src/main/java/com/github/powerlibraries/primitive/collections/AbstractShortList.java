package com.github.powerlibraries.primitive.collections;

import java.util.List;
import java.util.ListIterator;


import com.github.powerlibraries.primitive.common.ShortPointer;

public abstract class AbstractShortList extends AbstractShortCollection implements ShortList {
	
	protected AbstractShortList() {}
	
	@Override
	public Short get(int index) {
		return getShort(index);
	}

	@Override
	public Short set(int index, Short element) {
		return setShort(index, element);
	}

	@Override
	public void add(int index, Short element) {
		addShort(index, element);
	}

	@Override
	public int indexOf(Object o) {
		if(!(o instanceof Short)) {
			return -1;
		}
		return indexOfShort((Short)o);
	}

	@Override
	public int lastIndexOf(Object o) {
		if(!(o instanceof Short)) {
			return -1;
		}
		return lastIndexOfShort((Short)o);
	}
	
	@Override
	public ShortListIterator iterator() {
		return listIterator();
	}

	@Override
	public ShortListIterator listIterator() {
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
		if (o instanceof ShortList) {
			return equals((ShortList) o);
		}

		ShortListIterator e1 = listIterator();
		ListIterator<?> e2 = ((List<?>) o).listIterator();
		while (e1.hasNext() && e2.hasNext()) {
			Object o2 = e2.next();
			if(!(o2 instanceof Short)) {
				return false;
			}
			if (o2!=null || e1.nextShort() != (Short)o2) {
				return false;
			}
		}
		return !(e1.hasNext() || e2.hasNext());
	}
	
	public boolean equals(ShortList o) {
		if (o == this) {
			return true;
		}
		if (o == null) {
			return false;
		}

		ShortListIterator e1 = listIterator();
		ShortListIterator e2 = o.listIterator();
		while (e1.hasNext() && e2.hasNext()) {
			short o1 = e1.nextShort();
			short o2 = e2.nextShort();
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
		for (ShortPointer e : this.primitiveIterable()) {
			hashCode = 31*hashCode + (e==null ? 0 : Short.hashCode(e.get()));
		}
			
		return hashCode;
	}
	
	@Override
	public Short remove(int index) {
		return this.removeAt(index);
	}
}