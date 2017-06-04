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
package org.dozer.builder.model.configuration;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dozer.builder.model.AbstractDefinition;
import org.dozer.builder.model.MappingsDefinition;
import org.dozer.classmap.Configuration;
import org.dozer.classmap.CopyByReference;
import org.dozer.classmap.RelationshipType;
import org.dozer.config.BeanContainer;
import org.dozer.loader.DozerBuilder;
import org.dozer.util.DozerConstants;
import org.dozer.util.MappingUtils;

/**
 * Default values that are applied to all mappings. Global ConfigurationDefinition.
 * <p>
 * Required Attributes:
 * <p>
 * Optional Attributes:
 * <p>
 * stop-on-errors Indicates whether Dozer should stop mapping fields and throw the Exception if an error is
 * encountered while performing a field mapping. It is recommended that this is set to "true". If set to "false",
 * Dozer will trap the exception, log the error, and then continue mapping subsequent fields. The default value is "true".
 * <p>
 * date-format The string format of Date fields. This is used for field mapping between Strings and Dates
 * <p>
 * wildcard Indicates whether Dozer automatically map fields that have the same name.
 * This will typically be set to "true". The default value is "true"
 * <p>
 * trim-strings Indicates whether Dozer automatically trims String values prior to setting the destination value.
 * The default value is "false"
 * <p>
 * bean-factory The factory class to create data objects. This typically will not be specified.
 * By default Dozer constructs new instances of data objects by invoking the no-arg constructor
 * <p>
 * relationship-type Indications whether collections are mapped cumulative or non-cumulative. cumulative indicates
 * the element is added to the collection.
 * <p>
 * non-cumulative indicates the element will be added or an existing entry will be updated.
 * <p>
 * custom-converters The custom converters to be registered with Dozer.
 * <p>
 * copy-by-references Indicates which class types should always be copied by reference
 */
@XmlRootElement(name = "configuration")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfigurationDefinition extends AbstractDefinition {

    @XmlTransient
    private MappingsDefinition parent;

    @XmlElement(name = "stop-on-errors")
    private Boolean stopOnErrors;

    @XmlElement(name = "date-format")
    private String dateFormat;

    @XmlElement(name = "wildcard")
    private Boolean wildcard;

    @XmlElement(name = "trim-strings")
    private Boolean trimStrings;

    @XmlElement(name = "map-null")
    private Boolean mapNull;

    @XmlElement(name = "map-empty-string")
    private Boolean mapEmptyString;

    @XmlElement(name = "bean-factory")
    private String beanFactory;

    @XmlElement(name = "relationship-type")
    private Relationship relationshipType;

    @XmlElement(name = "custom-converters")
    private CustomConvertersDefinition customConverters;

    @XmlElement(name = "copy-by-references")
    private CopyByReferencesDefinition copyByReferences;

    @XmlElement(name = "allowed-exceptions")
    private AllowedExceptionsDefinition allowedExceptions;

    @XmlElement(name = "variables")
    private VariablesDefinition variables;

    public ConfigurationDefinition() {
        this(null);
    }

    public ConfigurationDefinition(MappingsDefinition parent) {
        this.parent = parent;

        setELEngine(BeanContainer.getInstance().getElEngine());
    }

    public Boolean getStopOnErrors() {
        return stopOnErrors;
    }

    public String getDateFormat() {
        return dateFormat;
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

    public Relationship getRelationshipType() {
        return relationshipType;
    }

    public CustomConvertersDefinition getCustomConverters() {
        return customConverters;
    }

    public CopyByReferencesDefinition getCopyByReferences() {
        return copyByReferences;
    }

    public AllowedExceptionsDefinition getAllowedExceptions() {
        return allowedExceptions;
    }

    public VariablesDefinition getVariables() {
        return variables;
    }

    public void setStopOnErrors(Boolean stopOnErrors) {
        this.stopOnErrors = stopOnErrors;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
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

    public void setRelationshipType(Relationship relationshipType) {
        this.relationshipType = relationshipType;
    }

    public void setCustomConverters(CustomConvertersDefinition customConverters) {
        this.customConverters = customConverters;
    }

    public void setCopyByReferences(CopyByReferencesDefinition copyByReferences) {
        this.copyByReferences = copyByReferences;
    }

    public void setAllowedExceptions(AllowedExceptionsDefinition allowedExceptions) {
        this.allowedExceptions = allowedExceptions;
    }

    public void setVariables(VariablesDefinition variables) {
        this.variables = variables;
    }

    // Fluent API
    //-------------------------------------------------------------------------
    public ConfigurationDefinition withStopOnErrors(Boolean stopOnErrors) {
        setStopOnErrors(stopOnErrors);

        return this;
    }

    public ConfigurationDefinition withDateFormat(String dateFormat) {
        setDateFormat(dateFormat);

        return this;
    }

    public ConfigurationDefinition withWildcard(Boolean wildcard) {
        setWildcard(wildcard);

        return this;
    }

    public ConfigurationDefinition withTrimStrings(Boolean trimStrings) {
        setTrimStrings(trimStrings);

        return this;
    }

    public ConfigurationDefinition withMapNull(Boolean mapNull) {
        setMapNull(mapNull);

        return this;
    }

    public ConfigurationDefinition withMapEmptyString(Boolean mapEmptyString) {
        setMapEmptyString(mapEmptyString);

        return this;
    }

    public ConfigurationDefinition withBeanFactory(String beanFactory) {
        setBeanFactory(beanFactory);

        return this;
    }

    public ConfigurationDefinition withRelationshipType(Relationship relationshipType) {
        setRelationshipType(relationshipType);

        return this;
    }

    public CustomConvertersDefinition withCustomConverters() {
        CustomConvertersDefinition customConverters = new CustomConvertersDefinition();
        setCustomConverters(customConverters);

        return customConverters;
    }

    public CopyByReferencesDefinition withCopyByReferences() {
        CopyByReferencesDefinition copyByReferences = new CopyByReferencesDefinition();
        setCopyByReferences(copyByReferences);

        return copyByReferences;
    }

    public AllowedExceptionsDefinition withAllowedExceptions() {
        AllowedExceptionsDefinition allowedExceptions = new AllowedExceptionsDefinition();
        setAllowedExceptions(allowedExceptions);

        return allowedExceptions;
    }

    public VariablesDefinition withVariables() {
        VariablesDefinition variables = new VariablesDefinition();
        setVariables(variables);

        return variables;
    }

    public MappingsDefinition end() {
        return parent;
    }


    public Configuration convert() {
        if (variables != null) {
            //NOTE: We resolve any EL variables first, so they can be used anywhere
            variables.build();
        }

        Configuration config = new Configuration();
        config.setBeanFactory(resolveELExpression(beanFactory));
        config.setDateFormat(dateFormat);
        config.setMapEmptyString(mapEmptyString ==  null ? DozerConstants.DEFAULT_MAP_EMPTY_STRING_POLICY : mapEmptyString);
        config.setMapNull(mapNull == null ? DozerConstants.DEFAULT_MAP_NULL_POLICY : mapNull);
        config.setRelationshipType(RelationshipType.valueOf(relationshipType == null ? "" : relationshipType.value()));
        config.setStopOnErrors(stopOnErrors == null ? true : stopOnErrors);
        config.setTrimStrings(trimStrings == null ? DozerConstants.DEFAULT_TRIM_STRINGS_POLICY : trimStrings);
        config.setWildcard(wildcard == null ? DozerConstants.DEFAULT_WILDCARD_POLICY : wildcard);

        if (allowedExceptions != null) {
            config.getAllowedExceptions().getExceptions().addAll(allowedExceptions.build());
        }

        if (copyByReferences != null) {
            List<CopyByReference> copyByReferencesList = copyByReferences.convert();
            for (CopyByReference current : copyByReferencesList) {
                config.getCopyByReferences().add(current);
            }
        }

        if (customConverters != null) {
            config.getCustomConverters().setConverters(customConverters.convert());
        }

        return config;
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("stopOnErrors", stopOnErrors)
            .append("dateFormat", dateFormat)
            .append("wildcard", wildcard)
            .append("trimStrings", trimStrings)
            .append("mapNull", mapNull)
            .append("mapEmptyString", mapEmptyString)
            .append("beanFactory", beanFactory)
            .append("relationshipType", relationshipType)
            .append("customConverters", customConverters)
            .append("copyByReferences", copyByReferences)
            .append("allowedExceptions", allowedExceptions)
            .append("variables", variables)
            .toString();
    }
}
