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
package spoon.support.compiler;


/**
 * A filtering resource, see https://github.com/INRIA/spoon/issues/877
 */
public class FilteringFolder extends spoon.support.compiler.VirtualFolder {
    /**
     * Removes all resources matching the given Java regex
     * Eg. resources3.removeIfMatches(".*packageprotected.*");
     */
    public spoon.support.compiler.FilteringFolder removeAllThatMatch(java.lang.String regex) {
        for (spoon.compiler.SpoonResource f : new java.util.ArrayList<>(files)) {
            if (f.getPath().matches(regex)) {
                files.remove(f);
            }
        }
        return this;
    }
}

