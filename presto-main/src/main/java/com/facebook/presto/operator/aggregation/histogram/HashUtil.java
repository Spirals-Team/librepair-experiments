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
package com.facebook.presto.operator.aggregation.histogram;

import static com.google.common.base.Preconditions.checkArgument;
import static it.unimi.dsi.fastutil.HashCommon.arraySize;

public class HashUtil
{
    private HashUtil()
    {
        throw new AssertionError("static util class only, do not try to instantiate");
    }

    public static int nextProbeLinear(int probeCount)
    {
        return probeCount;
    }

    public static int nextSumOfCount(int probeCount)
    {
        return (probeCount * (probeCount + 1)) / 2;
    }

    public static int nextSumOfSquares(int probeCount)
    {
        return (probeCount * (probeCount * probeCount + 1)) / 2;
    }

    /**
     *
     * @param bucketId - previous bucketId location
     * @param mask - mask being used (typically # of buckets+1; due to power-of-2 sized bucket arrays, handles wrap-around
     * @param probe - how many buckets to jump to find next bucket
     * @return next bucketId, including any necessary wrap-around (again mask handles this)
     */
    public static int nextBucketId(int bucketId, int mask, int probe)
    {
        return (bucketId + probe) & mask;
    }

    public static int calculateMaxFill(int bucketCount, float fillRatio)
    {
        checkArgument(bucketCount > 0, "bucketCount must be greater than 0");
        int maxFill = (int) Math.ceil(bucketCount * fillRatio);
        if (maxFill == bucketCount) {
            maxFill--;
        }
        checkArgument(bucketCount > maxFill, "bucketCount must be larger than maxFill");
        return maxFill;
    }

    public static int computeBucketCount(int expectedSize, float fillRatio)
    {
        return arraySize(expectedSize, fillRatio);
    }
}
