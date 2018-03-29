package com.weaverplatform.util;

import com.weaverplatform.protocol.model.AttributeDataType;
import com.weaverplatform.protocol.model.PrimitiveDataType;

/**
 * @author bastbijl, Sysunite 2017
 */
public class DataTypeUtil {
  
  public static final String XML_NAMESPACE = "http://www.w3.org/2001/XMLSchema#";
  public static final String RDF_NAMESPACE = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";


  public static String getDatatypeCode(AttributeDataType dataType) {
    switch (dataType) {

      case STRING :                   return "string";
      case DOUBLE :                   return "double";
      case DATE :                     return "date";
      case BOOLEAN :                  return "boolean";
      case RDF_LANGSTRING :           return "rdf:langString";
      case XSD_DURATION :             return "xsd:duration";
      case XSD_DATETIME :             return "xsd:dateTime";
      case XSD_DAYTIMEDURATION :      return "xsd:dayTimeDuration";
      case XSD_TIME :                 return "xsd:time";
      case XSD_DATE :                 return "xsd:date";
      case XSD_GYEARMONTH :           return "xsd:gYearMonth";
      case XSD_GYEAR :                return "xsd:gYear";
      case XSD_GMONTHDAY :            return "xsd:gMonthDay";
      case XSD_GDAY :                 return "xsd:gDay";
      case XSD_GMONTH :               return "xsd:gMonth";
      case XSD_STRING :               return "xsd:string";
      case XSD_BOOLEAN :              return "xsd:boolean";
      case XSD_BASE64BINARY :         return "xsd:base64Binary";
      case XSD_HEXBINARY :            return "xsd:hexBinary";
      case XSD_FLOAT :                return "xsd:float";
      case XSD_DECIMAL :              return "xsd:decimal";
      case XSD_DOUBLE :               return "xsd:double";
      case XSD_ANYURI :               return "xsd:anyURI";
      case XSD_QNAME :                return "xsd:QName";
      case XSD_NOTATION :             return "xsd:NOTATION";
      case XSD_NORMALIZEDSTRING :     return "xsd:normalizedString";
      case XSD_TOKEN :                return "xsd:token";
      case XSD_LANGUAGE :             return "xsd:language";
      case XSD_NMTOKEN :              return "xsd:NMTOKEN";
      case XSD_NMTOKENS :             return "xsd:NMTOKENS";
      case XSD_NAME :                 return "xsd:Name";
      case XSD_NCNAME :               return "xsd:NCName";
      case XSD_ID :                   return "xsd:ID";
      case XSD_IDREF :                return "xsd:IDREF";
      case XSD_IDREFS :               return "xsd:IDREFS";
      case XSD_ENTITY :               return "xsd:ENTITY";
      case XSD_ENTITIES :             return "xsd:ENTITIES";
      case XSD_INTEGER :              return "xsd:integer";
      case XSD_LONG :                 return "xsd:long";
      case XSD_INT :                  return "xsd:int";
      case XSD_SHORT :                return "xsd:short";
      case XSD_BYTE :                 return "xsd:byte";
      case XSD_NON_POSITIVE_INTEGER : return "xsd:nonPositiveInteger";
      case XSD_NEGATIVE_INTEGER :     return "xsd:negativeInteger";
      case XSD_NON_NEGATIVE_INTEGER : return "xsd:nonNegativeInteger";
      case XSD_POSITIVE_INTEGER :     return "xsd:positiveInteger";
      case XSD_UNSIGNED_LONG :        return "xsd:unsignedLong";
      case XSD_UNSIGNED_INT :         return "xsd:unsignedInt";
      case XSD_UNSIGNED_SHORT :       return "xsd:unsignedShort";
      case XSD_UNSIGNED_BYTE :        return "xsd:unsignedByte";
      case XSD_YEARMONTHDURATION :    return "xsd:yearMonthDuration";

      default:
        throw new RuntimeException("This dataType is not supported: "+dataType);

    }
  }

  public static AttributeDataType parse(String code) {

    if(code.startsWith(RDF_NAMESPACE)) {
      code = code.replace(RDF_NAMESPACE, "rdf:");
    }
    if(code.startsWith(XML_NAMESPACE)) {
      code = code.replace(XML_NAMESPACE, "xsd:");
    }

    switch (code) {

      case "string"                 : return AttributeDataType.STRING;
      case "double"                 : return AttributeDataType.DOUBLE;
      case "date"                   : return AttributeDataType.DATE;
      case "boolean"                : return AttributeDataType.BOOLEAN;
      case "rdf:langString"         : return AttributeDataType.RDF_LANGSTRING;
      case "xsd:duration"           : return AttributeDataType.XSD_DURATION;
      case "xsd:dateTime"           : return AttributeDataType.XSD_DATETIME;
      case "xsd:dayTimeDuration"    : return AttributeDataType.XSD_DAYTIMEDURATION;
      case "xsd:time"               : return AttributeDataType.XSD_TIME;
      case "xsd:date"               : return AttributeDataType.XSD_DATE;
      case "xsd:gYearMonth"         : return AttributeDataType.XSD_GYEARMONTH;
      case "xsd:gYear"              : return AttributeDataType.XSD_GYEAR;
      case "xsd:gMonthDay"          : return AttributeDataType.XSD_GMONTHDAY;
      case "xsd:gDay"               : return AttributeDataType.XSD_GDAY;
      case "xsd:gMonth"             : return AttributeDataType.XSD_GMONTH;
      case "xsd:string"             : return AttributeDataType.XSD_STRING;
      case "xsd:boolean"            : return AttributeDataType.XSD_BOOLEAN;
      case "xsd:base64Binary"       : return AttributeDataType.XSD_BASE64BINARY;
      case "xsd:hexBinary"          : return AttributeDataType.XSD_HEXBINARY;
      case "xsd:float"              : return AttributeDataType.XSD_FLOAT;
      case "xsd:decimal"            : return AttributeDataType.XSD_DECIMAL;
      case "xsd:double"             : return AttributeDataType.XSD_DOUBLE;
      case "xsd:anyURI"             : return AttributeDataType.XSD_ANYURI;
      case "xsd:QName"              : return AttributeDataType.XSD_QNAME;
      case "xsd:NOTATION"           : return AttributeDataType.XSD_NOTATION;
      case "xsd:normalizedString"   : return AttributeDataType.XSD_NORMALIZEDSTRING;
      case "xsd:token"              : return AttributeDataType.XSD_TOKEN;
      case "xsd:language"           : return AttributeDataType.XSD_LANGUAGE;
      case "xsd:NMTOKEN"            : return AttributeDataType.XSD_NMTOKEN;
      case "xsd:NMTOKENS"           : return AttributeDataType.XSD_NMTOKENS;
      case "xsd:Name"               : return AttributeDataType.XSD_NAME;
      case "xsd:NCName"             : return AttributeDataType.XSD_NCNAME;
      case "xsd:ID"                 : return AttributeDataType.XSD_ID;
      case "xsd:IDREF"              : return AttributeDataType.XSD_IDREF;
      case "xsd:IDREFS"             : return AttributeDataType.XSD_IDREFS;
      case "xsd:ENTITY"             : return AttributeDataType.XSD_ENTITY;
      case "xsd:ENTITIES"           : return AttributeDataType.XSD_ENTITIES;
      case "xsd:integer"            : return AttributeDataType.XSD_INTEGER;
      case "xsd:long"               : return AttributeDataType.XSD_LONG;
      case "xsd:int"                : return AttributeDataType.XSD_INT;
      case "xsd:short"              : return AttributeDataType.XSD_SHORT;
      case "xsd:byte"               : return AttributeDataType.XSD_BYTE;
      case "xsd:nonPositiveInteger" : return AttributeDataType.XSD_NON_POSITIVE_INTEGER;
      case "xsd:negativeInteger"    : return AttributeDataType.XSD_NEGATIVE_INTEGER;
      case "xsd:nonNegativeInteger" : return AttributeDataType.XSD_NON_NEGATIVE_INTEGER;
      case "xsd:positiveInteger"    : return AttributeDataType.XSD_POSITIVE_INTEGER;
      case "xsd:unsignedLong"       : return AttributeDataType.XSD_UNSIGNED_LONG;
      case "xsd:unsignedInt"        : return AttributeDataType.XSD_UNSIGNED_INT;
      case "xsd:unsignedShort"      : return AttributeDataType.XSD_UNSIGNED_SHORT;
      case "xsd:unsignedByte"       : return AttributeDataType.XSD_UNSIGNED_BYTE;
      case "xsd:yearMonthDuration"  : return AttributeDataType.XSD_YEARMONTHDURATION;

      default:
        throw new RuntimeException("This dataType IRI is not supported: "+code);

    }
  }
  public static String getUri(AttributeDataType dataType) {

    switch (dataType) {

      case BOOLEAN                    :
      case XSD_BOOLEAN                :   return XML_NAMESPACE + "boolean";

      case DATE                       :
      case XSD_DATETIME               :   return XML_NAMESPACE + "dateTime";

      case DOUBLE                     :
      case XSD_DOUBLE                 :   return XML_NAMESPACE + "double";

      case STRING                     :
      case XSD_STRING                 :   return XML_NAMESPACE + "string";
      case RDF_LANGSTRING             :   return RDF_NAMESPACE + "langString";
      case XSD_DURATION               :   return XML_NAMESPACE + "duration";
      case XSD_DAYTIMEDURATION        :   return XML_NAMESPACE + "dayTimeDuration";
      case XSD_TIME                   :   return XML_NAMESPACE + "time";
      case XSD_DATE                   :   return XML_NAMESPACE + "date";
      case XSD_GYEARMONTH             :   return XML_NAMESPACE + "gYearMonth";
      case XSD_GYEAR                  :   return XML_NAMESPACE + "gYear";
      case XSD_GMONTHDAY              :   return XML_NAMESPACE + "gMonthDay";
      case XSD_GDAY                   :   return XML_NAMESPACE + "gDay";
      case XSD_GMONTH                 :   return XML_NAMESPACE + "gMonth";
      case XSD_BASE64BINARY           :   return XML_NAMESPACE + "base64Binary";
      case XSD_HEXBINARY              :   return XML_NAMESPACE + "hexBinary";
      case XSD_FLOAT                  :   return XML_NAMESPACE + "float";
      case XSD_DECIMAL                :   return XML_NAMESPACE + "decimal";
      case XSD_ANYURI                 :   return XML_NAMESPACE + "anyURI";
      case XSD_QNAME                  :   return XML_NAMESPACE + "QName";
      case XSD_NOTATION               :   return XML_NAMESPACE + "NOTATION";
      case XSD_NORMALIZEDSTRING       :   return XML_NAMESPACE + "normalizedString";
      case XSD_TOKEN                  :   return XML_NAMESPACE + "token";
      case XSD_LANGUAGE               :   return XML_NAMESPACE + "language";
      case XSD_NMTOKEN                :   return XML_NAMESPACE + "NMTOKEN";
      case XSD_NMTOKENS               :   return XML_NAMESPACE + "NMTOKENS";
      case XSD_NAME                   :   return XML_NAMESPACE + "Name";
      case XSD_NCNAME                 :   return XML_NAMESPACE + "NCName";
      case XSD_ID                     :   return XML_NAMESPACE + "ID";
      case XSD_IDREF                  :   return XML_NAMESPACE + "IDREF";
      case XSD_IDREFS                 :   return XML_NAMESPACE + "IDREFS";
      case XSD_ENTITY                 :   return XML_NAMESPACE + "ENTITY";
      case XSD_ENTITIES               :   return XML_NAMESPACE + "ENTITIES";
      case XSD_INTEGER                :   return XML_NAMESPACE + "integer";
      case XSD_LONG                   :   return XML_NAMESPACE + "long";
      case XSD_INT                    :   return XML_NAMESPACE + "int";
      case XSD_SHORT                  :   return XML_NAMESPACE + "short";
      case XSD_BYTE                   :   return XML_NAMESPACE + "byte";
      case XSD_NON_POSITIVE_INTEGER   :   return XML_NAMESPACE + "nonPositiveInteger";
      case XSD_NEGATIVE_INTEGER       :   return XML_NAMESPACE + "negativeInteger";
      case XSD_NON_NEGATIVE_INTEGER   :   return XML_NAMESPACE + "nonNegativeInteger";
      case XSD_POSITIVE_INTEGER       :   return XML_NAMESPACE + "positiveInteger";
      case XSD_UNSIGNED_LONG          :   return XML_NAMESPACE + "unsignedLong";
      case XSD_UNSIGNED_INT           :   return XML_NAMESPACE + "unsignedInt";
      case XSD_UNSIGNED_SHORT         :   return XML_NAMESPACE + "unsignedShort";
      case XSD_UNSIGNED_BYTE          :   return XML_NAMESPACE + "unsignedByte";
      case XSD_YEARMONTHDURATION      :   return XML_NAMESPACE + "yearMonthDuration";

      default:
        throw new RuntimeException("This dataType is not supported: "+dataType);

    }
  }
  public static PrimitiveDataType primitiveDataType(AttributeDataType dataType) {

    switch(dataType) {

      case BOOLEAN :
      case XSD_BOOLEAN :
        return PrimitiveDataType.BOOLEAN;

      case DATE :
      case XSD_DATETIME :
        return PrimitiveDataType.DATE;

      case DOUBLE :
      case XSD_FLOAT :
      case XSD_DECIMAL :
      case XSD_DOUBLE :
      case XSD_LONG :
      case XSD_INT :
      case XSD_SHORT :
      case XSD_BYTE :
      case XSD_NON_POSITIVE_INTEGER :
      case XSD_NEGATIVE_INTEGER :
      case XSD_NON_NEGATIVE_INTEGER :
      case XSD_POSITIVE_INTEGER :
      case XSD_UNSIGNED_LONG :
      case XSD_UNSIGNED_INT :
      case XSD_UNSIGNED_SHORT :
      case XSD_UNSIGNED_BYTE :
        return PrimitiveDataType.DOUBLE;

      case STRING :
      case XSD_DURATION :
      case XSD_DAYTIMEDURATION :
      case XSD_TIME :
      case XSD_DATE :
      case XSD_GYEARMONTH :
      case XSD_GYEAR :
      case XSD_GMONTHDAY :
      case XSD_GDAY :
      case XSD_GMONTH :
      case XSD_STRING :
      case XSD_BASE64BINARY :
      case XSD_HEXBINARY :
      case XSD_ANYURI :
      case XSD_QNAME :
      case XSD_NOTATION :
      case XSD_NORMALIZEDSTRING :
      case XSD_TOKEN :
      case XSD_LANGUAGE :
      case XSD_NMTOKEN :
      case XSD_NMTOKENS :
      case XSD_NAME :
      case XSD_NCNAME :
      case XSD_ID :
      case XSD_IDREF :
      case XSD_IDREFS :
      case XSD_ENTITY :
      case XSD_ENTITIES :
      case XSD_INTEGER :
      case XSD_YEARMONTHDURATION :
      case RDF_LANGSTRING :
        return PrimitiveDataType.STRING;

      default:
        throw new RuntimeException("This dataType is not supported: "+dataType);
    }
  }
}
