package com.github.powerlibraries.primitive;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.github.powerlibraries.primitive.collections.AbstractObjectList;
import com.github.powerlibraries.primitive.collections.ObjectCollection;
import com.github.powerlibraries.primitive.collections.ObjectList;
import com.github.powerlibraries.primitive.collections.ObjectListIterator;
import com.github.powerlibraries.primitive.common.ObjectPointer;

public class SimpleObjectList<E> extends AbstractObjectList<E> {

	private List<E> l = new ArrayList<>();

	@Override
	public E getObject(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E setObject(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addObject(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOfObject(E o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int lastIndexOfObject(E o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ObjectList subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ObjectListIterator<E> listIterator(int index) {
		ListIterator<E> it = l.listIterator(index);
		return new ObjectListIterator<E>() {
			
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
			public void setObject(E e) {
				it.set(e);
			}
			
			@Override
			public E previousObject() {
				return it.previous();
			}
			
			@Override
			public E nextObject() {
				return it.next();
			}
			
			@Override
			public void addObject(E e) {
				it.add(e);
			}
		};
	}

	@Override
	public Iterable<ObjectPointer<E>> primitiveIterable(int index) {
		ListIterator<E> it = l.listIterator(index);
		return new Iterable<ObjectPointer<E>>() {
			public Iterator<ObjectPointer<E>> iterator() {
        		return new Iterator<ObjectPointer<E>>() {
        			
        			@Override
        			public void remove() {
        				it.remove();
        			}
        			
        			@Override
        			public boolean hasNext() {
        				return it.hasNext();
        			}
        			
        			@Override
        			public ObjectPointer<E> next() {
        				return new ObjectPointer<E>() {
        					public E get() {
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
	public E removeAt(int index) {
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
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
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
	public boolean containsObject(E o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E[] toObjectArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addObject(E e) {
		return l.add(e);
	}

	@Override
	public boolean removeObject(E o) {
		return l.remove((E)o);
	}

	@Override
	public boolean addAllObjects(ObjectCollection c) {
		throw new UnsupportedOperationException();
	}
	
	
}