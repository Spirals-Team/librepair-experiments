/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
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
package com.alibaba.csp.sentinel.node.metric;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.alibaba.csp.sentinel.util.PidUtil;
import com.alibaba.csp.sentinel.config.SentinelConfig;
import com.alibaba.csp.sentinel.log.RecordLog;

/**
 * This class is responsible for writing {@link MetricNode} to disk:
 * <ol>
 * <li>metric with the same second should write to the same file;</li>
 * <li>single file size must be controlled;</li>
 * <li>file name is like: {@code ${AppName}_pid-metrics.log.yyyy-MM-dd.[number]}</li>
 * <li>metric of different day should in different file;</li>
 * <li>every metric file is accompanied with an index file, which file name is {@code ${metricFileName}.idx}</li>
 * </ol>
 *
 * @author leyou
 */
public class MetricWriter {

    private static final String CHARSET = SentinelConfig.charset();
    public static final String METRIC_BASE_DIR = RecordLog.getLogBaseDir();
    public static final String METRIC_FILE_SUFFIX = "-metrics.log";
    public static final String METRIC_FILE_INDEX_SUFFIX = ".idx";
    public static final Comparator<String> METRIC_FILENAME_CMP = new MetricFileNameComparator();

    private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 排除时差干扰
     */
    private long timeSecondBase;
    private final StringBuilder sb = new StringBuilder(32);
    private String baseDir;
    private String baseFileName;
    /**
     * file must exist when writing
     */
    private File curMetricFile;
    private File curMetricIndexFile;

    private FileOutputStream outMetric;
    private DataOutputStream outIndex;
    private BufferedOutputStream outMetricBuf;
    private long singleFileSize;
    private int totalFileCount;
    private boolean append = false;
    private final int pid = PidUtil.getPid();

    /**
     * 秒级统计，忽略毫秒数。
     */
    private long lastSecond = -1;

    public MetricWriter(long singleFileSize) {
        this(singleFileSize, 6);
    }

    public MetricWriter(long singleFileSize, int totalFileCount) {
        if (singleFileSize <= 0 || totalFileCount <= 0) {
            throw new IllegalArgumentException();
        }
        RecordLog.info("new MetricWriter, singleFileSize=" + singleFileSize + ", totalFileCount=" + totalFileCount);
        this.baseDir = METRIC_BASE_DIR;
        File dir = new File(baseDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        long time = System.currentTimeMillis();
        this.lastSecond = time / 1000;
        this.singleFileSize = singleFileSize;
        this.totalFileCount = totalFileCount;
        try {
            this.timeSecondBase = df.parse("1970-01-01 00:00:00").getTime() / 1000;
        } catch (Exception e) {
            RecordLog.info("new MetricWriter error: ", e);
        }
    }

    /**
     * 如果传入了time，就认为nodes中所有的时间时间戳都是time.
     *
     * @param time
     * @param nodes
     */
    public synchronized void write(long time, List<MetricNode> nodes) throws Exception {
        if (nodes == null) {
            return;
        }
        for (MetricNode node : nodes) {
            node.setTimestamp(time);
        }

        String appName = SentinelConfig.getAppName();
        if (appName == null) {
            appName = "";
        }
        // first write, should create file
        if (curMetricFile == null) {
            baseFileName = formMetricFileName(appName, pid);
            closeAndNewFile(nextFileNameOfDay(time));
        }
        if (!(curMetricFile.exists() && curMetricIndexFile.exists())) {
            closeAndNewFile(nextFileNameOfDay(time));
        }

        long second = time / 1000;
        if (second < lastSecond) {
            // 时间靠前的直接忽略，不应该发生。
        } else if (second == lastSecond) {
            for (MetricNode node : nodes) {
                outMetricBuf.write(node.toFatString().getBytes(CHARSET));
            }
            outMetricBuf.flush();
            if (!validSize()) {
                closeAndNewFile(nextFileNameOfDay(time));
            }
        } else {
            writeIndex(second, outMetric.getChannel().position());
            if (isNewDay(lastSecond, second)) {
                closeAndNewFile(nextFileNameOfDay(time));
                for (MetricNode node : nodes) {
                    outMetricBuf.write(node.toFatString().getBytes(CHARSET));
                }
                outMetricBuf.flush();
                if (!validSize()) {
                    closeAndNewFile(nextFileNameOfDay(time));
                }
            } else {
                for (MetricNode node : nodes) {
                    outMetricBuf.write(node.toFatString().getBytes(CHARSET));
                }
                outMetricBuf.flush();
                if (!validSize()) {
                    closeAndNewFile(nextFileNameOfDay(time));
                }
            }
            lastSecond = second;
        }
    }

    public synchronized void close() throws Exception {
        if (outMetricBuf != null) {
            outMetricBuf.close();
        }
        if (outIndex != null) {
            outIndex.close();
        }
    }

    private void writeIndex(long time, long offset) throws Exception {
        outIndex.writeLong(time);
        outIndex.writeLong(offset);
        outIndex.flush();
    }

    private String nextFileNameOfDay(long time) {
        List<String> list = new ArrayList<String>();
        File baseFile = new File(baseDir);
        DateFormat fileNameDf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = fileNameDf.format(new Date(time));
        for (File file : baseFile.listFiles()) {
            if (file.getName().contains(baseFileName + "." + dateStr)
                && !file.getName().endsWith(METRIC_FILE_INDEX_SUFFIX)
                && !file.getName().endsWith(".lck")) {
                list.add(file.getAbsolutePath());
            }
        }
        Collections.sort(list, METRIC_FILENAME_CMP);
        if (list.isEmpty()) {
            return baseDir + baseFileName + "." + dateStr;
        }
        String last = list.get(list.size() - 1);
        int n = 0;
        String[] strs = last.split("\\.");
        if (strs.length > 0 && strs[strs.length - 1].matches("[0-9]{1,10}")) {
            n = Integer.parseInt(strs[strs.length - 1]);
        }
        return baseDir + baseFileName + "." + dateStr + "." + (n + 1);
    }

    /**
     * A comparator for metric file name. Metric file name is like: <br/>
     * <pre>
     * aliswitch-8728-metrics.log.2018-03-06
     * aliswitch-8728-metrics.log.2018-03-07
     * aliswitch-8728-metrics.log.2018-03-07.10
     * aliswitch-8728-metrics.log.2018-03-06.100
     * </pre>
     * <p>
     * File name with the early date is smaller, if date is same, the one with the small file number is smaller.
     * Note that if the name is an absolute path, only the fileName({@link File#getName()}) part will be considered.
     * So the above file names should be sorted as: <br/>
     * <pre>
     * aliswitch-8728-metrics.log.2018-03-06
     * aliswitch-8728-metrics.log.2018-03-06.100
     * aliswitch-8728-metrics.log.2018-03-07
     * aliswitch-8728-metrics.log.2018-03-07.10
     * </pre>
     * </p>
     */
    private static final class MetricFileNameComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            String name1 = new File(o1).getName();
            String name2 = new File(o2).getName();
            String dateStr1 = name1.split("\\.")[2];
            String dateStr2 = name2.split("\\.")[2];

            // compare date first
            int t = dateStr1.compareTo(dateStr2);
            if (t != 0) {
                return t;
            }

            // same date, compare file number
            t = name1.length() - name2.length();
            if (t != 0) {
                return t;
            }
            return name1.compareTo(name2);
        }
    }

    /**
     * Get all metric files' name in {@code baseDir}. The file name must contain {@code baseFileName}
     * and not endsWith {@link #METRIC_FILE_INDEX_SUFFIX} or ".lck".
     *
     * @param baseDir      the directory to search.
     * @param baseFileName the file name pattern.
     * @return the metric files' absolute path({@link File#getAbsolutePath()})
     * @throws Exception
     */
    static List<String> listMetricFiles(String baseDir, String baseFileName) throws Exception {
        List<String> list = new ArrayList<String>();
        File baseFile = new File(baseDir);
        File[] files = baseFile.listFiles();
        if (files == null) {
            return list;
        }
        for (File file : files) {
            if (file.isFile()
                && file.getName().contains(baseFileName)
                && !file.getName().endsWith(MetricWriter.METRIC_FILE_INDEX_SUFFIX)
                && !file.getName().endsWith(".lck")) {
                list.add(file.getAbsolutePath());
            }
        }
        Collections.sort(list, MetricWriter.METRIC_FILENAME_CMP);
        return list;
    }

    private void removeMoreFiles() throws Exception {
        List<String> list = listMetricFiles(baseDir, baseFileName);
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int i = 0; i < list.size() - totalFileCount + 1; i++) {
            String fileName = list.get(i);
            String indexFile = formIndexFileName(fileName);
            new File(fileName).delete();
            RecordLog.info("remove metric file: " + fileName);
            new File(indexFile).delete();
            RecordLog.info("remove metric index file: " + indexFile);
        }
    }

    private void closeAndNewFile(String fileName) throws Exception {
        removeMoreFiles();
        if (outMetricBuf != null) {
            outMetricBuf.close();
        }
        if (outIndex != null) {
            outIndex.close();
        }
        outMetric = new FileOutputStream(fileName, append);
        outMetricBuf = new BufferedOutputStream(outMetric);
        curMetricFile = new File(fileName);
        String idxFile = formIndexFileName(fileName);
        ;
        curMetricIndexFile = new File(idxFile);
        outIndex = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(idxFile, append)));
        RecordLog.info("create new metric file: " + fileName);
        RecordLog.info("create new metric index file: " + idxFile);
    }

    private boolean validSize() throws Exception {
        long size = outMetric.getChannel().size();
        return size < singleFileSize;
    }

    private boolean isNewDay(long lastSecond, long second) {
        long lastDay = (lastSecond - timeSecondBase) / 86400;
        long newDay = (second - timeSecondBase) / 86400;
        return newDay > lastDay;
    }

    /**
     * Form metric file name use the specific appName and pid. Not that only
     * form the file name, not include path.
     *
     * @param appName
     * @param pid
     * @return metric file name.
     */
    public static String formMetricFileName(String appName, int pid) {
        if (appName == null) {
            appName = "";
        }
        return appName + "-" + pid + METRIC_FILE_SUFFIX;
    }

    /**
     * Form index file name of the {@code metricFileName}
     *
     * @param metricFileName
     * @return the index file name of the metricFileName
     */
    public static String formIndexFileName(String metricFileName) {
        return metricFileName + METRIC_FILE_INDEX_SUFFIX;
    }
}
