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

import net.opengis.ows._2.ExceptionReport;
import net.opengis.ows._2.ExceptionType;
import org.h2gis.functions.io.geojson.GeoJsonRead;
import org.h2gis.functions.io.geojson.GeoJsonWrite;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;
import net.opengis.ows._1.BoundingBoxType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xnap.commons.i18n.I18n;
import org.xnap.commons.i18n.I18nFactory;

import javax.sql.DataSource;
import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Class containing methods used to manipulate model for the WPS scripts.
 *
 * @author Sylvain PALOMINOS
 */
public class WpsDataUtils {


    /** I18N object */
    private static final I18n I18N = I18nFactory.getI18n(WpsDataUtils.class);
    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(WpsDataUtils.class);

    /**
     * Convert a BoundingBox string representation into a JTS geometry
     * @param string Geometry string representation.
     * @return A JTS geometry.
     * @throws ParseException
     */
    public static Geometry parseStringToBoundingBox(String string) throws ParseException {
        Geometry geometry;
        String[] split = string.split(";");
        String[] wkt;
        String srid;
        if(split[0].contains(":")){
            srid = string.split(";")[0].split(":")[1];
            wkt = string.split(";")[1].split(",");
        }
        else{
            srid = string.split(";")[1].split(":")[1];
            wkt = string.split(";")[0].split(",");
        }
        if(wkt.length != 4){
            throw new ParseException(I18N.tr("Only 2D bounding boxes are supported yet."));
        }
        String minX, minY, maxX, maxY;
        minX = wkt[0].trim();
        minY = wkt[1].trim();
        maxX = wkt[2].trim();
        maxY = wkt[3].trim();
        //Read the string to retrieve the Geometry
        geometry = new WKTReader().read("POLYGON((" +
                minX+" "+minY+"," +
                maxX+" "+minY+"," +
                maxX+" "+maxY+"," +
                minX+" "+maxY+"," +
                minX+" "+minY+"))");
        geometry.setSRID(Integer.parseInt(srid));
        return geometry;
    }

    /**
     * Convert a OWS 1 2D BoundingBox into a JTS geometry
     * @param bbox Geometry string representation.
     * @return A JTS geometry.
     * @throws ParseException
     */
    public static Geometry parseOws1BoundingBoxToGeometry(BoundingBoxType bbox) throws ParseException {
        Geometry geometry;
        String srid = bbox.getCrs();
        if(srid.contains(":")){
            srid = srid.split(":")[1];
        }
        if(bbox.getDimensions().intValue() != 2){
            throw new ParseException(I18N.tr("Only 2D bounding boxes are supported yet."));
        }
        Double minX, minY, maxX, maxY;
        minX = bbox.getLowerCorner().get(0);
        minY = bbox.getLowerCorner().get(1);
        maxX = bbox.getUpperCorner().get(0);
        maxY = bbox.getUpperCorner().get(1);
        //Read the string to retrieve the Geometry
        geometry = new WKTReader().read("POLYGON((" +
                minX + " " + minY + "," +
                maxX + " " + minY + "," +
                maxX + " " + maxY + "," +
                minX + " " + maxY + "," +
                minX + " " + minY + "))");
        geometry.setSRID(Integer.parseInt(srid));
        return geometry;
    }

    /**
     * Convert a BoundingBox JTS geometry into its string representation.
     * @param geometry BoundingBox JTS Geometry to convert.
     * @return The BoundingBox string representation.
     */
    public static String parseBoundingBoxToString(Geometry geometry) {
        String wkt = new WKTWriter().write(geometry);
        //Update the WKT string to have this pattern : ":SRID;minX,minY,maxX,maxY"
        wkt = wkt.replace("POLYGON ((", "");
        wkt = wkt.replace("))", "");
        String[] split = wkt.split(", ");
        wkt = split[0].replaceAll(" ", ",") + "," + split[2].replaceAll(" ", ",");
        String str = ":" + geometry.getSRID() + ";" + wkt;
        return str;
    }


    /**
     * Convert a GeometryData string representation into a JTS geometry
     * @param string Geometry string representation.
     * @return A JTS geometry.
     * @throws ParseException
     */
    public static Geometry parseStringToGeometry(String string) throws ParseException {
        Geometry geometry = new WKTReader().read(string);
        return geometry;
    }

    /**
     * Convert a JTS geometry into its string representation.
     * @param geometry Jts Geometry to convert.
     * @return The geometry string representation.
     */
    public static String parseGeometryToString(Geometry geometry) {
        String wkt = new WKTWriter().write(geometry);
        return wkt;
    }

    /**
     * Convert a JTS geometry into a OWS 1 2D BoundingBox
     * @param geometry JTS Geometry.
     * @return A OWS 1 2D BoundingBox.
     * @throws ParseException
     */
    public static BoundingBoxType parseGeometryToOws1BoundingBox(Geometry geometry) {
        BoundingBoxType boundingBoxType = new BoundingBoxType();
        boundingBoxType.setCrs("EPSG:"+geometry.getSRID());
        boundingBoxType.setDimensions(new BigInteger("2"));

        String wkt = new WKTWriter().write(geometry);
        wkt = wkt.replace("POLYGON ((", "");
        wkt = wkt.replace("))", "");
        String[] split = wkt.split(", ");

        double dxMax = Double.MIN_VALUE;
        double dyMax = Double.MIN_VALUE;
        double dxMin = Double.MAX_VALUE;
        double dyMin = Double.MAX_VALUE;

        for(String part : split){
            double x = Double.parseDouble(part.split(" ")[0]);
            double y = Double.parseDouble(part.split(" ")[1]);
            dxMax = dxMax>x?dxMax:x;
            dyMax = dyMax>y?dyMax:y;
            dxMin = dxMin<x?dxMin:x;
            dyMin = dyMin<y?dyMin:y;
        }

        boundingBoxType.getUpperCorner().add(dxMax);
        boundingBoxType.getUpperCorner().add(dyMax);

        boundingBoxType.getLowerCorner().add(dxMin);
        boundingBoxType.getLowerCorner().add(dyMin);

        return boundingBoxType;
    }


    /**
     * If there is a format request different than 'text/plain', try to convert the given data into the format. If the
     * conversion fails, return the non converted data.
     * @param data Data ton convert.
     * @param mimeType MimeType of the output.
     * @return The well formatted data.
     */
    public static Object formatOutputData(Object data, String mimeType, DataSource ds, String workspacePath){
        if(mimeType != null && !FormatFactory.TEXT_MIMETYPE.equals(mimeType) && ds != null && data != null) {
            Connection connection = null;
            try {
                connection = ds.getConnection();
            } catch (SQLException e) {
                LOGGER.error("Unable to get a connection to the base to format the output\n" + e.getMessage());
            }
            if (ds == null) {
                LOGGER.error("Unable to get the dataSource to format the output");
            }
            if (connection != null) {
                switch (mimeType) {
                    case FormatFactory.GEOJSON_MIMETYPE:
                        try {
                            File f = new File(workspacePath, data.toString()+".geojson");
                            GeoJsonWrite.writeGeoJson(connection, f.getAbsolutePath(), data.toString());
                            byte[] bytes = Files.readAllBytes(Paths.get(f.getPath()));
                            return new String(bytes, 0, bytes.length);
                        } catch (IOException |SQLException e) {
                            LOGGER.error("Unable to generate the geojson file from the source '"+data+
                                    "\n"+e.getLocalizedMessage());
                        }
                        break;
                    default:
                        return data;
                }
            }
        }
        return data;
    }

    public static Object formatInputData(Object data, String mimeType, DataSource ds){
        if(!FormatFactory.TEXT_MIMETYPE.equals(mimeType) && data != null && ds != null) {
            Connection connection = null;
            try {
                connection = ds.getConnection();
            } catch (SQLException e) {
                LOGGER.error("Unable to get a connection to the base to format the output\n" + e.getMessage());
            }
            if (ds == null) {
                LOGGER.error("Unable to get the dataSource to format the output");
            }
            if (connection != null && mimeType != null) {
                switch (mimeType) {
                    case FormatFactory.GEOJSON_MIMETYPE:
                        try {
                            String name = "TABLE"+UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                            File f = File.createTempFile(name, ".geojson");
                            if(!f.exists() && !f.createNewFile()){
                                LOGGER.error("Unable to create the temporary geojson file");
                                return data;
                            }
                            FileWriter fw = new FileWriter(f);
                            fw.write(data.toString());
                            fw.close();
                            GeoJsonRead.readGeoJson(connection, f.getAbsolutePath(), name);
                            if(!f.delete()){
                                LOGGER.error("Unable to delete temporary created geojson file");
                                return data;
                            }
                            return name;
                        } catch (IOException|SQLException e) {
                            LOGGER.error("Unable to generate the geojson file from the source '"+data+
                                    "\n"+e.getLocalizedMessage());
                            return data;
                        }
                    default:
                        return data;
                }
            }
        }
        return data;
    }


    /**
     * Download and return a web resource pointed by an InputReferenceType.
     *
     * @param href String url of the web resource
     * @param method Request method name (POST or GET)
     * @param maximumMegaBytes Maximum size allowed of the data.
     *
     * @return The web resource.
     */
    //TODO Move this method to an utility class
    //TODO return instead an InputStream to avoid the overload of the memory
    public static Object getReferenceData(String href, String method, String maximumMegaBytes){
        HttpURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL(href);
            connection = (HttpURLConnection) url.openConnection();
            if(method != null) {
                connection.setRequestMethod(method);
            }
            connection.setRequestProperty("Content-Length", maximumMegaBytes);
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception ignore) {
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        //If the reference is not a valid URL, try to load it as an URI
        if (connection == null) {
            URI uri = URI.create(href);
            try {
                BufferedReader rd = new BufferedReader(new FileReader(new File(uri)));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                return response.toString();
            }
            catch(Exception ignored){
                ExceptionType exceptionType = new ExceptionType();
                exceptionType.setExceptionCode("InvalidParameterValue");
                exceptionType.setLocator("DataInputs");
                exceptionType.getExceptionText().add("Unable to get the data from the reference." +
                        " It seems to be an invalid URL/URI\n");
                LOGGER.error("Unable to get the data from the reference. It seems to be an invalid URL/URI\n");
                ExceptionReport report = new ExceptionReport();
                report.getException().add(exceptionType);
                return report;
            }
        }
        ExceptionType exceptionType = new ExceptionType();
        exceptionType.setExceptionCode("InvalidParameterValue");
        exceptionType.setLocator("DataInputs");
        exceptionType.getExceptionText().add("Unable to get the data from the reference." +
                " It seems to be an invalid URL/URI\n");
        LOGGER.error("Unable to get the data from the reference. It seems to be an invalid URL/URI\n");
        ExceptionReport report = new ExceptionReport();
        report.getException().add(exceptionType);
        return report;
    }
}
