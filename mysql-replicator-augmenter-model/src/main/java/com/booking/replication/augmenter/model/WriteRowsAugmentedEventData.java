package com.booking.replication.augmenter.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class WriteRowsAugmentedEventData implements TableAugmentedEventData {
    private AugmentedEventTable eventTable;
    private List<Boolean> includedColumns;
    private List<AugmentedEventColumn> columns;
    private List<Map<String, Serializable>> rows;

    public WriteRowsAugmentedEventData() {
    }

    public WriteRowsAugmentedEventData(AugmentedEventTable eventTable, List<Boolean> includedColumns, List<AugmentedEventColumn> columns, List<Map<String, Serializable>> rows) {
        this.eventTable = eventTable;
        this.includedColumns = includedColumns;
        this.columns = columns;
        this.rows = rows;
    }

    @Override
    public AugmentedEventTable getEventTable() {
        return this.eventTable;
    }

    public List<Boolean> getIncludedColumns() {
        return this.includedColumns;
    }

    public List<AugmentedEventColumn> getColumns() {
        return this.columns;
    }

    public List<Map<String, Serializable>> getRows() {
        return this.rows;
    }
}
