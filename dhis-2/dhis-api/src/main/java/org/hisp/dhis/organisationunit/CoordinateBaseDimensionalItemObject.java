package org.hisp.dhis.organisationunit;

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
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.apache.commons.lang3.StringUtils;
import org.hisp.dhis.common.BaseDimensionalItemObject;
import org.hisp.dhis.common.DxfNamespaces;
import org.hisp.dhis.schema.PropertyType;
import org.hisp.dhis.schema.annotation.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Henning Håkonsen
 */
public class CoordinateBaseDimensionalItemObject
    extends BaseDimensionalItemObject
{
    private static final Pattern JSON_POINT_PATTERN = Pattern.compile( "(\\[.*?])" );

    private static final Pattern JSON_COORDINATE_PATTERN = Pattern.compile( "(\\[{3}.*?]{3})" );

    private static final Pattern COORDINATE_PATTERN = Pattern.compile( "([\\-0-9.]+,[\\-0-9.]+)" );

    private FeatureType featureType = FeatureType.NONE;

    private String coordinates;

    public boolean hasCoordinates()
    {
        return coordinates != null && coordinates.trim().length() > 0;
    }

    public boolean hasFeatureType()
    {
        return featureType != null;
    }

    public boolean hasDescendantsWithCoordinates()
    {
        return false;
    }

    public boolean isPolygon()
    {
        return featureType != null && featureType.isPolygon();
    }

    public boolean isPoint()
    {
        return featureType != null && featureType == FeatureType.POINT;
    }

    public List<CoordinatesTuple> getCoordinatesAsList()
    {
        List<CoordinatesTuple> list = new ArrayList<>();

        if ( coordinates != null && !coordinates.trim().isEmpty() )
        {
            Matcher jsonMatcher = isPoint() ?
                JSON_POINT_PATTERN.matcher( coordinates ) : JSON_COORDINATE_PATTERN.matcher( coordinates );

            while ( jsonMatcher.find() )
            {
                CoordinatesTuple tuple = new CoordinatesTuple();

                Matcher matcher = COORDINATE_PATTERN.matcher( jsonMatcher.group() );

                while ( matcher.find() )
                {
                    tuple.addCoordinates( matcher.group() );
                }

                list.add( tuple );
            }
        }

        return list;
    }

    void setMultiPolygonCoordinatesFromList( List<CoordinatesTuple> list )
    {
        StringBuilder builder = new StringBuilder();

        if ( CoordinatesTuple.hasCoordinates( list ) )
        {
            builder.append( "[" );

            for ( CoordinatesTuple tuple : list )
            {
                if ( tuple.hasCoordinates() )
                {
                    builder.append( "[[" );

                    for ( String coordinates : tuple.getCoordinatesTuple() )
                    {
                        builder.append( "[" ).append( coordinates ).append( "]," );
                    }

                    builder.deleteCharAt( builder.lastIndexOf( "," ) );
                    builder.append( "]]," );
                }
            }

            builder.deleteCharAt( builder.lastIndexOf( "," ) );
            builder.append( "]" );
        }

        this.coordinates = StringUtils.trimToNull( builder.toString() );
    }

    void setPointCoordinatesFromList( List<CoordinatesTuple> list )
    {
        StringBuilder builder = new StringBuilder();

        if ( list != null && list.size() > 0 )
        {
            for ( CoordinatesTuple tuple : list )
            {
                for ( String coordinates : tuple.getCoordinatesTuple() )
                {
                    builder.append( "[" ).append( coordinates ).append( "]" );
                }
            }
        }

        this.coordinates = StringUtils.trimToNull( builder.toString() );
    }

    @JsonProperty
    @JacksonXmlProperty( namespace = DxfNamespaces.DXF_2_0 )
    public FeatureType getFeatureType()
    {
        return featureType;
    }

    public void setFeatureType( FeatureType featureType )
    {
        this.featureType = featureType;
    }

    @JsonProperty
    @JacksonXmlProperty( namespace = DxfNamespaces.DXF_2_0 )
    @Property( PropertyType.GEOLOCATION )
    public String getCoordinates()
    {
        return coordinates;
    }

    public void setCoordinates( String coordinates )
    {
        this.coordinates = coordinates;
    }
}
