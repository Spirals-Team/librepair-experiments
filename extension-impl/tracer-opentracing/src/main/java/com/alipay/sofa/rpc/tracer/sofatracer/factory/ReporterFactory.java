/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.rpc.tracer.sofatracer.factory;

import com.alipay.common.tracer.core.appender.encoder.SpanEncoder;
import com.alipay.common.tracer.core.reporter.digest.DiskReporterImpl;
import com.alipay.common.tracer.core.reporter.facade.Reporter;
import com.alipay.common.tracer.core.reporter.stat.SofaTracerStatisticReporter;
import com.alipay.common.tracer.core.span.SofaTracerSpan;
import com.alipay.sofa.rpc.common.utils.StringUtils;

/**
 * @author bystander
 * @version $Id: ReporterFactory.java, v 0.1 2018年05月17日 9:00 AM bystander Exp $
 */
public class ReporterFactory {

    private static String REPORT_TYPE = System.getProperty("reporter_type", "DISK");

    public static Reporter build(String digestLog, String digestRollingPolicy,
                                 String digestLogReserveConfig, SpanEncoder<SofaTracerSpan> spanEncoder,
                                 SofaTracerStatisticReporter statReporter) {
        Reporter reporter = null;

        if (StringUtils.equals(REPORT_TYPE, "MEMORY")) {
            //构造实例
            reporter = new MemoryReporterImpl(digestLog, digestRollingPolicy,
                digestLogReserveConfig, spanEncoder, statReporter);
        } else {
            //构造实例
            reporter = new DiskReporterImpl(digestLog, digestRollingPolicy,
                digestLogReserveConfig, spanEncoder, statReporter);
        }
        return reporter;
    }
}