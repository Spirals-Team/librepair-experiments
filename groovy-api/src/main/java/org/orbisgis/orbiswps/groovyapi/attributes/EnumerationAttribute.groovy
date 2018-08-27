/*
 * OrbisWPS contains a set of libraries to build a Web Processing Service (WPS)
 * compliant with the 2.0 specification.
 *
 * OrbisWPS is part of the OrbisGIS platform
 *
 * OrbisGIS is a java GIS application dedicated to research in GIScience.
 * OrbisGIS is developed by the GIS group of the DECIDE team of the
 * Lab-STICC CNRS laboratory, see <http://www.lab-sticc.fr/>.
 *
 * The GIS group of the DECIDE team is located at :
 *
 * Laboratoire Lab-STICC – CNRS UMR 6285
 * Equipe DECIDE
 * UNIVERSITÉ DE BRETAGNE-SUD
 * Institut Universitaire de Technologie de Vannes
 * 8, Rue Montaigne - BP 561 56017 Vannes Cedex
 *
 * OrbisWPS is distributed under GPL 3 license.
 *
 * Copyright (C) 2015-2017 CNRS (Lab-STICC UMR CNRS 6285)
 *
 *
 * OrbisWPS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * OrbisWPS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * OrbisWPS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.orbisgis.org/>
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.orbisgis.orbiswps.groovyapi.attributes

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Attributes for the Enumeration complex model.
 * The Enumeration complex model represents a selection of values from a predefined list.
 *
 * The following fields must be defined (mandatory) :
 *  - values : String[]
 *      List of possible values.
 *
 * The following fields can be defined (optional) :
 *  - multiSelection : boolean
 *      Allow or not to select more than one value.
 *
 *  - isEditable : boolean
 *      Enable or not the user to use its own value.
 *
 *  - names : String[]
 *      Displayable name of the values. If not specified, use the values as name. The names attribute is composed of
 *      pairs of String : the coma separated list of the names and the language of the names.
 *      i.e.
 *          names = ["name1,name2,name3","en",
 *                   "nom1,nom2,nom3","fr"]
 *
 * @author Sylvain PALOMINOS
 */
@Retention(RetentionPolicy.RUNTIME)
@interface EnumerationAttribute {

    /** Allow or not to select more than one value.*/
    boolean multiSelection() default false

    /** Enable or not the user to use its own value.*/
    boolean isEditable() default false

    /** List of possible values.*/
    String[] values()

    /** Displayable name of the values. If not specified, use the values as name. The names attribute is composed of
     * pairs of String : the coma separated list of the names and the language of the names.
     * i.e.
     *      names = ["name1,name2,name3","en",
     *               "nom1,nom2,nom3","fr"]
     */
    String[] names() default []
}
