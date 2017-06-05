/*
 * Copyright 2005-2017 Dozer Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dozer.builder.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dozer.classmap.ClassMap;
import org.dozer.classmap.Configuration;
import org.dozer.classmap.MappingDirection;
import org.dozer.classmap.RelationshipType;
import org.dozer.config.BeanContainer;
import org.dozer.fieldmap.FieldMap;

/**
 * Specifies a custom mapping definition between two classes(data types). All Mapping definitions are
 * bi-directional by default.
 * Global configuration element values are inherited
 * <p>
 * Required Attributes:
 * <p>
 * Optional Attributes:
 * <p>
 * date-format The string format of Date fields. This is used for field mapping between Strings and Dates
 * <p>
 * stop-on-errors Indicates whether Dozer should stop mapping fields and throw the Exception if an error is
 * encountered while performing a field mapping. It is recommended that this is set to "true".
 * If set to "false", Dozer will trap the exception, log the error, and then continue mapping subsequent fields
 * The default value is "true"
 * <p>
 * wildcard Indicates whether Dozer automatically map fields that have the same name. The default value is "true"
 * <p>
 * trim-strings Indicates whether Dozer automatically trims String values prior to setting the destination value.
 * The default value is "false"
 * <p>
 * map-null Indicates whether null values are mapped. The default value is "true"
 * <p>
 * map-empty-string Indicates whether empty string values are mapped. The default value is "true"
 * <p>
 * bean-factory The factory class to create data objects. This typically will not be specified.
 * By default Dozer constructs new instances of data objects by invoking the no-arg constructor
 * <p>
 * type Indicates whether this mapping is bi-directional or only one-way. Typically this will be set to
 * bi-directional. The default is "bi-directional".
 * <p>
 * map-id The id that uniquely identifies this mapping definition. This typically will not be specified.
 * You would only need to specify this for only need this for special context based mapping
 * and when mapping between Map objects and Custom Data Objects.
 * <p>
 * relationship-type Indications whether collections are mapped cumulative or non-cumulative. cumulative indicates
 * the element is added to the collection.
 * <p>
 * non-cumulative indicates the element will be added or an existing entry will be updated.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "mapping")
public class MappingDefinition extends AbstractDefinition {

    @XmlTransient
    private MappingsDefinition parent;

    @XmlElement(name = "class-a", required = true)
    private ClassDefinition classA;

    @XmlElement(name = "class-b", required = true)
    private ClassDefinition classB;

    @XmlElements({
        @XmlElement(name = "field", type = FieldDefinition.class),
        @XmlElement(name = "field-exclude", type = FieldExcludeDefinition.class)
    })
    private List<Object> onlyUsedForXSDGeneratation;

    @JacksonXmlProperty(localName = "field")
    private List<FieldDefinition> fields;

    @JacksonXmlProperty(localName = "field-exclude")
    private List<FieldExcludeDefinition> fieldExcludes;

    @XmlAttribute(name = "date-format")
    private String dateFormat;

    @XmlAttribute(name = "stop-on-errors")
    private Boolean stopOnErrors;

    @XmlAttribute(name = "wildcard")
    private Boolean wildcard;

    @XmlAttribute(name = "trim-strings")
    private Boolean trimStrings;

    @XmlAttribute(name = "map-null")
    private Boolean mapNull;

    @XmlAttribute(name = "map-empty-string")
    private Boolean mapEmptyString;

    @XmlAttribute(name = "bean-factory")
    private String beanFactory;

    @XmlAttribute(name = "type")
    private Type type;

    @XmlAttribute(name = "relationship-type")
    private Relationship relationshipType;

    @XmlAttribute(name = "map-id")
    private String mapId;

    public MappingDefinition() {
        this(null);
    }

    public MappingDefinition(MappingsDefinition parent) {
        this.parent = parent;

        setELEngine(BeanContainer.getInstance().getElEngine());
    }

    public ClassDefinition getClassA() {
        return classA;
    }

    public ClassDefinition getClassB() {
        return classB;
    }

    public List<FieldDefinition> getFields() {
        return fields;
    }

    public List<FieldExcludeDefinition> getFieldExcludes() {
        return fieldExcludes;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public Boolean getStopOnErrors() {
        return stopOnErrors;
    }

    public Boolean getWildcard() {
        return wildcard;
    }

    public Boolean getTrimStrings() {
        return trimStrings;
    }

    public Boolean getMapNull() {
        return mapNull;
    }

    public Boolean getMapEmptyString() {
        return mapEmptyString;
    }

    public String getBeanFactory() {
        return beanFactory;
    }

    public Type getType() {
        return type;
    }

    public Relationship getRelationshipType() {
        return relationshipType;
    }

    public String getMapId() {
        return mapId;
    }

    public void setClassA(ClassDefinition classA) {
        this.classA = classA;
    }

    public void setClassB(ClassDefinition classB) {
        this.classB = classB;
    }

    public void setFields(List<FieldDefinition> fields) {
        this.fields = fields;
    }

    public void setFieldExcludes(List<FieldExcludeDefinition> fieldExcludes) {
        this.fieldExcludes = fieldExcludes;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setStopOnErrors(Boolean stopOnErrors) {
        this.stopOnErrors = stopOnErrors;
    }

    public void setWildcard(Boolean wildcard) {
        this.wildcard = wildcard;
    }

    public void setTrimStrings(Boolean trimStrings) {
        this.trimStrings = trimStrings;
    }

    public void setMapNull(Boolean mapNull) {
        this.mapNull = mapNull;
    }

    public void setMapEmptyString(Boolean mapEmptyString) {
        this.mapEmptyString = mapEmptyString;
    }

    public void setBeanFactory(String beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setRelationshipType(Relationship relationshipType) {
        this.relationshipType = relationshipType;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    // Fluent API
    //-------------------------------------------------------------------------
    public ClassDefinition withClassA() {
        ClassDefinition classA = new ClassDefinition(this);
        setClassA(classA);

        return classA;
    }

    public ClassDefinition withClassB() {
        ClassDefinition classB = new ClassDefinition(this);
        setClassB(classB);

        return classB;
    }

    public FieldDefinition withField() {
        if (getFields() == null) {
            setFields(new ArrayList<FieldDefinition>());
        }

        FieldDefinition field = new FieldDefinition(this, null);
        getFields().add(field);

        return field;
    }

    public FieldExcludeDefinition withFieldExclude() {
        if (getFieldExcludes() == null) {
            setFieldExcludes(new ArrayList<FieldExcludeDefinition>());
        }

        FieldExcludeDefinition fieldExclude = new FieldExcludeDefinition(this);
        getFieldExcludes().add(fieldExclude);

        return fieldExclude;
    }

    public MappingDefinition withDateFormat(String dateFormat) {
        setDateFormat(dateFormat);

        return this;
    }

    public MappingDefinition withStopOnErrors(Boolean stopOnErrors) {
        setStopOnErrors(stopOnErrors);

        return this;
    }

    public MappingDefinition withWildcard(Boolean wildcard) {
        setWildcard(wildcard);

        return this;
    }

    public MappingDefinition withTrimStrings(Boolean trimStrings) {
        setTrimStrings(trimStrings);

        return this;
    }

    public MappingDefinition withMapNull(Boolean mapNull) {
        setMapNull(mapNull);

        return this;
    }

    public MappingDefinition withMapEmptyString(Boolean mapEmptyString) {
        setMapEmptyString(mapEmptyString);

        return this;
    }

    public MappingDefinition withBeanFactory(String beanFactory) {
        setBeanFactory(beanFactory);

        return this;
    }

    public MappingDefinition withType(Type type) {
        setType(type);

        return this;
    }

    public MappingDefinition withRelationshipType(Relationship relationshipType) {
        setRelationshipType(relationshipType);

        return this;
    }

    public MappingDefinition withMapId(String mapId) {
        setMapId(mapId);

        return this;
    }

    public MappingsDefinition end() {
        return parent;
    }

    public ClassMap build(Configuration configuration) {
        ClassMap current = new ClassMap(configuration);
        current.setSrcClass(classA.convert());
        current.setDestClass(classB.convert());
        current.setType(MappingDirection.valueOf(type == null ? Type.BI_DIRECTIONAL.value() : type.value()));
        current.setDateFormat(dateFormat);
        current.setBeanFactory(resolveELExpression(beanFactory));

        if (mapNull != null) {
            current.setMapNull(mapNull);
        }

        if (mapEmptyString != null) {
            current.setMapEmptyString(mapEmptyString);
        }

        current.setWildcard(wildcard == null ? null : wildcard);
        current.setStopOnErrors(stopOnErrors == null ? null : stopOnErrors);
        current.setTrimStrings(trimStrings == null ? null : trimStrings);
        current.setMapId(mapId);
        current.setRelationshipType(RelationshipType.valueOf(relationshipType == null ? Relationship.CUMULATIVE.value() : relationshipType.value()));
        current.setFieldMaps(convertFieldMap(current));

        return current;
    }

    private List<FieldMap> convertFieldMap(ClassMap classMap) {
        List<FieldMap> answer = new ArrayList<FieldMap>();
        if (fields != null) {
            for (FieldDefinition current : fields) {
                answer.add(current.build(classMap));
            }
        }

        if (fieldExcludes != null) {
            for (FieldExcludeDefinition current : fieldExcludes) {
                answer.add(current.build(classMap));
            }
        }

        return answer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("classA", classA)
            .append("classB", classB)
            .append("fields", fields)
            .append("fieldExcludes", fieldExcludes)
            .append("dateFormat", dateFormat)
            .append("stopOnErrors", stopOnErrors)
            .append("wildcard", wildcard)
            .append("trimStrings", trimStrings)
            .append("mapNull", mapNull)
            .append("mapEmptyString", mapEmptyString)
            .append("beanFactory", beanFactory)
            .append("type", type)
            .append("relationshipType", relationshipType)
            .append("mapId", mapId)
            .toString();
    }
}
