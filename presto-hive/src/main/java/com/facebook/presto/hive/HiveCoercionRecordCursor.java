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
package com.facebook.presto.hive;

import com.facebook.presto.hive.HivePageSourceProvider.ColumnMapping;
import com.facebook.presto.spi.PageBuilder;
import com.facebook.presto.spi.PrestoException;
import com.facebook.presto.spi.RecordCursor;
import com.facebook.presto.spi.block.Block;
import com.facebook.presto.spi.block.BlockBuilder;
import com.facebook.presto.spi.type.Type;
import com.facebook.presto.spi.type.TypeManager;
import com.facebook.presto.spi.type.VarcharType;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import io.airlift.slice.Slice;
import org.apache.hadoop.hive.serde2.typeinfo.ListTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.MapTypeInfo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.facebook.presto.hive.HiveType.HIVE_BYTE;
import static com.facebook.presto.hive.HiveType.HIVE_DOUBLE;
import static com.facebook.presto.hive.HiveType.HIVE_FLOAT;
import static com.facebook.presto.hive.HiveType.HIVE_INT;
import static com.facebook.presto.hive.HiveType.HIVE_LONG;
import static com.facebook.presto.hive.HiveType.HIVE_SHORT;
import static com.facebook.presto.hive.HiveUtil.extractStructFieldTypes;
import static com.facebook.presto.hive.HiveUtil.isArrayType;
import static com.facebook.presto.hive.HiveUtil.isMapType;
import static com.facebook.presto.hive.HiveUtil.isRowType;
import static com.facebook.presto.spi.StandardErrorCode.NOT_SUPPORTED;
import static io.airlift.slice.Slices.utf8Slice;
import static java.lang.Float.intBitsToFloat;
import static java.lang.Math.min;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class HiveCoercionRecordCursor
        implements RecordCursor
{
    private final RecordCursor delegate;
    private final List<ColumnMapping> columnMappings;
    private final Coercer[] coercers;
    private final BridgingRecordCursor bridgingRecordCursor;

    public HiveCoercionRecordCursor(
            List<ColumnMapping> columnMappings,
            TypeManager typeManager,
            RecordCursor delegate)
    {
        requireNonNull(columnMappings, "columns is null");
        requireNonNull(typeManager, "typeManager is null");
        this.bridgingRecordCursor = new BridgingRecordCursor();

        this.delegate = requireNonNull(delegate, "delegate is null");
        this.columnMappings = ImmutableList.copyOf(columnMappings);

        int size = columnMappings.size();

        this.coercers = new Coercer[size];

        for (int columnIndex = 0; columnIndex < size; columnIndex++) {
            ColumnMapping columnMapping = columnMappings.get(columnIndex);

            if (columnMapping.getCoercionFrom().isPresent()) {
                coercers[columnIndex] = createCoercer(typeManager, columnMapping.getCoercionFrom().get(), columnMapping.getHiveColumnHandle().getHiveType(), bridgingRecordCursor);
            }
        }
    }

    @Override
    public long getCompletedBytes()
    {
        return delegate.getCompletedBytes();
    }

    @Override
    public Type getType(int field)
    {
        return delegate.getType(field);
    }

    @Override
    public boolean advanceNextPosition()
    {
        for (int i = 0; i < columnMappings.size(); i++) {
            if (coercers[i] != null) {
                coercers[i].reset();
            }
        }
        return delegate.advanceNextPosition();
    }

    @Override
    public boolean getBoolean(int field)
    {
        if (coercers[field] == null) {
            return delegate.getBoolean(field);
        }
        return coercers[field].getBoolean(delegate, field);
    }

    @Override
    public long getLong(int field)
    {
        if (coercers[field] == null) {
            return delegate.getLong(field);
        }
        return coercers[field].getLong(delegate, field);
    }

    @Override
    public double getDouble(int field)
    {
        if (coercers[field] == null) {
            return delegate.getDouble(field);
        }
        return coercers[field].getDouble(delegate, field);
    }

    @Override
    public Slice getSlice(int field)
    {
        if (coercers[field] == null) {
            return delegate.getSlice(field);
        }
        return coercers[field].getSlice(delegate, field);
    }

    @Override
    public Object getObject(int field)
    {
        if (coercers[field] == null) {
            return delegate.getObject(field);
        }
        return coercers[field].getObject(delegate, field);
    }

    @Override
    public boolean isNull(int field)
    {
        if (coercers[field] == null) {
            return delegate.isNull(field);
        }
        return coercers[field].isNull(delegate, field);
    }

    @Override
    public void close()
    {
        delegate.close();
    }

    @Override
    public long getReadTimeNanos()
    {
        return delegate.getReadTimeNanos();
    }

    @Override
    public long getSystemMemoryUsage()
    {
        return delegate.getSystemMemoryUsage();
    }

    @VisibleForTesting
    RecordCursor getRegularColumnRecordCursor()
    {
        return delegate;
    }

    private abstract static class Coercer
    {
        private boolean isNull;
        private boolean loaded;

        private boolean booleanValue;
        private long longValue;
        private double doubleValue;
        private Slice sliceValue;
        private Object objectValue;

        public void reset()
        {
            isNull = false;
            loaded = false;
        }

        public boolean isNull(RecordCursor delegate, int field)
        {
            assureLoaded(delegate, field);
            return isNull;
        }

        public boolean getBoolean(RecordCursor delegate, int field)
        {
            assureLoaded(delegate, field);
            return booleanValue;
        }

        public long getLong(RecordCursor delegate, int field)
        {
            assureLoaded(delegate, field);
            return longValue;
        }

        public double getDouble(RecordCursor delegate, int field)
        {
            assureLoaded(delegate, field);
            return doubleValue;
        }

        public Slice getSlice(RecordCursor delegate, int field)
        {
            assureLoaded(delegate, field);
            return sliceValue;
        }

        public Object getObject(RecordCursor delegate, int field)
        {
            assureLoaded(delegate, field);
            return objectValue;
        }

        private void assureLoaded(RecordCursor delegate, int field)
        {
            if (!loaded) {
                isNull = delegate.isNull(field);
                if (!isNull) {
                    coerce(delegate, field);
                }
                loaded = true;
            }
        }

        protected abstract void coerce(RecordCursor delegate, int field);

        protected void setBoolean(boolean value)
        {
            booleanValue = value;
        }

        protected void setLong(long value)
        {
            longValue = value;
        }

        protected void setDouble(double value)
        {
            doubleValue = value;
        }

        protected void setSlice(Slice value)
        {
            sliceValue = value;
        }

        protected void setObject(Object value)
        {
            objectValue = value;
        }

        protected void setIsNull(boolean isNull)
        {
            this.isNull = isNull;
        }
    }

    private static Coercer createCoercer(TypeManager typeManager, HiveType fromHiveType, HiveType toHiveType, BridgingRecordCursor bridgingRecordCursor)
    {
        Type fromType = typeManager.getType(fromHiveType.getTypeSignature());
        Type toType = typeManager.getType(toHiveType.getTypeSignature());
        if (toType instanceof VarcharType && (fromHiveType.equals(HIVE_BYTE) || fromHiveType.equals(HIVE_SHORT) || fromHiveType.equals(HIVE_INT) || fromHiveType.equals(HIVE_LONG))) {
            return new IntegerNumberToVarcharCoercer();
        }
        else if (fromType instanceof VarcharType && (toHiveType.equals(HIVE_BYTE) || toHiveType.equals(HIVE_SHORT) || toHiveType.equals(HIVE_INT) || toHiveType.equals(HIVE_LONG))) {
            return new VarcharToIntegerNumberCoercer(toHiveType);
        }
        else if (fromHiveType.equals(HIVE_BYTE) && toHiveType.equals(HIVE_SHORT) || toHiveType.equals(HIVE_INT) || toHiveType.equals(HIVE_LONG)) {
            return new IntegerNumberUpscaleCoercer();
        }
        else if (fromHiveType.equals(HIVE_SHORT) && toHiveType.equals(HIVE_INT) || toHiveType.equals(HIVE_LONG)) {
            return new IntegerNumberUpscaleCoercer();
        }
        else if (fromHiveType.equals(HIVE_INT) && toHiveType.equals(HIVE_LONG)) {
            return new IntegerNumberUpscaleCoercer();
        }
        else if (fromHiveType.equals(HIVE_FLOAT) && toHiveType.equals(HIVE_DOUBLE)) {
            return new FloatToDoubleCoercer();
        }
        else if (isArrayType(fromType) && isArrayType(toType)) {
            return new ListCoercer(typeManager, fromHiveType, toHiveType, bridgingRecordCursor);
        }
        else if (isMapType(fromType) && isMapType(toType)) {
            return new MapCoercer(typeManager, fromHiveType, toHiveType, bridgingRecordCursor);
        }
        else if (isRowType(fromType) && isRowType(toType)) {
            return new StructCoercer(typeManager, fromHiveType, toHiveType, bridgingRecordCursor);
        }

        throw new PrestoException(NOT_SUPPORTED, format("Unsupported coercion from %s to %s", fromHiveType, toHiveType));
    }

    private static class IntegerNumberUpscaleCoercer
            extends Coercer
    {
        @Override
        public void coerce(RecordCursor delegate, int field)
        {
            setLong(delegate.getLong(field));
        }
    }

    private static class IntegerNumberToVarcharCoercer
            extends Coercer
    {
        @Override
        public void coerce(RecordCursor delegate, int field)
        {
            setSlice(utf8Slice(String.valueOf(delegate.getLong(field))));
        }
    }

    private static class FloatToDoubleCoercer
            extends Coercer
    {
        @Override
        protected void coerce(RecordCursor delegate, int field)
        {
            setDouble(intBitsToFloat((int) delegate.getLong(field)));
        }
    }

    private static class VarcharToIntegerNumberCoercer
            extends Coercer
    {
        private final long maxValue;
        private final long minValue;

        public VarcharToIntegerNumberCoercer(HiveType type)
        {
            if (type.equals(HIVE_BYTE)) {
                minValue = Byte.MIN_VALUE;
                maxValue = Byte.MAX_VALUE;
            }
            else if (type.equals(HIVE_SHORT)) {
                minValue = Short.MIN_VALUE;
                maxValue = Short.MAX_VALUE;
            }
            else if (type.equals(HIVE_INT)) {
                minValue = Integer.MIN_VALUE;
                maxValue = Integer.MAX_VALUE;
            }
            else if (type.equals(HIVE_LONG)) {
                minValue = Long.MIN_VALUE;
                maxValue = Long.MAX_VALUE;
            }
            else {
                throw new PrestoException(NOT_SUPPORTED, format("Could not create Coercer from varchar to %s", type));
            }
        }

        @Override
        public void coerce(RecordCursor delegate, int field)
        {
            try {
                long value = Long.parseLong(delegate.getSlice(field).toStringUtf8());
                if (minValue <= value && value <= maxValue) {
                    setLong(value);
                }
                else {
                    setIsNull(true);
                }
            }
            catch (NumberFormatException e) {
                setIsNull(true);
            }
        }
    }

    private static class ListCoercer
            extends Coercer
    {
        private final HiveType fromElementHiveType;
        private final HiveType toElementHiveType;
        private final Type fromElementType;
        private final Type toElementType;
        private final Coercer elementCoercer;
        private final BridgingRecordCursor bridgingRecordCursor;
        private final PageBuilder pageBuilder;

        public ListCoercer(TypeManager typeManager, HiveType fromHiveType, HiveType toHiveType, BridgingRecordCursor bridgingRecordCursor)
        {
            requireNonNull(typeManager, "typeManage is null");
            requireNonNull(fromHiveType, "fromHiveType is null");
            requireNonNull(toHiveType, "toHiveType is null");
            this.bridgingRecordCursor = requireNonNull(bridgingRecordCursor, "bridgingRecordCursor is null");
            this.fromElementHiveType = HiveType.valueOf(((ListTypeInfo) fromHiveType.getTypeInfo()).getListElementTypeInfo().getTypeName());
            this.toElementHiveType = HiveType.valueOf(((ListTypeInfo) toHiveType.getTypeInfo()).getListElementTypeInfo().getTypeName());
            this.fromElementType = fromElementHiveType.getType(typeManager);
            this.toElementType = toElementHiveType.getType(typeManager);
            this.elementCoercer = fromElementHiveType.equals(toElementHiveType) ? null : createCoercer(typeManager, fromElementHiveType, toElementHiveType, bridgingRecordCursor);
            this.pageBuilder = elementCoercer == null ? null : new PageBuilder(ImmutableList.of(toHiveType.getType(typeManager)));
        }

        @Override
        public void coerce(RecordCursor delegate, int field)
        {
            if (delegate.isNull(field)) {
                setIsNull(true);
                return;
            }
            Block block = (Block) delegate.getObject(field);
            if (pageBuilder.isFull()) {
                pageBuilder.reset();
            }
            BlockBuilder blockBuilder = pageBuilder.getBlockBuilder(0);
            BlockBuilder listBuilder = blockBuilder.beginBlockEntry();
            for (int i = 0; i < block.getPositionCount(); i++) {
                if (block.isNull(i)) {
                    listBuilder.appendNull();
                }
                else if (elementCoercer == null) {
                    block.writePositionTo(i, listBuilder);
                    listBuilder.closeEntry();
                }
                else {
                    rewriteBlock(fromElementType, toElementType, block, i, listBuilder, elementCoercer, field, bridgingRecordCursor);
                }
            }
            blockBuilder.closeEntry();
            setObject(blockBuilder.getRegion(blockBuilder.getPositionCount() - 1, 1).getObject(0, Block.class));
        }
    }

    private static class MapCoercer
            extends Coercer
    {
        private final HiveType fromKeyHiveType;
        private final HiveType toKeyHiveType;
        private final HiveType fromValueHiveType;
        private final HiveType toValueHiveType;
        private final Type fromKeyType;
        private final Type toKeyType;
        private final Type fromValueType;
        private final Type toValueType;
        private final Coercer keyCoercer;
        private final Coercer valueCoercer;
        private final BridgingRecordCursor bridgingRecordCursor;
        private final PageBuilder pageBuilder;

        public MapCoercer(TypeManager typeManager, HiveType fromHiveType, HiveType toHiveType, BridgingRecordCursor bridgingRecordCursor)
        {
            requireNonNull(typeManager, "typeManage is null");
            requireNonNull(fromHiveType, "fromHiveType is null");
            requireNonNull(toHiveType, "toHiveType is null");
            this.bridgingRecordCursor = requireNonNull(bridgingRecordCursor, "bridgingRecordCursor is null");
            this.fromKeyHiveType = HiveType.valueOf(((MapTypeInfo) fromHiveType.getTypeInfo()).getMapKeyTypeInfo().getTypeName());
            this.fromValueHiveType = HiveType.valueOf(((MapTypeInfo) fromHiveType.getTypeInfo()).getMapValueTypeInfo().getTypeName());
            this.toKeyHiveType = HiveType.valueOf(((MapTypeInfo) toHiveType.getTypeInfo()).getMapKeyTypeInfo().getTypeName());
            this.toValueHiveType = HiveType.valueOf(((MapTypeInfo) toHiveType.getTypeInfo()).getMapValueTypeInfo().getTypeName());
            this.fromKeyType = fromKeyHiveType.getType(typeManager);
            this.toKeyType = toKeyHiveType.getType(typeManager);
            this.fromValueType = fromValueHiveType.getType(typeManager);
            this.toValueType = toValueHiveType.getType(typeManager);
            this.keyCoercer = fromKeyHiveType.equals(toKeyHiveType) ? null : createCoercer(typeManager, fromKeyHiveType, toKeyHiveType, bridgingRecordCursor);
            this.valueCoercer = fromValueHiveType.equals(toValueHiveType) ? null : createCoercer(typeManager, fromValueHiveType, toValueHiveType, bridgingRecordCursor);
            this.pageBuilder = keyCoercer == null && valueCoercer == null ? null : new PageBuilder(ImmutableList.of(toHiveType.getType(typeManager)));
        }

        @Override
        public void coerce(RecordCursor delegate, int field)
        {
            if (delegate.isNull(field)) {
                setIsNull(true);
                return;
            }
            Block block = (Block) delegate.getObject(field);
            if (pageBuilder.isFull()) {
                pageBuilder.reset();
            }
            BlockBuilder blockBuilder = pageBuilder.getBlockBuilder(0);
            BlockBuilder mapBuilder = blockBuilder.beginBlockEntry();
            for (int i = 0; i < block.getPositionCount(); i += 2) {
                if (block.isNull(i)) {
                    mapBuilder.appendNull();
                }
                else if (keyCoercer == null) {
                    block.writePositionTo(i, mapBuilder);
                    mapBuilder.closeEntry();
                }
                else {
                    rewriteBlock(fromKeyType, toKeyType, block.getSingleValueBlock(i), 0, mapBuilder, keyCoercer, field, bridgingRecordCursor);
                }
                if (block.isNull(i + 1)) {
                    mapBuilder.appendNull();
                }
                if (valueCoercer == null) {
                    block.writePositionTo(i + 1, mapBuilder);
                    mapBuilder.closeEntry();
                }
                else {
                    rewriteBlock(fromValueType, toValueType, block.getSingleValueBlock(i + 1), 0, mapBuilder, valueCoercer, field, bridgingRecordCursor);
                }
            }
            blockBuilder.closeEntry();
            setObject(blockBuilder.getRegion(blockBuilder.getPositionCount() - 1, 1).getObject(0, Block.class));
        }
    }

    private static class StructCoercer
            extends Coercer
    {
        private final List<HiveType> fromFieldHiveTypes;
        private final List<HiveType> toFieldHiveTypes;
        private final List<Type> fromFieldTypes;
        private final List<Type> toFieldTypes;
        private final Coercer[] coercers;
        private final BridgingRecordCursor bridgingRecordCursor;
        private final PageBuilder pageBuilder;

        public StructCoercer(TypeManager typeManager, HiveType fromHiveType, HiveType toHiveType, BridgingRecordCursor bridgingRecordCursor)
        {
            requireNonNull(typeManager, "typeManage is null");
            requireNonNull(fromHiveType, "fromHiveType is null").getType(typeManager);
            requireNonNull(toHiveType, "toHiveType is null");
            this.bridgingRecordCursor = requireNonNull(bridgingRecordCursor, "bridgingRecordCursor is null");
            this.fromFieldHiveTypes = extractStructFieldTypes(fromHiveType);
            this.toFieldHiveTypes = extractStructFieldTypes(toHiveType);
            this.fromFieldTypes = fromFieldHiveTypes.stream()
                    .map(hiveType -> hiveType.getType(typeManager))
                    .collect(Collectors.toList());
            this.toFieldTypes = toFieldHiveTypes.stream()
                    .map(hiveType -> hiveType.getType(typeManager))
                    .collect(Collectors.toList());
            this.coercers = new Coercer[toFieldHiveTypes.size()];
            for (int i = 0; i < min(fromFieldHiveTypes.size(), toFieldHiveTypes.size()); i++) {
                if (!fromFieldTypes.get(i).equals(toFieldTypes.get(i))) {
                    coercers[i] = createCoercer(typeManager, fromFieldHiveTypes.get(i), toFieldHiveTypes.get(i), bridgingRecordCursor);
                }
            }
            this.pageBuilder = new PageBuilder(ImmutableList.of(toHiveType.getType(typeManager)));
        }

        @Override
        public void coerce(RecordCursor delegate, int field)
        {
            if (delegate.isNull(field)) {
                setIsNull(true);
                return;
            }
            Block block = (Block) delegate.getObject(field);
            if (pageBuilder.isFull()) {
                pageBuilder.reset();
            }
            BlockBuilder blockBuilder = pageBuilder.getBlockBuilder(0);
            BlockBuilder rowBuilder = blockBuilder.beginBlockEntry();
            for (int i = 0; i < toFieldTypes.size(); i++) {
                if (i >= fromFieldTypes.size() || block.isNull(i)) {
                    rowBuilder.appendNull();
                }
                else if (coercers[i] == null) {
                    block.writePositionTo(i, rowBuilder);
                    rowBuilder.closeEntry();
                }
                else {
                    rewriteBlock(fromFieldTypes.get(i), toFieldTypes.get(i), block, i, rowBuilder, coercers[i], field, bridgingRecordCursor);
                }
            }
            blockBuilder.closeEntry();
            setObject(blockBuilder.getRegion(blockBuilder.getPositionCount() - 1, 1).getObject(0, Block.class));
        }
    }

    private static void rewriteBlock(
            Type fromType,
            Type toType,
            Block block,
            int position,
            BlockBuilder blockBuilder,
            Coercer coercer,
            int field,
            BridgingRecordCursor bridgingRecordCursor)
    {
        bridgingRecordCursor.setType(fromType);
        Class<?> fromJavaType = fromType.getJavaType();
        if (fromJavaType == long.class) {
            bridgingRecordCursor.setValue(fromType.getLong(block, position));
        }
        else if (fromJavaType == double.class) {
            bridgingRecordCursor.setValue(fromType.getDouble(block, position));
        }
        else if (fromJavaType == boolean.class) {
            bridgingRecordCursor.setValue(fromType.getBoolean(block, position));
        }
        else if (fromJavaType == Slice.class) {
            bridgingRecordCursor.setValue(fromType.getSlice(block, position));
        }
        else if (fromJavaType == Block.class) {
            bridgingRecordCursor.setValue(fromType.getObject(block, position));
        }
        else {
            bridgingRecordCursor.setValue(null);
        }
        coercer.reset();
        Class<?> toJaveType = toType.getJavaType();
        if (coercer.isNull(bridgingRecordCursor, field)) {
            blockBuilder.appendNull();
        }
        else if (toJaveType == long.class) {
            toType.writeLong(blockBuilder, coercer.getLong(bridgingRecordCursor, field));
        }
        else if (toJaveType == double.class) {
            toType.writeDouble(blockBuilder, coercer.getDouble(bridgingRecordCursor, field));
        }
        else if (toJaveType == Slice.class) {
            toType.writeSlice(blockBuilder, coercer.getSlice(bridgingRecordCursor, field));
        }
        else if (toJaveType == Block.class) {
            toType.writeObject(blockBuilder, coercer.getObject(bridgingRecordCursor, field));
        }
        else {
            throw new PrestoException(NOT_SUPPORTED, format("Unsupported coercion from %s to %s", fromType.getDisplayName(), toType.getDisplayName()));
        }
        coercer.reset();
        bridgingRecordCursor.setValue(null);
    }

    private static class BridgingRecordCursor
            implements RecordCursor
    {
        private Object value;
        private Type type;

        public void setValue(Object value)
        {
            this.value = value;
        }

        public void setType(Type type)
        {
            this.type = requireNonNull(type, "type is null");
        }

        @Override
        public long getCompletedBytes()
        {
            return 0;
        }

        @Override
        public long getReadTimeNanos()
        {
            return 0;
        }

        @Override
        public Type getType(int field)
        {
            return type;
        }

        @Override
        public boolean advanceNextPosition()
        {
            return true;
        }

        @Override
        public boolean getBoolean(int field)
        {
            return (Boolean) value;
        }

        @Override
        public long getLong(int field)
        {
            return (Long) value;
        }

        @Override
        public double getDouble(int field)
        {
            return (Double) value;
        }

        @Override
        public Slice getSlice(int field)
        {
            return (Slice) value;
        }

        @Override
        public Object getObject(int field)
        {
            return value;
        }

        @Override
        public boolean isNull(int field)
        {
            return Objects.isNull(value);
        }

        @Override
        public void close()
        {
        }
    }
}
