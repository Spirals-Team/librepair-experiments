/*
 * Copyright (c) 2001-2017, Zoltan Farkas All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * Additionally licensed with:
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.spf4j.tsdb2;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jfree.chart.JFreeChart;
import org.spf4j.base.Arrays;
import org.spf4j.base.Pair;
import static org.spf4j.perf.impl.chart.Charts.fillGaps;
import org.spf4j.tsdb2.avro.ColumnDef;
import org.spf4j.tsdb2.avro.TableDef;

/**
 *
 * @author zoly
 */
public final class Charts {

    private Charts() { }

   public static boolean canGenerateMinMaxAvgCount(final TableDef info) {
       int found = 0;
       for (ColumnDef colDef : info.getColumns()) {
           switch (colDef.getName()) {
               case "min":
               case "max":
               case "total":
               case "count":
                   found++;
                   break;
               default:
           }
       }
       return found >= 4;
    }

    public static boolean canGenerateCount(final TableDef info) {
       int found = 0;
       for (ColumnDef colDef : info.getColumns()) {
           switch (colDef.getName()) {
               case "count":
                   found++;
                   break;
               default:
           }
       }
       return found >= 1;
    }


    @SuppressFBWarnings("STT_STRING_PARSING_A_FIELD")
    public static boolean canGenerateHeatChart(final TableDef info) {
        for (ColumnDef colDef : info.getColumns()) {
            if (colDef.name.startsWith("Q") && colDef.name.contains("_")) {
                return true;
            }
        }
        return false;
    }

    public static JFreeChart createHeatJFreeChart(final File database, final List<TableDef> table, final long startTime,
            final long endTime) throws IOException {

        TimeSeries data = TSDBQuery.getTimeSeries(database, TSDBQuery.getIds(table), startTime, endTime);
        return createHeatJFreeChart(data, table.get(0));
    }

   public static JFreeChart createHeatJFreeChart(final TimeSeries data, final TableDef info) {
        Pair<long[], double[][]> mData = fillGaps(data.getTimeStamps(), data.getValues(),
                info.getSampleTime(), info.getColumns().size());
        ColumnDef columnDef = TSDBQuery.getColumnDef(info, "total");
        return org.spf4j.perf.impl.chart.Charts.createHeatJFreeChart(TSDBQuery.getColumnNames(info),
                mData.getSecond(), data.getTimeStamps()[0], info.getSampleTime(),
                columnDef.getUnitOfMeasurement(), "Measurements distribution for "
                + info.getName() + ", sampleTime " + info.getSampleTime() + "ms, generated by spf4j");
    }

    public static JFreeChart createMinMaxAvgJFreeChart(final TimeSeries data, final TableDef info) {
        long[][] vals = data.getValues();
        double[] min = Arrays.getColumnAsDoubles(vals, TSDBQuery.getColumnIndex(info, "min"));
        double[] max = Arrays.getColumnAsDoubles(vals, TSDBQuery.getColumnIndex(info, "max"));
        final int totalColumnIndex = TSDBQuery.getColumnIndex(info, "total");
        double[] total = Arrays.getColumnAsDoubles(vals, totalColumnIndex);
        double[] count = Arrays.getColumnAsDoubles(vals, TSDBQuery.getColumnIndex(info, "count"));
        for (int i = 0; i < count.length; i++) {
            if (count[i] == 0) {
                min[i] = 0;
                max[i] = 0;
            }
        }
        long[] timestamps = data.getTimeStamps();
        return org.spf4j.perf.impl.chart.Charts.createTimeSeriesJFreeChart("Min,Max,Avg chart for "
                + info.getName() + ", sampleTime " + info.getSampleTime() + "ms, generated by spf4j", timestamps,
                new String[]{"min", "max", "avg"},
                TSDBQuery.getColumnDef(info, "total").getUnitOfMeasurement(),
                new double[][]{min, max, Arrays.divide(total, count)});
    }

    public static JFreeChart createMinMaxAvgJFreeChart(final File database, final List<TableDef> table,
            final long startTime, final long endTime) throws IOException {
        TimeSeries data = TSDBQuery.getTimeSeries(database, TSDBQuery.getIds(table), startTime, endTime);
        return createMinMaxAvgJFreeChart(data, table.get(0));
    }


    public static JFreeChart createCountJFreeChart(final TimeSeries data, final TableDef info) {
        long[][] vals = data.getValues();
        double[] count = Arrays.getColumnAsDoubles(vals, TSDBQuery.getColumnIndex(info, "count"));
        long[] timestamps = data.getTimeStamps();
        return org.spf4j.perf.impl.chart.Charts.createTimeSeriesJFreeChart("count chart for "
                + info.getName() + ", sampleTime " + info.getSampleTime() + " ms, generated by spf4j", timestamps,
                new String[]{"count"}, "count", new double[][]{count});
    }

    public static JFreeChart createCountJFreeChart(final File database, final List<TableDef> info, final long startTime,
            final long endTime) throws IOException {
        TimeSeries data = TSDBQuery.getTimeSeries(database, TSDBQuery.getIds(info), startTime, endTime);
        return createCountJFreeChart(data, info.get(0));
    }


    public static List<JFreeChart> createJFreeCharts(final TimeSeries data, final TableDef info) {
        long[][] vals = data.getValues();
        Map<String, Pair<List<String>, List<double[]>>> measurementsByUom = new HashMap<>();
        //String[] columnMetaData = TSDBQuery.getColumnUnitsOfMeasurement(info);
        int i = 0;
        for (ColumnDef colDef : info.getColumns()) {
            String uom = colDef.getUnitOfMeasurement();
            Pair<List<String>, List<double[]>> meas = measurementsByUom.get(uom);
            if (meas == null) {
                meas = Pair.of((List<String>) new ArrayList<String>(), (List<double[]>) new ArrayList<double[]>());
                measurementsByUom.put(uom, meas);
            }
            meas.getFirst().add(colDef.getName());
            meas.getSecond().add(Arrays.getColumnAsDoubles(vals, i));
            i++;
        }
        long[] timestamps = data.getTimeStamps();
        List<JFreeChart> result = new ArrayList<>(measurementsByUom.size());
        for (Map.Entry<String, Pair<List<String>, List<double[]>>> entry : measurementsByUom.entrySet()) {
            Pair<List<String>, List<double[]>> p = entry.getValue();
            final List<String> measurementNames = p.getFirst();
            final List<double[]> measurements = p.getSecond();
            result.add(org.spf4j.perf.impl.chart.Charts.createTimeSeriesJFreeChart("chart for "
                    + info.getName() + ", sampleTime " + info.getSampleTime()
                    + " ms, generated by spf4j", timestamps,
                    measurementNames.toArray(new String[measurementNames.size()]), entry.getKey(),
                    measurements.toArray(new double[measurements.size()][])));
        }
        return result;
    }


    public static List<JFreeChart> createJFreeCharts(final File database, final List<TableDef> td,
            final long startTime, final long endTime) throws IOException {
        TimeSeries data = TSDBQuery.getTimeSeries(database, TSDBQuery.getIds(td), startTime, endTime);
        return createJFreeCharts(data, td.get(0));
    }

}
