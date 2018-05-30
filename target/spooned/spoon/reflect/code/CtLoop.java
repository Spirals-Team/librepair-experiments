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
package spoon.reflect.code;


/**
 * This abstract code element defines a loop.
 */
public interface CtLoop extends spoon.reflect.code.CtBodyHolder , spoon.reflect.code.CtStatement , spoon.template.TemplateParameter<java.lang.Void> {
    /**
     * Gets the body of this loop.
     */
    @java.lang.Override
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.BODY)
    spoon.reflect.code.CtStatement getBody();

    @java.lang.Override
    spoon.reflect.code.CtLoop clone();
}

