/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.presto.sql.planner.planPrinter;

import com.facebook.presto.Session;
import com.facebook.presto.metadata.Metadata;
import com.facebook.presto.metadata.TableHandle;
import com.facebook.presto.metadata.TableMetadata;
import com.facebook.presto.spi.CatalogSchemaTableName;
import com.facebook.presto.spi.ColumnHandle;
import com.facebook.presto.spi.ColumnMetadata;
import com.facebook.presto.spi.PrestoException;
import com.facebook.presto.spi.predicate.Domain;
import com.facebook.presto.spi.predicate.Marker;
import com.facebook.presto.spi.predicate.Marker.Bound;
import com.facebook.presto.spi.predicate.TupleDomain;
import com.facebook.presto.spi.type.BigintType;
import com.facebook.presto.spi.type.DoubleType;
import com.facebook.presto.spi.type.SmallintType;
import com.facebook.presto.spi.type.TinyintType;
import com.facebook.presto.spi.type.Type;
import com.facebook.presto.spi.type.VarcharType;
import com.facebook.presto.sql.planner.plan.PlanNode;
import com.facebook.presto.sql.planner.plan.PlanVisitor;
import com.facebook.presto.sql.planner.plan.TableFinishNode;
import com.facebook.presto.sql.planner.plan.TableScanNode;
import com.facebook.presto.sql.planner.plan.TableWriterNode.CreateHandle;
import com.facebook.presto.sql.planner.plan.TableWriterNode.CreateName;
import com.facebook.presto.sql.planner.plan.TableWriterNode.DeleteHandle;
import com.facebook.presto.sql.planner.plan.TableWriterNode.InsertHandle;
import com.facebook.presto.sql.planner.plan.TableWriterNode.InsertReference;
import com.facebook.presto.sql.planner.plan.TableWriterNode.WriterTarget;
import com.facebook.presto.sql.planner.planPrinter.IOPlanPrinter.IOPlan.IOPlanBuilder;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import io.airlift.slice.Slice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.facebook.presto.spi.StandardErrorCode.NOT_SUPPORTED;
import static com.facebook.presto.spi.predicate.Marker.Bound.EXACTLY;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.ImmutableList.toImmutableList;
import static io.airlift.json.JsonCodec.jsonCodec;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class IOPlanPrinter
{
    private final Metadata metadata;
    private final Session session;

    private IOPlanPrinter(Metadata metadata, Session session)
    {
        this.metadata = requireNonNull(metadata, "metadata is null");
        this.session = requireNonNull(session, "session is null");
    }

    private String print(PlanNode plan)
    {
        IOPlanBuilder ioPlanBuilder = new IOPlanBuilder();
        plan.accept(new IOPlanVisitor(), ioPlanBuilder);
        return jsonCodec(IOPlan.class).toJson(ioPlanBuilder.build());
    }

    public static String textIOPlan(PlanNode plan, Metadata metadata, Session session)
    {
        return new IOPlanPrinter(metadata, session).print(plan);
    }

    public static class IOPlan
    {
        private final List<TableColumnInfo> inputTableColumnInfos;
        private final Optional<CatalogSchemaTableName> outputTable;

        @JsonCreator
        public IOPlan(
                @JsonProperty("inputTableColumnInfos") List<TableColumnInfo> inputTableColumnInfos,
                @JsonProperty("outputTable") Optional<CatalogSchemaTableName> outputTable)
        {
            this.inputTableColumnInfos = ImmutableList.copyOf(requireNonNull(inputTableColumnInfos, "inputTableColumnInfos is null"));
            this.outputTable = requireNonNull(outputTable, "outputTable is null");
        }

        @JsonProperty
        public List<TableColumnInfo> getInputTableColumnInfos()
        {
            return inputTableColumnInfos;
        }

        @JsonProperty
        public Optional<CatalogSchemaTableName> getOutputTable()
        {
            return outputTable;
        }

        protected static class IOPlanBuilder
        {
            private List<TableColumnInfo> inputTableColumnInfos;
            private Optional<CatalogSchemaTableName> outputTable;

            private IOPlanBuilder()
            {
                this.inputTableColumnInfos = new ArrayList<>();
                this.outputTable = Optional.empty();
            }

            private void addInputTableColumnInfo(TableColumnInfo tableColumnInfo)
            {
                inputTableColumnInfos.add(tableColumnInfo);
            }

            private void setOutputTable(CatalogSchemaTableName outputTable)
            {
                this.outputTable = Optional.of(outputTable);
            }

            private IOPlan build()
            {
                return new IOPlan(inputTableColumnInfos, outputTable);
            }
        }

        public static class TableColumnInfo
        {
            private final CatalogSchemaTableName table;
            private final List<ColumnConstraint> columnConstraints;

            @JsonCreator
            public TableColumnInfo(
                    @JsonProperty("table") CatalogSchemaTableName table,
                    @JsonProperty("columnConstraints") List<ColumnConstraint> columnConstraints)
            {
                this.table = requireNonNull(table, "table is null");
                this.columnConstraints = requireNonNull(columnConstraints, "columnConstraints is null");
            }

            @JsonProperty
            public CatalogSchemaTableName getTable()
            {
                return table;
            }

            @JsonProperty
            public List<ColumnConstraint> getColumns()
            {
                return columnConstraints;
            }
        }
    }

    public static class ColumnConstraint
    {
        private final String columnName;
        private final Type type;
        private final FormattedDomain domain;

        @JsonCreator
        public ColumnConstraint(
                @JsonProperty("columnName") String columnName,
                @JsonProperty("type") Type type,
                @JsonProperty("domain") FormattedDomain domain)
        {
            this.columnName = requireNonNull(columnName, "columnName is null");
            this.type = requireNonNull(type, "type is null");
            this.domain = requireNonNull(domain, "domain is null");
        }

        @JsonProperty
        public String getColumnName()
        {
            return columnName;
        }

        @JsonProperty
        public Type getType()
        {
            return type;
        }

        @JsonProperty
        public FormattedDomain getDomain()
        {
            return domain;
        }
    }

    public static class FormattedDomain
    {
        private final boolean nullsAllowed;
        private final List<FormattedRange> ranges;

        @JsonCreator
        public FormattedDomain(
                @JsonProperty("nullsAllowed") boolean nullsAllowed,
                @JsonProperty("ranges") List<FormattedRange> ranges)
        {
            this.nullsAllowed = nullsAllowed;
            this.ranges = ImmutableList.copyOf(requireNonNull(ranges, "ranges is null"));
        }

        @JsonProperty
        public boolean isNullsAllowed()
        {
            return nullsAllowed;
        }

        @JsonProperty
        public List<FormattedRange> getRanges()
        {
            return ranges;
        }
    }

    public static class FormattedRange
    {
        private final FormattedMarker low;
        private final FormattedMarker high;

        @JsonCreator
        public FormattedRange(
                @JsonProperty("low") FormattedMarker low,
                @JsonProperty("high") FormattedMarker high)
        {
            this.low = requireNonNull(low, "low is null");
            this.high = requireNonNull(high, "high is null");
        }

        @JsonProperty
        public FormattedMarker getLow()
        {
            return low;
        }

        @JsonProperty
        public FormattedMarker getHigh()
        {
            return high;
        }
    }

    public static class FormattedMarker
    {
        private final Optional<String> value;
        private final Bound bound;

        @JsonCreator
        public FormattedMarker(
                @JsonProperty("value") Optional<String> value,
                @JsonProperty("bound") Bound bound)
        {
            this.value = requireNonNull(value, "value is null");
            this.bound = requireNonNull(bound, "bound is null");
        }

        @JsonProperty
        public Optional<String> getValue()
        {
            return value;
        }

        @JsonProperty
        public Bound getBound()
        {
            return bound;
        }
    }

    private class IOPlanVisitor
            extends PlanVisitor<Void, IOPlanBuilder>
    {
        @Override
        protected Void visitPlan(PlanNode node, IOPlanBuilder context)
        {
            return processChildren(node, context);
        }

        @Override
        public Void visitTableScan(TableScanNode node, IOPlanBuilder context)
        {
            TableMetadata tableMetadata = metadata.getTableMetadata(session, node.getTable());
            context.addInputTableColumnInfo(new IOPlan.TableColumnInfo(
                    new CatalogSchemaTableName(
                            tableMetadata.getConnectorId().getCatalogName(),
                            tableMetadata.getTable().getSchemaName(),
                            tableMetadata.getTable().getTableName()),
                    parseConstraints(node.getTable(), node.getCurrentConstraint())));
            return null;
        }

        @Override
        public Void visitTableFinish(TableFinishNode node, IOPlanBuilder context)
        {
            WriterTarget writerTarget = node.getTarget();
            if (writerTarget instanceof CreateHandle) {
                CreateHandle createHandle = (CreateHandle) writerTarget;
                context.setOutputTable(new CatalogSchemaTableName(
                        createHandle.getHandle().getConnectorId().getCatalogName(),
                        createHandle.getSchemaTableName().getSchemaName(),
                        createHandle.getSchemaTableName().getTableName()));
            }
            else if (writerTarget instanceof InsertHandle) {
                InsertHandle insertHandle = (InsertHandle) writerTarget;
                context.setOutputTable(new CatalogSchemaTableName(
                        insertHandle.getHandle().getConnectorId().getCatalogName(),
                        insertHandle.getSchemaTableName().getSchemaName(),
                        insertHandle.getSchemaTableName().getTableName()));
            }
            else if (writerTarget instanceof DeleteHandle) {
                DeleteHandle deleteHandle = (DeleteHandle) writerTarget;
                context.setOutputTable(new CatalogSchemaTableName(
                        deleteHandle.getHandle().getConnectorId().getCatalogName(),
                        deleteHandle.getSchemaTableName().getSchemaName(),
                        deleteHandle.getSchemaTableName().getTableName()));
            }
            else if (writerTarget instanceof CreateName || writerTarget instanceof InsertReference) {
                throw new IllegalStateException(format("%s should not appear in final plan", writerTarget.getClass().getSimpleName()));
            }
            else {
                throw new IllegalStateException(format("Unknown WriterTarget subclass %s", writerTarget.getClass().getSimpleName()));
            }
            return processChildren(node, context);
        }

        private List<ColumnConstraint> parseConstraints(TableHandle tableHandle, TupleDomain<ColumnHandle> constraint)
        {
            checkArgument(!constraint.isNone());
            ImmutableList.Builder<ColumnConstraint> columnConstraints = ImmutableList.builder();
            for (Map.Entry<ColumnHandle, Domain> entry : constraint.getDomains().get().entrySet()) {
                ColumnMetadata columnMetadata = metadata.getColumnMetadata(session, tableHandle, entry.getKey());
                columnConstraints.add(new ColumnConstraint(columnMetadata.getName(), columnMetadata.getType(), parseDomain(entry.getValue())));
            }
            return columnConstraints.build();
        }

        private FormattedDomain parseDomain(Domain domain)
        {
            ImmutableList.Builder<FormattedRange> formattedRanges = ImmutableList.builder();
            Type type = domain.getType();

            domain.getValues().getValuesProcessor().consume(
                    ranges -> formattedRanges.addAll(
                            ranges.getOrderedRanges().stream()
                                    .map(range -> new FormattedRange(formatMarker(range.getLow()), formatMarker(range.getHigh())))
                                    .collect(toImmutableList())),
                    discreteValues -> formattedRanges.addAll(
                            discreteValues.getValues().stream()
                                    .map(value -> getVarcharValue(type, value))
                                    .map(value -> new FormattedMarker(Optional.of(value), EXACTLY))
                                    .map(marker -> new FormattedRange(marker, marker))
                                    .collect(toImmutableList())),
                    allOrNone -> {
                        throw new IllegalStateException("Unreachable AllOrNone consumer");
                    });

            return new FormattedDomain(domain.isNullAllowed(), formattedRanges.build());
        }

        private FormattedMarker formatMarker(Marker marker)
        {
            if (!marker.getValueBlock().isPresent()) {
                return new FormattedMarker(Optional.empty(), marker.getBound());
            }
            return new FormattedMarker(Optional.of(getVarcharValue(marker.getType(), marker.getValue())), marker.getBound());
        }

        private String getVarcharValue(Type type, Object value)
        {
            if (type instanceof VarcharType) {
                return ((Slice) value).toStringUtf8();
            }
            if (type instanceof TinyintType || type instanceof SmallintType || type instanceof BigintType || type instanceof DoubleType) {
                return String.valueOf(value);
            }
            throw new PrestoException(NOT_SUPPORTED, format("Unsupported type in EXPLAIN (TYPE IO): %s", type.getDisplayName()));
        }

        private Void processChildren(PlanNode node, IOPlanBuilder context)
        {
            for (PlanNode child : node.getSources()) {
                child.accept(this, context);
            }

            return null;
        }
    }
}
