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
package org.orbisgis.orbiswps.scripts.scripts.Select

import org.orbisgis.orbiswps.groovyapi.input.*
import org.orbisgis.orbiswps.groovyapi.output.*
import org.orbisgis.orbiswps.groovyapi.process.*
import org.h2gis.utilities.SFSUtilities
import org.h2gis.utilities.TableLocation

/**
 * Select rows from one table based on one column value. SQL expression could be used to custom the select.
 *
 * @author Erwan BOCHER (CNRS)
 */
@Process(
    title = "Attribut selection",
    description = "Select rows from one table based on one column value. SQL expression could be used to custom the \
            select. ",
	keywords = "Filtering",
    properties = ["DBMS_TYPE", "H2GIS", "DBMS_TYPE", "POSTGIS"],
    version = "1.0",
    identifier = "orbisgis:wps:official:selectAttribute"
)
def processing() {

    //Build the start of the query
    def outputTable = "${fromSelectedTable}_filtered"

    if (outputTableName != null) {
        outputTable = outputTableName
    }

    def query = "CREATE TABLE ${outputTable} AS SELECT a.* from ${fromSelectedTable} as a  where " +
            "${fromSelectedColumn[0]} ${operation[0]} ${fromSelectedValue}"

    if (dropTable) {
        sql.execute("drop table if exists ${outputTable}".toString())
    }
    //Execute the query
    sql.execute(query.toString())

    outputTable = outputTableName
}

/****************/
/** INPUT Data **/
/****************/

@JDBCTableInput(
    title = "Table to select from",
    description = "The spatial model source that contains the selected features.")
String fromSelectedTable


@JDBCColumnInput(
    title = "Geometric column from",
    description = "The geometric column of selected table.",
    jdbcTableReference = "fromSelectedTable",
    excludedTypes = ["GEOMETRY"]
)
String[] fromSelectedColumn


@EnumerationInput(
    title = "Operator",
    description = "Operator to select rows.",
	values=["=", ">",">=", "<", "<=","<>", "limit", "in", "not in", "like"],
    names=["Equal to","Greater than","Greater than or equal to","Less than","Less than or equal to","Not equal to",
            "Limit","In","Not In","Like"])
String[] operation = ["="]

@LiteralDataInput(
    title = "Value",
    description = "Value to select or any syntax supported by SQL as function on value.")
String fromSelectedValue



@LiteralDataInput(
    title = "Drop the output table if exists",
    description = "Drop the output table if exists.",
    minOccurs = 0)
Boolean dropTable 

@LiteralDataInput(
    title = "Output table name",
    description = "Name of the table containing the result of the process.",
    minOccurs = 0,
    identifier = "outputTableName"
)
String outputTableName


/*****************/
/** OUTPUT Data **/
/*****************/

@JDBCTableOutput(
        title = "output table",
        description = "Table that contains the output.",
        identifier = "outputTable")
String outputTable


