/**
 * Copyright 2018 OceanDataExplorer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ode.hadoop.io;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.TwoDArrayWritable;

/**
 * Explicitly extend TwoDArrayWritable to provide Array[Array[Double]] writable.
 * Used in [[WavPcmInputFormat]] and [[WavPcmRecordReader]] to store signal
 * chunks by channel.
 *
 * @author Joseph Allemandou
 */

public class TwoDDoubleArrayWritable extends TwoDArrayWritable {

    public TwoDDoubleArrayWritable() {
        super(DoubleWritable.class);
    }

}
