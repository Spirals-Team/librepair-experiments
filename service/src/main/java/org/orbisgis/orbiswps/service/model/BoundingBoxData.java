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
package org.orbisgis.orbiswps.service.model;

import net.opengis.wps._2_0.ComplexDataType;
import net.opengis.wps._2_0.Format;

import javax.xml.bind.annotation.*;
import java.util.List;

import org.orbisgis.orbiswps.serviceapi.model.MalformedScriptException;
import org.xnap.commons.i18n.I18n;
import org.xnap.commons.i18n.I18nFactory;

/**
 * Object representing a bounding box.
 *
 * @author Sylvain PALOMINOS
 * @author Erwan Bocher
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BoundingBoxData",
        propOrder = {"defaultCrs", "supportedCrs", "dimension", "defaultValue"})
public class BoundingBoxData extends ComplexDataType {

    /** Default CRS of the BoundingBox. Should be a string with the pattern : authority:code, like EPSG:2000.*/
    @XmlAttribute(name = "defaultCrs", namespace = "http://orbisgis.org")
    private String defaultCrs;
    /** List of CRS supported by the BoundingBox model without the default one. Should be a string with the pattern :
     *      authority:code, like EPSG:2000.*/
    @XmlAttribute(name = "supportedCrs", namespace = "http://orbisgis.org")
    String[] supportedCrs;
    /** Dimension of the bounding box.*/
    @XmlAttribute(name = "dimension", namespace = "http://orbisgis.org")
    private int dimension;
    /** Default value.*/
    @XmlAttribute(name = "defaultValue", namespace = "http://orbisgis.org")
    private String defaultValue;
    
    private static final I18n I18N = I18nFactory.getI18n(BoundingBoxData.class);

    /**
     * Main constructor.
     * @param formatList Formats of the model accepted.
     * @param supportedCrs List of CRS supported by the BoundingBox model without the default one.
     * @param dimension Dimension of the bounding box.
     * @throws MalformedScriptException
     */
    public BoundingBoxData(List<Format> formatList, String[] supportedCrs, int dimension)
            throws MalformedScriptException {
        format = formatList;
        this.supportedCrs = supportedCrs;
        if(dimension != 2 && dimension != 3){
            throw new MalformedScriptException(BoundingBoxData.class, "dimension",  I18N.tr("dimension should be 2 or 3"));
        }
        if(dimension == 3){
            throw new MalformedScriptException(BoundingBoxData.class, "dimension",  I18N.tr("3D Bounding Box is not supported yet."));
        }
        this.dimension = dimension;
    }

    /**
     * Protected empty constructor used in the ObjectFactory class for JAXB.
     */
    protected BoundingBoxData(){
        super();
    }

    /**
     * Sets the default value.
     * @param defaultValue The default value.
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Returns the default value.
     * @return The default value.
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the default CRS.
     * @param defaultCrs The default CRS.
     */
    public void setDefaultCrs(String defaultCrs) {
        this.defaultCrs = defaultCrs;
    }

    /**
     * Returns the default CRS.
     * @return The default CRS.
     */
    public String getDefaultCrs() {
        return defaultCrs;
    }

    /**
     * Sets the list of the supported CRS.
     * @param supportedCrs The list of the supported CRS.
     */
    public void setSupportedCrs(String[] supportedCrs) {
        this.supportedCrs = supportedCrs;
    }

    /**
     * Returns the list of the supported CRS.
     * @return The list of the supported CRS.
     */
    public String[] getSupportedCrs() {
        return supportedCrs;
    }

    /**
     * Sets the dimension od the BoundingBox.
     * @param dimension The dimension of the BoundingBox.
     */
    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    /**
     * Returns the dimension of the BoundingBox.
     * @return The dimension of the BoundingBox.
     */
    public int getDimension() {
        return dimension;
    }
}
