/*
 * Copyright 2018 EMBL - European Bioinformatics Institute
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
package uk.ac.ebi.eva.accession.dbsnp.model;

import java.util.HashMap;
import java.util.Map;

public enum DbsnpVariantType {

    SNV(1),

    DIV(2),

    HETEROZYGOUS(3),

    MICROSATELLITE(4),

    NAMED(5),

    NO_VARIATION(6),

    MIXED(7),

    MNV(8),

    EXCEPTION(9);

    private final Integer type;

    private static Map<Integer, DbsnpVariantType> integerToEnum = new HashMap<>();

    DbsnpVariantType(Integer type) {
        this.type = type;
    }

    public static DbsnpVariantType getVariantClass(Integer type) {
        return integerToEnum.get(type);
    }

    static {
        for (DbsnpVariantType op : values()) {
            integerToEnum.put(op.type, op);
        }
    }

    public int intValue() {
        return this.type;
    }
}