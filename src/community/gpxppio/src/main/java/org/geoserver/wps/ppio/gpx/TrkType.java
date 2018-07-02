//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference
// Implementation, v2.0-hudson-3037-ea3
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2007.07.27 at 11:06:51 PM CDT
//
package org.geoserver.wps.ppio.gpx;

import java.util.ArrayList;
import java.util.List;

/**
 * trk represents a track - an ordered list of points describing a path.
 *
 * <p>Java class for trkType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="trkType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cmt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="desc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="src" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="link" type="{http://www.topografix.com/GPX/1/1}linkType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="extensions" type="{http://www.topografix.com/GPX/1/1}extensionsType" minOccurs="0"/>
 *         &lt;element name="trkseg" type="{http://www.topografix.com/GPX/1/1}trksegType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
public class TrkType {
    protected String name;

    protected String cmt;

    protected String desc;

    protected String src;

    protected List<LinkType> link;

    protected int number;

    protected String type;

    protected ExtensionsType extensions;

    protected List<TrksegType> trkseg;

    /** Gets the value of the name property. */
    public String getName() {
        return name;
    }

    /** Sets the value of the name property. */
    public void setName(String value) {
        this.name = value;
    }

    /** Gets the value of the cmt property. */
    public String getCmt() {
        return cmt;
    }

    /** Sets the value of the cmt property. */
    public void setCmt(String value) {
        this.cmt = value;
    }

    /** Gets the value of the desc property. */
    public String getDesc() {
        return desc;
    }

    /** Sets the value of the desc property. */
    public void setDesc(String value) {
        this.desc = value;
    }

    /** Gets the value of the src property. */
    public String getSrc() {
        return src;
    }

    /** Sets the value of the src property. */
    public void setSrc(String value) {
        this.src = value;
    }

    /**
     * Gets the value of the link property.
     *
     * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the link property.
     *
     * <p>For example, to add a new item, do as follows:
     *
     * <pre>
     * getLink().add(newItem);
     * </pre>
     *
     * <p>Objects of the following type(s) are allowed in the list {@link LinkType }
     */
    public List<LinkType> getLink() {
        if (link == null) {
            link = new ArrayList<LinkType>();
        }

        return this.link;
    }

    /** Gets the value of the number property. */
    public int getNumber() {
        return number;
    }

    /** Sets the value of the number property. */
    public void setNumber(int value) {
        this.number = value;
    }

    /** Gets the value of the type property. */
    public String getType() {
        return type;
    }

    /** Sets the value of the type property. */
    public void setType(String value) {
        this.type = value;
    }

    /** Gets the value of the extensions property. */
    public ExtensionsType getExtensions() {
        return extensions;
    }

    /** Sets the value of the extensions property. */
    public void setExtensions(ExtensionsType value) {
        this.extensions = value;
    }

    /**
     * Gets the value of the trkseg property.
     *
     * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the trkseg property.
     *
     * <p>For example, to add a new item, do as follows:
     *
     * <pre>
     * getTrkseg().add(newItem);
     * </pre>
     *
     * <p>Objects of the following type(s) are allowed in the list {@link TrksegType }
     */
    public List<TrksegType> getTrkseg() {
        if (trkseg == null) {
            trkseg = new ArrayList<TrksegType>();
        }

        return this.trkseg;
    }
}
