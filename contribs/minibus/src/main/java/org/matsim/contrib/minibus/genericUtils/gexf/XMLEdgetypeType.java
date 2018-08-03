//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.09.19 at 03:18:45 PM MESZ 
//


package org.matsim.contrib.minibus.genericUtils.gexf;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for edgetype-type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="edgetype-type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="directed"/>
 *     &lt;enumeration value="undirected"/>
 *     &lt;enumeration value="mutual"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "edgetype-type")
@XmlEnum
public enum XMLEdgetypeType {

    @XmlEnumValue("directed")
    DIRECTED("directed"),
    @XmlEnumValue("undirected")
    UNDIRECTED("undirected"),
    @XmlEnumValue("mutual")
    MUTUAL("mutual");
    private final String value;

    XMLEdgetypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static XMLEdgetypeType fromValue(String v) {
        for (XMLEdgetypeType c: XMLEdgetypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
