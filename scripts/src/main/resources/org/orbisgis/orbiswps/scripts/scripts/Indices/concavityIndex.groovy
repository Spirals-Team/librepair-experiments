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
 * The concavity is equal to the geometry’s area divided by its convex hull’s area.
 *
 * @author Erwan BOCHER (CNRS)
 */
@Process(
    title = "Concavity indice",
    description = "The concavity is equal to the geometry’s area divided by its convex hull’s area.<br>\
            <em>Bibliography:</em><br>\
            L. Adolphe, A simplified model of urban morphology: Application to an analysis of the environmental \
            performance of cities, Environment and Planning B: Planning and Design 28 (2001) 183–200.<br>\
            A. P. d’URbanisme (APUR), Consommations d'énergie et émissions degaz à effet de serre liées au chauffage \
            des résidences principales parisiennes, Technical Report, Atelier Parisien d’URbanisme (APUR), 2007.",
    keywords = ["Vector","Geometry","Morphology"],
    properties = ["DBMS_TYPE", "H2GIS", "DBMS_TYPE", "POSTGIS"],
    version = "1.0",
    identifier = "orbisgis:wps:official:concavityIndice"
)
def processing() {

    //Build the start of the query
    def outputTable = "${inputTable}_concavityindice"

    if (outputTableName != null) {
        outputTable = outputTableName
    }

    def query = "CREATE TABLE ${outputTable} AS SELECT "

    if (keepgeom == true) {
        query += "${geometryColumn[0]},"
    }
    query += "${idField[0]},st_area(${geometryColumn[0]})/st_area(ST_CONVEXHULL(${geometryColumn[0]})) as " +
            "concavityindice from ${inputTable}"


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


