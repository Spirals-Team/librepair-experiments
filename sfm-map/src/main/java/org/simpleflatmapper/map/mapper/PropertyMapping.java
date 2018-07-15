package org.simpleflatmapper.map.mapper;

import org.simpleflatmapper.map.FieldKey;
import org.simpleflatmapper.reflect.meta.PropertyMeta;

public class PropertyMapping<T, P, K extends FieldKey<K>, D extends ColumnDefinition<K, D>> {
	private final PropertyMeta<T, P> propertyMeta;
	private final K columnKey;
	private final D columnDefinition;

	public PropertyMapping(PropertyMeta<T, P> propertyMeta, K columnKey, D columnDefinition) {
		super();
		this.propertyMeta = propertyMeta;
		this.columnKey = columnKey;
		this.columnDefinition = columnDefinition;
	}

	public <TT, PP> PropertyMapping<TT, PP, K, D> propertyMeta(PropertyMeta<TT, PP> propertyMeta) {
		Object[] definedProperties = propertyMeta.getDefinedProperties();
		D mergeColumnDefintion = definedProperties != null ?  this.columnDefinition.newColumnDefinition(definedProperties) : this.columnDefinition;
		return new PropertyMapping<TT, PP, K, D>(propertyMeta, columnKey, mergeColumnDefintion);
	}

	public PropertyMeta<T, P> getPropertyMeta() {
		return propertyMeta;
	}

	public K getColumnKey() {
		return columnKey;
	}

	public D getColumnDefinition() {
		return columnDefinition;
	}

    @Override
    public String toString() {
        return "PropertyMapping{" +
                "propertyMeta=" + propertyMeta +
                ", columnKey=" + columnKey +
                ", columnDefinition=" + columnDefinition +
                '}';
    }
}
