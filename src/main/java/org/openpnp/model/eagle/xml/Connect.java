//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference
// Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.10.23 at 08:50:01 AM PDT
//


package org.openpnp.model.eagle.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "connect")
public class Connect {

    @XmlAttribute(name = "gate", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String gate;
    @XmlAttribute(name = "pin", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String pin;
    @XmlAttribute(name = "pad", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String pad;
    @XmlAttribute(name = "route")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String route;

    /**
     * Gets the value of the gate property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getGate() {
        return gate;
    }

    /**
     * Sets the value of the gate property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setGate(String value) {
        this.gate = value;
    }

    /**
     * Gets the value of the pin property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPin() {
        return pin;
    }

    /**
     * Sets the value of the pin property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPin(String value) {
        this.pin = value;
    }

    /**
     * Gets the value of the pad property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPad() {
        return pad;
    }

    /**
     * Sets the value of the pad property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPad(String value) {
        this.pad = value;
    }

    /**
     * Gets the value of the route property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRoute() {
        if (route == null) {
            return "all";
        }
        else {
            return route;
        }
    }

    /**
     * Sets the value of the route property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setRoute(String value) {
        this.route = value;
    }

}
