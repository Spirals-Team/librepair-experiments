//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.09.20 at 07:20:42 PM MESZ 
//


package org.matsim.jaxb.lightsignalsystemsconfig10;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *     This type is used to configure the signal groups, i.e. which signal groups are controlled together. Furthermore
 *     the type of controlling applied to this group can be set. In case of a plan based controlling the plans
 *     can be specified.
 *     
 * 
 * <p>Java class for lightSignalSystemConfigurationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="lightSignalSystemConfigurationType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.matsim.org/files/dtd}idRefType">
 *       &lt;sequence>
 *         &lt;element name="lightSignalSystemControlInfo" type="{http://www.matsim.org/files/dtd}lightSignalSystemControlInfoType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lightSignalSystemConfigurationType", propOrder = {
    "lightSignalSystemControlInfo"
})
public class XMLLightSignalSystemConfigurationType
    extends XMLIdRefType
{

    @XmlElement(required = true)
    protected XMLLightSignalSystemControlInfoType lightSignalSystemControlInfo;

    /**
     * Gets the value of the lightSignalSystemControlInfo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLLightSignalSystemControlInfoType }
     *     
     */
    public XMLLightSignalSystemControlInfoType getLightSignalSystemControlInfo() {
        return lightSignalSystemControlInfo;
    }

    /**
     * Sets the value of the lightSignalSystemControlInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLLightSignalSystemControlInfoType }
     *     
     */
    public void setLightSignalSystemControlInfo(XMLLightSignalSystemControlInfoType value) {
        this.lightSignalSystemControlInfo = value;
    }

}
