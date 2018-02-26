package com.weaverplatform.protocol.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Mohamad Alamili
 */
public enum AttributeDataType {

  @SerializedName("string")                       STRING,
  @SerializedName("double")                       DOUBLE,
  @SerializedName("date")                         DATE,
  @SerializedName("boolean")                      BOOLEAN,

  @SerializedName("rdf:langString")               RDF_LANGSTRING,

  @SerializedName("xsd:duration")                 XSD_DURATION,
  @SerializedName("xsd:dateTime")                 XSD_DATETIME,
  @SerializedName("xsd:dayTimeDuration")          XSD_DAYTIMEDURATION,
  @SerializedName("xsd:time")                     XSD_TIME,
  @SerializedName("xsd:date")                     XSD_DATE,
  @SerializedName("xsd:gYearMonth")               XSD_GYEARMONTH,
  @SerializedName("xsd:gYear")                    XSD_GYEAR,
  @SerializedName("xsd:gMonthDay")                XSD_GMONTHDAY,
  @SerializedName("xsd:gDay")                     XSD_GDAY,
  @SerializedName("xsd:gMonth")                   XSD_GMONTH,
  @SerializedName("xsd:string")                   XSD_STRING,
  @SerializedName("xsd:boolean")                  XSD_BOOLEAN,
  @SerializedName("xsd:base64Binary")             XSD_BASE64BINARY,
  @SerializedName("xsd:hexBinary")                XSD_HEXBINARY,
  @SerializedName("xsd:float")                    XSD_FLOAT,
  @SerializedName("xsd:decimal")                  XSD_DECIMAL,
  @SerializedName("xsd:double")                   XSD_DOUBLE,
  @SerializedName("xsd:anyURI")                   XSD_ANYURI,
  @SerializedName("xsd:QName")                    XSD_QNAME,
  @SerializedName("xsd:NOTATION")                 XSD_NOTATION,
  @SerializedName("xsd:normalizedString")         XSD_NORMALIZEDSTRING,
  @SerializedName("xsd:token")                    XSD_TOKEN,
  @SerializedName("xsd:language")                 XSD_LANGUAGE,
  @SerializedName("xsd:NMTOKEN")                  XSD_NMTOKEN,
  @SerializedName("xsd:NMTOKENS")                 XSD_NMTOKENS,
  @SerializedName("xsd:Name")                     XSD_NAME,
  @SerializedName("xsd:NCName")                   XSD_NCNAME,
  @SerializedName("xsd:ID")                       XSD_ID,
  @SerializedName("xsd:IDREF")                    XSD_IDREF,
  @SerializedName("xsd:IDREFS")                   XSD_IDREFS,
  @SerializedName("xsd:ENTITY")                   XSD_ENTITY,
  @SerializedName("xsd:ENTITIES")                 XSD_ENTITIES,
  @SerializedName("xsd:integer")                  XSD_INTEGER,
  @SerializedName("xsd:long")                     XSD_LONG,
  @SerializedName("xsd:int")                      XSD_INT,
  @SerializedName("xsd:short")                    XSD_SHORT,
  @SerializedName("xsd:byte")                     XSD_BYTE,
  @SerializedName("xsd:nonPositiveInteger")       XSD_NON_POSITIVE_INTEGER,
  @SerializedName("xsd:negativeInteger")          XSD_NEGATIVE_INTEGER,
  @SerializedName("xsd:nonNegativeInteger")       XSD_NON_NEGATIVE_INTEGER,
  @SerializedName("xsd:positiveInteger")          XSD_POSITIVE_INTEGER,
  @SerializedName("xsd:unsignedLong")             XSD_UNSIGNED_LONG,
  @SerializedName("xsd:unsignedInt")              XSD_UNSIGNED_INT,
  @SerializedName("xsd:unsignedShort")            XSD_UNSIGNED_SHORT,
  @SerializedName("xsd:unsignedByte")             XSD_UNSIGNED_BYTE,
  @SerializedName("xsd:yearMonthDuration")        XSD_YEARMONTHDURATION
}
