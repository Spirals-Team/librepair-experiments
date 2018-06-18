/*
 * Copyright (c) 2018 Shapelets.io
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package io.shapelets.khiva;

import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class DistancesTest {

    private static final double DELTA = 1e-6;

    @Parameters()
    public static Iterable<Object[]> backends() {
        String OS;

        OS = System.getProperty("os.name").toLowerCase();

        if (OS.indexOf("mac") >= 0) {
            return Arrays.asList(new Object[][]{
                    {Array.Backend.KHIVA_BACKEND_CPU}
            });
        } else
            return Arrays.asList(new Object[][]{
                    {Array.Backend.KHIVA_BACKEND_OPENCL}, {Array.Backend.KHIVA_BACKEND_CPU}
            });
    }

    public DistancesTest(Library.Backend back) {
        Library.setKhivaBackend(back);
    }


    @Test
    public void testEuclidean() throws Exception {
        float[] timeSeries = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        long[] dims = {4, 3, 1, 1};
        Array arrayOfTimeSeries = new Array(timeSeries, dims);
        float[] result = Distances.euclidean(arrayOfTimeSeries).getData();
        Assert.assertEquals(result[0], 0, DELTA);
        Assert.assertEquals(result[1], 0, DELTA);
        Assert.assertEquals(result[2], 0, DELTA);
        Assert.assertEquals(result[3], 8, DELTA);
        Assert.assertEquals(result[4], 0, DELTA);
        Assert.assertEquals(result[5], 0, DELTA);
        Assert.assertEquals(result[6], 16, DELTA);
        Assert.assertEquals(result[7], 8, DELTA);
        Assert.assertEquals(result[8], 0, DELTA);
    }

    @Test
    public void testSquaredEuclidean() throws Exception {
        float[] timeSeries = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        long[] dims = {4, 3, 1, 1};
        Array arrayOfTimeSeries = new Array(timeSeries, dims);
        float[] result = Distances.squaredEuclidean(arrayOfTimeSeries).getData();
        Assert.assertEquals(result[0], 0, DELTA);
        Assert.assertEquals(result[1], 0, DELTA);
        Assert.assertEquals(result[2], 0, DELTA);
        Assert.assertEquals(result[3], 64, DELTA);
        Assert.assertEquals(result[4], 0, DELTA);
        Assert.assertEquals(result[5], 0, DELTA);
        Assert.assertEquals(result[6], 256, DELTA);
        Assert.assertEquals(result[7], 64, DELTA);
        Assert.assertEquals(result[8], 0, DELTA);
    }

    @Test
    public void testDwt() throws Exception {
        float[] timeSeries = {1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5};
        long[] dims = {5, 5, 1, 1};
        Array arrayOfTimeSeries = new Array(timeSeries, dims);
        Array resultArray = Distances.dtw(arrayOfTimeSeries);
        float[] result = resultArray.getData();
        float[] expectedResult = {0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 10, 5, 0, 0, 0, 15, 10, 5, 0, 0, 20, 15, 10, 5, 0};
        Assert.assertEquals(expectedResult.length, result.length, DELTA);
        for (int i = 0; i < result.length; i++) {
            Assert.assertEquals(expectedResult[i], result[i], DELTA);
        }
    }

    @Test
    public void testHamming() throws Exception {
        float[] timeSeries = {1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5};
        long[] dims = {5, 5, 1, 1};
        Array a = new Array(timeSeries, dims);
        float[] result = Distances.hamming(a).getData();
        float[] expectedResult = {0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 5, 5, 0, 0, 0, 5, 5, 5, 0, 0, 5, 5, 5, 5, 0};
        Assert.assertEquals(expectedResult.length, result.length, DELTA);
        for (int i = 0; i < result.length; i++) {
            Assert.assertEquals(expectedResult[i], result[i], DELTA);
        }
    }

    @Test
    public void testManhattan() throws Exception {
        float[] timeSeries = {1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5};
        long[] dims = {5, 5, 1, 1};
        Array a = new Array(timeSeries, dims);
        float[] result = Distances.manhattan(a).getData();
        float[] expectedResult = {0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 10, 5, 0, 0, 0, 15, 10, 5, 0, 0, 20, 15, 10, 5, 0};
        Assert.assertEquals(expectedResult.length, result.length, DELTA);
        for (int i = 0; i < result.length; i++) {
            Assert.assertEquals(expectedResult[i], result[i], DELTA);
        }
    }
}