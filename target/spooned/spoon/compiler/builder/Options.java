/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.compiler.builder;


public abstract class Options<T extends spoon.compiler.builder.Options<T>> {
    final java.lang.String COMMA_DELIMITER = ",";

    final java.util.List<java.lang.String> args = new java.util.ArrayList<>();

    final T myself;

    public Options(java.lang.Class<?> type) {
        this.myself = ((T) (type.cast(this)));
    }

    public java.lang.String[] build() {
        return args.toArray(new java.lang.String[args.size()]);
    }

    protected java.lang.String join(java.lang.String delimiter, java.lang.String[] classpath) {
        if ((classpath == null) || ((classpath.length) == 0)) {
            return "";
        }
        final java.lang.StringBuilder builder = new java.lang.StringBuilder();
        for (java.lang.String entry : classpath) {
            builder.append(entry);
            builder.append(delimiter);
        }
        return builder.toString();
    }
}

