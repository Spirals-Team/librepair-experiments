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
package org.orbisgis.orbiswps.scripts.scripts.Indices

import org.orbisgis.orbiswps.groovyapi.input.*
import org.orbisgis.orbiswps.groovyapi.output.*
import org.orbisgis.orbiswps.groovyapi.process.*
import org.h2gis.utilities.SFSUtilities
import org.h2gis.utilities.TableLocation

/**
 * Compute the fractal dimension of a polygon.
 *
 * @author Erwan BOCHER (CNRS)
 */
@Process(
    title = "Fractal dimension index",
    description = "Compute the fractal dimension of a polygon. <p><em>Bibliography:</em><br>\
            Herold, M., Scepan, J., and Clarke, K. C. (2002). The use of remote sensing and landscape metrics to \
            describe structures and changes in urban land uses. Environment and Planning A, 34(8):1443–1458.<br>\
            McGarigal, K. and Marks, B. J. (1995). Fragstats: spatial pattern analysis program for quantifying \
            landscape structure. Gen. Tech. Rep. PNW-GTR-351. Portland, OR: U.S. Department of Agriculture, Forest \
            Service, Pacific Northwest Research Station. 122 p.",
	keywords = ["Vector","Geometry","Morphology"],
    properties = ["DBMS_TYPE", "H2GIS", "DBMS_TYPE", "POSTGIS"],
    version = "1.0",
    identifier = "orbisgis:wps:official:fractalDimension"
)
def processing() {

    //Build the start of the query
    def outputTable = "${inputTable}_fractaldimensionIndex"

    if (outputTableName != null) {
        outputTable = outputTableName
    }

    def query = "CREATE TABLE ${outputTable} AS SELECT "

    if (keepgeom == true) {
        query += "${geometryColumn[0]},"
    }
    query += "${idField[0]},(2 * LOG ( ST_PERIMETER (${geometryColumn[0]}) ) ) / " +
            "LOG (ST_AREA (${geometryColumn[0]}) ) as fractaldim from ${inputTable}"

    if (dropTable) {
        sql.execute("drop table if exists ${outputTable}".toString())
    }
    //Execute the query
    sql.execute(query.toString())

    outputJDBCTable = outputTableName
}

/****************/
/** INPUT Data **/
/****************/

@JDBCTableInput(
    title = "Input table",
    description = "The spatial model source that contains the polygons.",
    dataTypes = ["POLYGON", "MULTIPOLYGON"]
)
String inputTable


@JDBCColumnInput(
    title = "Geometric column",
    description = "The geometric column of input table.",
    jdbcTableReference = "inputTable",
    dataTypes = ["POLYGON", "MULTIPOLYGON"]
)
String[] geometryColumn

/** Name of the identifier field of the JDBCTable inputTable. */
@JDBCColumnInput(
    title = "Column identifier",
    description = "A column used as an identifier.",
    excludedTypes=["GEOMETRY"],
    multiSelection = false,
    jdbcTableReference = "inputTable")
String[] idField

@LiteralDataInput(
    title = "Keep the geometry",
    description = "Keep the input geometry in the result table.",
    minOccurs = 0)
Boolean keepgeom;


@LiteralDataInput(
    title = "Drop the output table if exists",
    description = "Drop the output table if exists.",
    minOccurs = 0)
Boolean dropTable 

@LiteralDataInput(
    title = "Output table prefix",
    description = "Prefix of the table containing the result of the process.",
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
String outputJDBCTable


