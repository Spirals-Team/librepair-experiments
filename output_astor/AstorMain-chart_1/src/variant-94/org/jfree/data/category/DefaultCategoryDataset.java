package org.jfree.data.category;


public class DefaultCategoryDataset extends org.jfree.data.category.AbstractCategoryDataset implements java.io.Serializable , org.jfree.chart.util.PublicCloneable , org.jfree.data.category.CategoryDataset , org.jfree.data.category.CategoryDatasetSelectionState , org.jfree.data.category.SelectableCategoryDataset {
	private static final long serialVersionUID = -8168173757291644622L;

	private org.jfree.data.KeyedObjects2D data;

	public DefaultCategoryDataset() {
		this.data = new org.jfree.data.KeyedObjects2D();
		setSelectionState(this);
	}

	public int getRowCount() {
		return this.data.getRowCount();
	}

	public int getColumnCount() {
		return this.data.getColumnCount();
	}

	public java.lang.Number getValue(int row, int column) {
		org.jfree.data.SelectableValue sv = ((org.jfree.data.SelectableValue) (this.data.getObject(row, column)));
		if (sv == null) {
			return null;
		}else {
			return sv.getValue();
		}
	}

	public java.lang.Comparable getRowKey(int row) {
		return this.data.getRowKey(row);
	}

	public int getRowIndex(java.lang.Comparable key) {
		return this.data.getRowIndex(key);
	}

	public java.util.List getRowKeys() {
		return this.data.getRowKeys();
	}

	public java.lang.Comparable getColumnKey(int column) {
		return this.data.getColumnKey(column);
	}

	public int getColumnIndex(java.lang.Comparable key) {
		return this.data.getColumnIndex(key);
	}

	public java.util.List getColumnKeys() {
		return this.data.getColumnKeys();
	}

	public java.lang.Number getValue(java.lang.Comparable rowKey, java.lang.Comparable columnKey) {
		org.jfree.data.SelectableValue sv = ((org.jfree.data.SelectableValue) (this.data.getObject(rowKey, columnKey)));
		if (sv != null) {
			return sv.getValue();
		}else {
			return null;
		}
	}

	public void addValue(java.lang.Number value, java.lang.Comparable rowKey, java.lang.Comparable columnKey) {
		this.data.addObject(new org.jfree.data.SelectableValue(value), rowKey, columnKey);
		fireDatasetChanged(new org.jfree.chart.event.DatasetChangeInfo());
	}

	public void addValue(double value, java.lang.Comparable rowKey, java.lang.Comparable columnKey) {
		addValue(new java.lang.Double(value), rowKey, columnKey);
	}

	public void setValue(java.lang.Number value, java.lang.Comparable rowKey, java.lang.Comparable columnKey) {
		this.data.setObject(new org.jfree.data.SelectableValue(value), rowKey, columnKey);
		fireDatasetChanged(new org.jfree.chart.event.DatasetChangeInfo());
	}

	public void setValue(double value, java.lang.Comparable rowKey, java.lang.Comparable columnKey) {
		setValue(new java.lang.Double(value), rowKey, columnKey);
	}

	public void incrementValue(double value, java.lang.Comparable rowKey, java.lang.Comparable columnKey) {
		double existing = 0.0;
		java.lang.Number n = getValue(rowKey, columnKey);
		if (n != null) {
			existing = n.doubleValue();
		}
		setValue((existing + value), rowKey, columnKey);
	}

	public void removeValue(java.lang.Comparable rowKey, java.lang.Comparable columnKey) {
		this.data.removeObject(rowKey, columnKey);
		fireDatasetChanged(new org.jfree.chart.event.DatasetChangeInfo());
	}

	public void removeRow(int rowIndex) {
		this.data.removeRow(rowIndex);
		fireDatasetChanged(new org.jfree.chart.event.DatasetChangeInfo());
	}

	public void removeRow(java.lang.Comparable rowKey) {
		this.data.removeRow(rowKey);
		fireDatasetChanged(new org.jfree.chart.event.DatasetChangeInfo());
	}

	public void removeColumn(int columnIndex) {
		this.data.removeColumn(columnIndex);
		fireDatasetChanged(new org.jfree.chart.event.DatasetChangeInfo());
	}

	public void removeColumn(java.lang.Comparable columnKey) {
		this.data.removeColumn(columnKey);
		fireDatasetChanged(new org.jfree.chart.event.DatasetChangeInfo());
	}

	public void clear() {
		this.data.clear();
		fireDatasetChanged(new org.jfree.chart.event.DatasetChangeInfo());
	}

	public boolean equals(java.lang.Object obj) {
		if (obj == (this)) {
			return true;
		}
		if (!(obj instanceof org.jfree.data.category.CategoryDataset)) {
			return false;
		}
		org.jfree.data.category.CategoryDataset that = ((org.jfree.data.category.CategoryDataset) (obj));
		if (!(getRowKeys().equals(that.getRowKeys()))) {
			return false;
		}
		if (!(getColumnKeys().equals(that.getColumnKeys()))) {
			return false;
		}
		int rowCount = getRowCount();
		int colCount = getColumnCount();
		for (int r = 0; r < rowCount; r++) {
			for (int c = 0; c < colCount; c++) {
				java.lang.Number v1 = getValue(r, c);
				java.lang.Number v2 = that.getValue(r, c);
				if (v1 == null) {
					if (v2 != null) {
						return false;
					}
				}else
					if (!(v1.equals(v2))) {
						return false;
					}

			}
		}
		return true;
	}

	public int hashCode() {
		return this.data.hashCode();
	}

	public java.lang.Object clone() throws java.lang.CloneNotSupportedException {
		org.jfree.data.category.DefaultCategoryDataset clone = ((org.jfree.data.category.DefaultCategoryDataset) (super.clone()));
		clone.data = ((org.jfree.data.KeyedObjects2D) (this.data.clone()));
		return clone;
	}

	public boolean isSelected(int row, int column) {
		org.jfree.data.SelectableValue sv = ((org.jfree.data.SelectableValue) (this.data.getObject(row, column)));
		return sv.isSelected();
	}

	public void setSelected(int row, int column, boolean selected) {
		setSelected(row, column, selected, true);
	}

	public void setSelected(int row, int column, boolean selected, boolean notify) {
		org.jfree.data.SelectableValue sv = ((org.jfree.data.SelectableValue) (this.data.getObject(row, column)));
		sv.setSelected(selected);
		if (notify) {
			fireSelectionEvent();
		}
	}

	public void clearSelection() {
		int rowCount = getRowCount();
		int colCount = getColumnCount();
		for (int r = 0; r < rowCount; r++) {
			for (int c = 0; c < colCount; c++) {
				setSelected(r, c, false, false);
			}
		}
		fireSelectionEvent();
	}

	public void fireSelectionEvent() {
		fireDatasetChanged(new org.jfree.chart.event.DatasetChangeInfo());
	}
}

