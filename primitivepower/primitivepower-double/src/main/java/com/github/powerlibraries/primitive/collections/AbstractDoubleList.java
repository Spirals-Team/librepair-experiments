package com.github.powerlibraries.primitive.collections;

import java.util.List;
import java.util.ListIterator;


import com.github.powerlibraries.primitive.common.DoublePointer;

public abstract class AbstractDoubleList extends AbstractDoubleCollection implements DoubleList {
	
	protected AbstractDoubleList() {}
	
	@Override
	public Double get(int index) {
		return getDouble(index);
	}

	@Override
	public Double set(int index, Double element) {
		return setDouble(index, element);
	}

	@Override
	public void add(int index, Double element) {
		addDouble(index, element);
	}

	@Override
	public int indexOf(Object o) {
		if(!(o instanceof Double)) {
			return -1;
		}
		return indexOfDouble((Double)o);
	}

	@Override
	public int lastIndexOf(Object o) {
		if(!(o instanceof Double)) {
			return -1;
		}
		return lastIndexOfDouble((Double)o);
	}
	
	@Override
	public DoubleListIterator iterator() {
		return listIterator();
	}

	@Override
	public DoubleListIterator listIterator() {
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
		if (o instanceof DoubleList) {
			return equals((DoubleList) o);
		}

		DoubleListIterator e1 = listIterator();
		ListIterator<?> e2 = ((List<?>) o).listIterator();
		while (e1.hasNext() && e2.hasNext()) {
			Object o2 = e2.next();
			if(!(o2 instanceof Double)) {
				return false;
			}
			if (o2!=null || e1.nextDouble() != (Double)o2) {
				return false;
			}
		}
		return !(e1.hasNext() || e2.hasNext());
	}
	
	public boolean equals(DoubleList o) {
		if (o == this) {
			return true;
		}
		if (o == null) {
			return false;
		}

		DoubleListIterator e1 = listIterator();
		DoubleListIterator e2 = o.listIterator();
		while (e1.hasNext() && e2.hasNext()) {
			double o1 = e1.nextDouble();
			double o2 = e2.nextDouble();
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
		for (DoublePointer e : this.primitiveIterable()) {
			hashCode = 31*hashCode + (e==null ? 0 : Double.hashCode(e.get()));
		}
			
		return hashCode;
	}
	
	@Override
	public Double remove(int index) {
		return this.removeAt(index);
	}
}