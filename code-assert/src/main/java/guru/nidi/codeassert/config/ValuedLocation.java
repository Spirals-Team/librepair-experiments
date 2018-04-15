/*
 * Copyright © 2015 Stefan Niederhauser (nidin@gmx.ch)
 *
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
package guru.nidi.codeassert.config;

public class ValuedLocation {
    final String pack;
    final String clazz;
    final double[] values;
    final double[] appliedLimits;

    public ValuedLocation(String pack, String clazz, double[] values) {
        this.pack = pack;
        this.clazz = clazz;
        this.values = values;
        appliedLimits = new double[values.length];
    }

    public String getPack() {
        return pack;
    }

    public String getClazz() {
        return clazz;
    }

    public double[] getValues() {
        return values;
    }

    public double[] getAppliedLimits() {
        return appliedLimits;
    }
}
