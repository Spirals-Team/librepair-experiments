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
package org.orbisgis.orbiswps.scripts.scripts.Import

import org.orbisgis.orbiswps.groovyapi.input.*
import org.orbisgis.orbiswps.groovyapi.output.*
import org.orbisgis.orbiswps.groovyapi.process.*

/**
 * Import the OSM file 'fileDataInput' into the table 'inputTable'.
 * If 'dropTable' set to true, drop the table 'jdbcTableOutput'.
 * The table is returned as the output 'jdbcTableOutput'.
 *
 * @author Erwan BOCHER (CNRS)
 */
@Process(title = "Import a OSM file",
    description = "Import a OSM file from path and creates several tables prefixed by tableName representing the\
 file’s contents.<br> Please go to  http://www.h2gis.org",
    keywords = ["OrbisGIS","Import","File","OSM"],
    properties = ["DBMS_TYPE","H2GIS"],
    version = "1.0")
def processing() {
    def fileData = new File(fileDataInput[0])
    def name = fileData.getName()
    def tableName = name.substring(0, name.lastIndexOf(".")).toUpperCase()
    def query = "CALL OSMRead('${fileData.absolutePath}','"
    if (jdbcTableOutputName != null) {
        tableName = jdbcTableOutputName
    }

    query += "${tableName}')"

    sql.execute(query.toString())

    outputJDBCTable = tableName
}


@RawDataInput(
    title = "Input OSM",
    description = "The input OSM file to be imported.",
    fileTypes = ["osm", "osm.gz", "osm.bz2"],
    isDirectory = false)
String[] fileDataInput



/** Optional table name. */
@LiteralDataInput(
    title = "Prefix for all tables",
    description = "Prefix for all table names to store the OSM file.",
    minOccurs = 0)
String jdbcTableOutputName


/*****************/
/** OUTPUT Data **/
/*****************/

@JDBCTableOutput(
        title = "output table",
        description = "Table that contains the output.",
        identifier = "outputTable")
String outputJDBCTable
