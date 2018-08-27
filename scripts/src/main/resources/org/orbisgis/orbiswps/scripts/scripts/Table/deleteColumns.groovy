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
 * Copyright (C) 2015-2018 CNRS (Lab-STICC UMR CNRS 6285)
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
package org.orbisgis.orbiswps.scripts.scripts.Table

import org.orbisgis.orbiswps.groovyapi.input.*
import org.orbisgis.orbiswps.groovyapi.output.*
import org.orbisgis.orbiswps.groovyapi.process.*

/**
 * This process deletes the given columns from the given table.
 * The user has to specify (mandatory):
 *  - The input table
 *  - The column to delete
 *
 * @author Erwan BOCHER (CNRS)
 * @author Sylvain PALOMINOS (UBS 2018)
 */
@Process(
        title = "Delete columns",
        description = "Delete columns from a table.",
        keywords = ["Table","Delete"],
        properties = ["DBMS_TYPE", "H2GIS", "DBMS_TYPE", "POSTGIS"],
        version = "1.0",
        identifier = "orbisgis:wps:official:deleteColumns"
)
def processing() {
    //Build the start of the query
    for (columnName in columnNames) {
        def query = "ALTER TABLE ${tableName} DROP COLUMN `${columnName}`"
        //Execute the query
        sql.execute(query.toString())
    }
    literalOutput = i18n.tr("Deletion done.")
}


/****************/
/** INPUT Data **/
/****************/

/** This JDBCTable is the input model source table. */
@JDBCTableInput(
        title = "Table",
        description = "The table to edit.",
        identifier = "tableName"
)
String tableName

/**********************/
/** INPUT Parameters **/
/**********************/

/** Name of the columns of the JDBCTable tableName to remove. */
@JDBCColumnInput(
        title = "Columns",
        description = "The columns to remove names.",
        jdbcTableReference = "tableName",
        identifier = "columnNames"
)
String[] columnNames


/*****************/
/** OUTPUT Data **/
/*****************/

@LiteralDataOutput(
        title = "Output message",
        description = "The output message.",
        identifier = "literalOutput")
String literalOutput

