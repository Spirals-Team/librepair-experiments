//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.09.20 at 07:22:03 PM MESZ 
//


package org.matsim.jaxb.signalsystemsconfig11;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *     This type or more precisely its subtypes define how a signal group system is controlled. Control can be adaptive, plan based or both.
 *     The attribute controlStrategy reflects the subtype choosen and is required for simplicity of parsing the
 *     xml-files.
 *     
 * 
 * <p>Java class for signalSystemControlInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="signalSystemControlInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "signalSystemControlInfoType")
@XmlSeeAlso({
    XMLPlanbasedSignalSystemControlInfoType.class,
    XMLAdaptiveSignalSystemControlInfoType.class
})
public abstract class XMLSignalSystemControlInfoType {


}
