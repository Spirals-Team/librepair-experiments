package com.booking.replication.augmenter.model.schema;

import com.booking.replication.augmenter.model.FullTableName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class CurrentPositionSchemaVersion {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final Map<Long, FullTableName> tableIdToTableNameMap;

    private final Map<String, TableSchema> tableSchemaCache;

    public CurrentPositionSchemaVersion() {
        this.tableIdToTableNameMap = new ConcurrentHashMap<>();
        this.tableSchemaCache = new ConcurrentHashMap<>();
    }

    public Map<Long, FullTableName> getTableIdToTableNameMap() {
        return tableIdToTableNameMap;
    }

    public void removeTableFromCache(String tableName) {
        this.tableSchemaCache.remove(tableName);
    }

    // get from tableSchemaCache or from active schema
    public TableSchema getTableColumns(
            String tableName,
            Function<String,TableSchema> computeAndReturnTableSchema) {
        return this.tableSchemaCache.computeIfAbsent(tableName, computeAndReturnTableSchema);
    }
}
