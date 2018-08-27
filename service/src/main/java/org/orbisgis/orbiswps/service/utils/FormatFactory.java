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
package org.orbisgis.orbiswps.service.utils;

import net.opengis.wps._2_0.Format;
import java.util.ArrayList;
import java.util.List;

/**
 * Class creating formats.
 *
 * @author Sylvain PALOMINOS
 **/

public class FormatFactory {

    public static final String SHAPEFILE_EXTENSION = "shp";
    public static final String GEOJSON_EXTENSION = "geojson";
    public static final String SQL_EXTENSION = "sqlTable";
    public static final String WKT_EXTENSION = "wkt";
    public static final String GEOMETRY_EXTENSION = "geometry";
    public static final String TEXT_EXTENSION = "txt";
    public static final String GML_EXTENSION = "gml";
    public static final String XML_EXTENSION = "xml";

    public static final String SHAPEFILE_MIMETYPE = "application/octet-stream";
    public static final String GEOJSON_MIMETYPE = "application/geo+json";
    public static final String SQL_MIMETYPE = "custom/sql";
    public static final String WKT_MIMETYPE = "custom/wkt";
    public static final String TEXT_MIMETYPE = "text/plain";
    public static final String XML_MIMETYPE = "text/xml";
    public static final String GML_MIMETYPE = "application/gml+xml";

    public static final String SHAPEFILE_URI = "https://tools.ietf.org/html/rfc2046";
    public static final String GEOJSON_URI = "https://tools.ietf.org/html/rfc7946";
    public static final String SQL_URI = "";
    public static final String WKT_URI = "";
    public static final String GML_URI = "https://tools.ietf.org/html/rfc7303";
    public static final String OTHER_URI = "";

    public static final String SQL_DESCRIPTION = "SQL table";
    public static final String WKT_DESCRIPTION = "Well-Known Text";

    /**
     * Returns the Format from the given extension. If the extension isn't recognize, return null.
     * @param extension Extension used for the Format generation.
     * @return Format corresponding to the given extension.
     */
    public static Format getFormatFromExtension(String extension){
        Format format = new Format();
        format.setEncoding("simple");
        switch(extension){
            case SHAPEFILE_EXTENSION:
                format.setMimeType(SHAPEFILE_MIMETYPE);
                format.setSchema(SHAPEFILE_URI);
                break;
            case GEOJSON_EXTENSION:
                format.setMimeType(GEOJSON_MIMETYPE);
                format.setSchema(GEOJSON_URI);
                break;
            case SQL_EXTENSION:
                format.setMimeType(SQL_MIMETYPE);
                format.setSchema(SQL_URI);
                break;
            case WKT_EXTENSION:
                format.setMimeType(WKT_MIMETYPE);
                format.setSchema(WKT_URI);
                break;
            case GML_EXTENSION:
                format.setMimeType(GML_MIMETYPE);
                format.setSchema(GML_URI);
                break;
            case XML_EXTENSION:
                format.setMimeType(XML_MIMETYPE);
                format.setSchema(OTHER_URI);
                break;
            default:
                format.setMimeType(TEXT_MIMETYPE);
                format.setSchema(OTHER_URI);
                break;
        }
        return format;
    }

    /**
     * Return a list of Format from a list of extension.
     * @param extensions List of extension for the Format generation.
     * @return A list of Format.
     */
    public static List<Format> getFormatsFromExtensions(List<String> extensions){
        List<Format> formatList = new ArrayList<>();
        for(String extension : extensions){
            formatList.add(getFormatFromExtension(extension));
        }
        return formatList;
    }

    /**
     * Return a list of Format from an array of extension.
     * @param extensions List of extension for the Format generation.
     * @return A list of Format.
     */
    public static List<Format> getFormatsFromExtensions(String[] extensions){
        List<Format> formatList = new ArrayList<>();
        for(String extension : extensions){
            formatList.add(getFormatFromExtension(extension));
        }
        return formatList;
    }

    /**
     * Return the extension from a Format.
     * @param format Format to decode.
     * @return File extension.
     */
    public static String getFormatExtension(Format format){
        switch(format.getMimeType()){
            case SHAPEFILE_MIMETYPE:
                return SHAPEFILE_EXTENSION;
            case GEOJSON_MIMETYPE:
                return GEOJSON_EXTENSION;
            case SQL_MIMETYPE:
                return SQL_DESCRIPTION;
            case WKT_MIMETYPE:
                return WKT_DESCRIPTION;
            case TEXT_MIMETYPE:
                return TEXT_EXTENSION;
            case GML_MIMETYPE:
                return GML_EXTENSION;
            default:
                return format.getMimeType();
        }
    }

    /**
     * Return the well formatted Format corresponding to the given mimeType
     * @param mimeType MimeType of the format.
     * @return The Format corresponding to the given mimeType.
     */
    public static Format getFormatFromMimeType(String mimeType) {
        Format format = new Format();
        format.setEncoding("simple");
        switch(mimeType){
            case SHAPEFILE_MIMETYPE:
                format.setMimeType(SHAPEFILE_MIMETYPE);
                format.setSchema(SHAPEFILE_URI);
                break;
            case GEOJSON_MIMETYPE:
                format.setMimeType(GEOJSON_MIMETYPE);
                format.setSchema(GEOJSON_URI);
                break;
            case SQL_MIMETYPE:
                format.setMimeType(SQL_MIMETYPE);
                format.setSchema(SQL_URI);
                break;
            case WKT_MIMETYPE:
                format.setMimeType(WKT_MIMETYPE);
                format.setSchema(WKT_URI);
                break;
            case GML_MIMETYPE:
                format.setMimeType(GML_MIMETYPE);
                format.setSchema(GML_URI);
                break;
            case XML_MIMETYPE:
                format.setMimeType(XML_MIMETYPE);
                format.setSchema(OTHER_URI);
                break;
            default:
                format.setMimeType(TEXT_MIMETYPE);
                format.setSchema(OTHER_URI);
                break;
        }
        return format;
    }
}
