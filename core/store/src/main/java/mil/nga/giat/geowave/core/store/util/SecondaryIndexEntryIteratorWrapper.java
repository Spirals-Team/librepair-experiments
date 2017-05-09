package mil.nga.giat.geowave.core.store.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

import mil.nga.giat.geowave.core.store.CloseableIterator;
import mil.nga.giat.geowave.core.store.adapter.DataAdapter;

public abstract class SecondaryIndexEntryIteratorWrapper<T, RowType> implements
		Iterator<RowType>,
		CloseableIterator<RowType>
{

	private final Iterator<?> scanIterator;
	protected final DataAdapter<T> adapter;

	private RowType nextValue;

	public SecondaryIndexEntryIteratorWrapper(
			final Iterator<?> scanIterator,
			final DataAdapter<T> adapter ) {
		super();
		this.scanIterator = scanIterator;
		this.adapter = adapter;
	}

	@Override
	public boolean hasNext() {
		findNext();
		return nextValue != null;
	}

	@Override
	public RowType next() {
		if (nextValue == null) {
			findNext();
		}
		final RowType previousNext = nextValue;
		if (nextValue == null) {
			throw new NoSuchElementException();
		}
		nextValue = null;
		return previousNext;
	}

	@Override
	public void remove() {
		scanIterator.remove();
	}

	private void findNext() {
		while ((nextValue == null) && scanIterator.hasNext()) {
			final Object row = scanIterator.next();
			final RowType decodedValue = decodeRow(row);
			if (decodedValue != null) {
				nextValue = decodedValue;
				return;
			}
		}
	}

	protected abstract RowType decodeRow(
			Object row );

}
