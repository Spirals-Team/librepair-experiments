/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.storm.daemon.metrics;

import org.apache.storm.DaemonConfig;
import org.apache.storm.daemon.metrics.reporters.JmxPreparableReporter;
import org.apache.storm.daemon.metrics.reporters.PreparableReporter;
import org.apache.storm.utils.ConfigUtils;
import org.apache.storm.utils.ObjectReader;
import org.apache.storm.utils.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MetricsUtils {
    private final static Logger LOG = LoggerFactory.getLogger(MetricsUtils.class);

    public static List<PreparableReporter> getPreparableReporters(Map<String, Object> topoConf) {
        List<String> clazzes = (List<String>) topoConf.get(DaemonConfig.STORM_DAEMON_METRICS_REPORTER_PLUGINS);
        List<PreparableReporter> reporterList = new ArrayList<>();

        if (clazzes != null) {
            for (String clazz : clazzes) {
                reporterList.add(getPreparableReporter(clazz));
            }
        }
        if (reporterList.isEmpty()) {
            reporterList.add(new JmxPreparableReporter());
        }
        return reporterList;
    }

    private static PreparableReporter getPreparableReporter(String clazz) {
        PreparableReporter reporter = null;
        LOG.info("Using statistics reporter plugin:" + clazz);
        if (clazz != null) {
            reporter = (PreparableReporter) ReflectionUtils.newInstance(clazz);
        }
        return reporter;
    }

    public static Locale getMetricsReporterLocale(Map<String, Object> topoConf) {
        String languageTag = ObjectReader.getString(topoConf.get(DaemonConfig.STORM_DAEMON_METRICS_REPORTER_PLUGIN_LOCALE), null);
        if (languageTag != null) {
            return Locale.forLanguageTag(languageTag);
        }
        return null;
    }

    public static TimeUnit getMetricsRateUnit(Map<String, Object> topoConf) {
        return getTimeUnitForCofig(topoConf, DaemonConfig.STORM_DAEMON_METRICS_REPORTER_PLUGIN_RATE_UNIT);
    }

    public static TimeUnit getMetricsDurationUnit(Map<String, Object> topoConf) {
        return getTimeUnitForCofig(topoConf, DaemonConfig.STORM_DAEMON_METRICS_REPORTER_PLUGIN_DURATION_UNIT);
    }

    private static TimeUnit getTimeUnitForCofig(Map<String, Object> topoConf, String configName) {
        String rateUnitString = ObjectReader.getString(topoConf.get(configName), null);
        if (rateUnitString != null) {
            return TimeUnit.valueOf(rateUnitString);
        }
        return null;
    }

    public static File getCsvLogDir(Map<String, Object> topoConf) {
        String csvMetricsLogDirectory = ObjectReader.getString(topoConf.get(DaemonConfig.STORM_DAEMON_METRICS_REPORTER_CSV_LOG_DIR), null);
        if (csvMetricsLogDirectory == null) {
            csvMetricsLogDirectory = ConfigUtils.absoluteStormLocalDir(topoConf);
            csvMetricsLogDirectory = csvMetricsLogDirectory + ConfigUtils.FILE_SEPARATOR + "csvmetrics";
        }
        File csvMetricsDir = new File(csvMetricsLogDirectory);
        validateCreateOutputDir(csvMetricsDir);
        return csvMetricsDir;
    }

    private static void validateCreateOutputDir(File dir) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (!dir.canWrite()) {
            throw new IllegalStateException(dir.getName() + " does not have write permissions.");
        }
        if (!dir.isDirectory()) {
            throw new IllegalStateException(dir.getName() + " is not a directory.");
        }
    }
}
