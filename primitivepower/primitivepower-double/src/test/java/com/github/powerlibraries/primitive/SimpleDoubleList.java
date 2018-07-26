package com.github.powerlibraries.primitive;


import java.nio.DoubleBuffer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.github.powerlibraries.primitive.collections.AbstractDoubleList;
import com.github.powerlibraries.primitive.collections.DoubleCollection;
import com.github.powerlibraries.primitive.collections.DoubleList;
import com.github.powerlibraries.primitive.collections.DoubleListIterator;
import com.github.powerlibraries.primitive.common.DoublePointer;

public class SimpleDoubleList extends AbstractDoubleList {

	private List<Double> l = new ArrayList<>();

	@Override
	public double getDouble(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double setDouble(int index, double element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addDouble(int index, double element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOfDouble(double o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int lastIndexOfDouble(double o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DoubleList subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DoubleListIterator listIterator(int index) {
		ListIterator<Double> it = l.listIterator(index);
		return new DoubleListIterator() {
			
			@Override
			public void remove() {
				it.remove();
			}
			
			@Override
			public int previousIndex() {
				return it.previousIndex();
			}
			
			@Override
			public int nextIndex() {
				return it.nextIndex();
			}
			
			@Override
			public boolean hasPrevious() {
				return it.hasPrevious();
			}
			
			@Override
			public boolean hasNext() {
				return it.hasNext();
			}
			
			@Override
			public void setDouble(double e) {
				it.set(e);
			}
			
			@Override
			public double previousDouble() {
				return it.previous();
			}
			
			@Override
			public double nextDouble() {
				return it.next();
			}
			
			@Override
			public void addDouble(double e) {
				it.add(e);
			}
		};
	}

	@Override
	public Iterable<DoublePointer> primitiveIterable(int index) {
		ListIterator<Double> it = l.listIterator(index);
		return new Iterable<DoublePointer>() {
			public Iterator<DoublePointer> iterator() {
        		return new Iterator<DoublePointer>() {
        			
        			@Override
        			public void remove() {
        				it.remove();
        			}
        			
        			@Override
        			public boolean hasNext() {
        				return it.hasNext();
        			}
        			
        			@Override
        			public DoublePointer next() {
        				return new DoublePointer() {
        					public double get() {
        						return it.next();
        					}
        				};
        			}
        		};
        	}
		};
	}

	@Override
	public void reverse() {
		throw new UnsupportedOperationException();
	}

	@Override
	public double removeAt(int index) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public DoubleBuffer asBuffer() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public int size() {
		return l.size();
	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray() {
		return l.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends Double> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends Double> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsDouble(double o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double[] toDoubleArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addDouble(double e) {
		return l.add(e);
	}

	@Override
	public boolean removeDouble(double o) {
		return l.remove((Double)o);
	}

	@Override
	public boolean addAllDoubles(DoubleCollection c) {
		throw new UnsupportedOperationException();
	}
	
	
	@Override
	public void sort() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void parallelSort() {
		throw new UnsupportedOperationException();
	}
	
}