/*
 * Copyright (c) 2016, Salesforce.com, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of Salesforce.com nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.salesforce.dva.argus.service.metric.transform;

/**
 * Gaussian-based anomaly detection using probability density estimation.
 * Source: http://www.holehouse.org/mlclass/15_Anomaly_Detection.html (Andrew Ng)
 *
 * @author  Shouvik Mani (shouvik.mani@salesforce.com)
 */
public class AnomalyDetectionGaussianDensityTransform extends AnomalyDetectionGaussianTransform {

    private static final String RESULT_METRIC_NAME = "probability density (neg. log)";

    @Override
    public String getResultScopeName() {
        return TransformFactory.Function.ANOMALY_DENSITY.name();
    }

    @Override
    public String getResultMetricName() {
        return RESULT_METRIC_NAME;
    }

    /**
     * Calculates the probability density (PDF) of the data point, which
     * describes the relative likelihood of the point occurring in the
     * Gaussian distribution.
     *
     * Large variances in data causes floating point underflow during the
     * probability density calculation. Since we cannot take the negative
     * log of 0.0, data points that cause underflow are omitted from the
     * anomaly score results.
     *
     * @param value the value of the data point
     * @return the negative log of the probability density of the data point
     */
    @Override
    public double calculateAnomalyScore(double value) {
        double probabilityDensity = (1.0/Math.sqrt(2.0 * Math.PI * variance)) *
                Math.exp((-1.0 * Math.pow((value - mean), 2.0)) / (2.0 * variance));

        if (probabilityDensity == 0.0) {
            throw new ArithmeticException("Cannot take the log of 0.");
        }

        /**
         * Taking negative log transforms the probability density
         * into a human-readable anomaly score
         */
        return -1.0 * Math.log(probabilityDensity);
    }

}
/* Copyright (c) 2016, Salesforce.com, Inc.  All rights reserved. */
