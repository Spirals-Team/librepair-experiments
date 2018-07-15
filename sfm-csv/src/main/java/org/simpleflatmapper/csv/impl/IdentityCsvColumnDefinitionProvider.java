package org.simpleflatmapper.csv.impl;

import org.simpleflatmapper.csv.CsvColumnDefinition;
import org.simpleflatmapper.csv.CsvColumnKey;
import org.simpleflatmapper.map.mapper.ColumnDefinitionProvider;
import org.simpleflatmapper.util.BiConsumer;
import org.simpleflatmapper.util.Predicate;

public class IdentityCsvColumnDefinitionProvider implements ColumnDefinitionProvider<CsvColumnDefinition, CsvColumnKey> {
    @Override
    public CsvColumnDefinition getColumnDefinition(CsvColumnKey key) {
        return CsvColumnDefinition.identity();
}

    @Override
    public <CP, BC extends BiConsumer<Predicate<? super CsvColumnKey>, CP>> BC forEach(Class<CP> propertyType, BC consumer) {
        return consumer;
    }
}
