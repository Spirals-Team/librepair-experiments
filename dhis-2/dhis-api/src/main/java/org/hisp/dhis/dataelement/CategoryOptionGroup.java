package org.hisp.dhis.dataelement;

/*
 * Copyright (c) 2004-2018, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.hisp.dhis.common.BaseDimensionalItemObject;
import org.hisp.dhis.common.BaseIdentifiableObject;
import org.hisp.dhis.common.DataDimensionType;
import org.hisp.dhis.common.DimensionItemType;
import org.hisp.dhis.common.DxfNamespaces;
import org.hisp.dhis.common.MetadataObject;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Lars Helge Overland
 */
@JacksonXmlRootElement( localName = "categoryOptionGroup", namespace = DxfNamespaces.DXF_2_0 )
public class CategoryOptionGroup
    extends BaseDimensionalItemObject implements MetadataObject
{
    private Set<DataElementCategoryOption> members = new HashSet<>();

    private Set<CategoryOptionGroupSet> groupSets = new HashSet<>();

    private DataDimensionType dataDimensionType;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public CategoryOptionGroup()
    {

    }

    public CategoryOptionGroup( String name )
    {
        this();
        this.name = name;
    }


    // -------------------------------------------------------------------------
    // DimensionalItemObject
    // -------------------------------------------------------------------------

    @Override
    public DimensionItemType getDimensionItemType()
    {
        return DimensionItemType.CATEGORY_OPTION_GROUP;
    }

    // -------------------------------------------------------------------------
    // Getters and setters
    // -------------------------------------------------------------------------

    @JsonProperty( "categoryOptions" )
    @JsonSerialize( contentAs = BaseIdentifiableObject.class )
    @JacksonXmlElementWrapper( localName = "categoryOptions", namespace = DxfNamespaces.DXF_2_0 )
    @JacksonXmlProperty( localName = "categoryOption", namespace = DxfNamespaces.DXF_2_0 )
    public Set<DataElementCategoryOption> getMembers()
    {
        return members;
    }

    public void setMembers( Set<DataElementCategoryOption> members )
    {
        this.members = members;
    }

    @JsonProperty
    @JsonSerialize( contentAs = BaseIdentifiableObject.class )
    @JacksonXmlElementWrapper( localName = "groupSets", namespace = DxfNamespaces.DXF_2_0 )
    @JacksonXmlProperty( localName = "groupSet", namespace = DxfNamespaces.DXF_2_0 )
    public Set<CategoryOptionGroupSet> getGroupSets()
    {
        return groupSets;
    }

    public void setGroupSets( Set<CategoryOptionGroupSet> groupSets )
    {
        this.groupSets = groupSets;
    }

    @JsonProperty
    @JacksonXmlProperty( namespace = DxfNamespaces.DXF_2_0 )
    public DataDimensionType getDataDimensionType()
    {
        return dataDimensionType;
    }

    public void setDataDimensionType( DataDimensionType dataDimensionType )
    {
        this.dataDimensionType = dataDimensionType;
    }

    // -------------------------------------------------------------------------
    // Logic
    // -------------------------------------------------------------------------

    public void addCategoryOption( DataElementCategoryOption categoryOption )
    {
        members.add( categoryOption );
        categoryOption.getGroups().add( this );
    }

    public void removeCategoryOption( DataElementCategoryOption categoryOption )
    {
        members.remove( categoryOption );
        categoryOption.getGroups().remove( this );
    }
}
