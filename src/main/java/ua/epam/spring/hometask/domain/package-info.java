//1st approach to turn on java.time support for proper converting to/from XML

@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(type = LocalDate.class, value = LocalDateXmlAdapter.class),
        @XmlJavaTypeAdapter(type = LocalDateTime.class, value = LocalDateTimeXmlAdapter.class)})

//@XmlSchemaTypes({
//        @XmlSchemaType(name = "date", type = LocalDate.class),
//        @XmlSchemaType(name = "dateTime", type = LocalDateTime.class)})

package ua.epam.spring.hometask.domain;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSchemaTypes;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.LocalDate;
import java.time.LocalDateTime;