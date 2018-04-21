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

import com.google.common.collect.ImmutableMap;

import javax.annotation.Nullable;

import java.util.Map;
import java.util.OptionalLong;

import static com.google.common.base.MoreObjects.toStringHelper;
import static java.util.Objects.requireNonNull;

public class HiveBasicStatistics
{
    private static final String NUM_FILES = "numFiles";
    private static final String NUM_ROWS = "numRows";
    private static final String RAW_DATA_SIZE = "rawDataSize";
    private static final String TOTAL_SIZE = "totalSize";

    private final OptionalLong fileCount;
    private final OptionalLong rowCount;
    private final OptionalLong rawDataSize;
    private final OptionalLong totalSize;

    public HiveBasicStatistics(long fileCount, long rowCount, long rawDataSize, long totalSize)
    {
        this(OptionalLong.of(fileCount), OptionalLong.of(rowCount), OptionalLong.of(rawDataSize), OptionalLong.of(totalSize));
    }

    public HiveBasicStatistics(
            OptionalLong fileCount,
            OptionalLong rowCount,
            OptionalLong rawDataSize,
            OptionalLong totalSize)
    {
        this.fileCount = requireNonNull(fileCount, "fileCount is null");
        this.rowCount = requireNonNull(rowCount, "rowCount is null");
        this.rawDataSize = requireNonNull(rawDataSize, "rawDataSize is null");
        this.totalSize = requireNonNull(totalSize, "totalSize is null");
    }

    public OptionalLong getFileCount()
    {
        return fileCount;
    }

    public OptionalLong getRowCount()
    {
        return rowCount;
    }

    public OptionalLong getRawDataSize()
    {
        return rawDataSize;
    }

    public OptionalLong getTotalSize()
    {
        return totalSize;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
                .add("fileCount", fileCount)
                .add("rowCount", rowCount)
                .add("rawDataSize", rawDataSize)
                .add("totalSize", totalSize)
                .toString();
    }

    public HiveBasicStatistics add(HiveBasicStatistics that)
    {
        return new HiveBasicStatistics(
                add(this.fileCount, that.fileCount),
                add(this.rowCount, that.rowCount),
                add(this.rawDataSize, that.rawDataSize),
                add(this.totalSize, that.totalSize));
    }

    private OptionalLong add(OptionalLong first, OptionalLong second)
    {
        if (!first.isPresent()) {
            return second;
        }
        else if (!second.isPresent()) {
            return first;
        }
        else {
            return OptionalLong.of(first.getAsLong() + second.getAsLong());
        }
    }

    public Map<String, String> toPartitionParameters()
    {
        ImmutableMap.Builder<String, String> properties = ImmutableMap.builder();
        fileCount.ifPresent(fileCount -> properties.put(NUM_FILES, Long.toString(fileCount)));
        rowCount.ifPresent(fileCount -> properties.put(NUM_ROWS, Long.toString(fileCount)));
        rawDataSize.ifPresent(fileCount -> properties.put(RAW_DATA_SIZE, Long.toString(fileCount)));
        totalSize.ifPresent(fileCount -> properties.put(TOTAL_SIZE, Long.toString(fileCount)));
        return properties.build();
    }

    public static HiveBasicStatistics createFromPartitionParameters(Map<String, String> parameters)
    {
        OptionalLong numFiles = convertStringParameter(parameters.get(NUM_FILES));
        OptionalLong numRows = convertStringParameter(parameters.get(NUM_ROWS));
        OptionalLong rawDataSize = convertStringParameter(parameters.get(RAW_DATA_SIZE));
        OptionalLong totalSize = convertStringParameter(parameters.get(TOTAL_SIZE));
        return new HiveBasicStatistics(numFiles, numRows, rawDataSize, totalSize);
    }

    private static OptionalLong convertStringParameter(@Nullable String parameterValue)
    {
        if (parameterValue == null) {
            return OptionalLong.empty();
        }
        try {
            long longValue = Long.parseLong(parameterValue);
            if (longValue < 0) {
                return OptionalLong.empty();
            }
            return OptionalLong.of(longValue);
        }
        catch (NumberFormatException e) {
            return OptionalLong.empty();
        }
    }
}
