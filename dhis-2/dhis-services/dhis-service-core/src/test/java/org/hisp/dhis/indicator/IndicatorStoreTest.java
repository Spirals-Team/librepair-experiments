package org.hisp.dhis.indicator;

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

import org.hisp.dhis.DhisSpringTest;
import org.hisp.dhis.common.GenericIdentifiableObjectStore;
import org.hisp.dhis.common.IdentifiableObjectManager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Lars Helge Overland
 * @version $Id: IndicatorStoreTest.java 3286 2007-05-07 18:05:21Z larshelg $
 */
public class IndicatorStoreTest
    extends DhisSpringTest
{
    @Autowired
    private IndicatorStore indicatorStore;
    
    @Autowired
    private IdentifiableObjectManager idObjectManager;

    @Resource(name="org.hisp.dhis.indicator.IndicatorTypeStore")
    private GenericIdentifiableObjectStore<IndicatorType> indicatorTypeStore;

    // -------------------------------------------------------------------------
    // Support methods
    // -------------------------------------------------------------------------

    private void assertEq( char uniqueCharacter, Indicator indicator )
    {
        assertEquals( "Indicator" + uniqueCharacter, indicator.getName() );
        assertEquals( "IndicatorShort" + uniqueCharacter, indicator.getShortName() );
        assertEquals( "IndicatorCode" + uniqueCharacter, indicator.getCode() );
        assertEquals( "IndicatorDescription" + uniqueCharacter, indicator.getDescription() );
    }

    // -------------------------------------------------------------------------
    // IndicatorType
    // -------------------------------------------------------------------------

    @Test
    public void testAddIndicatorType()
    {
        IndicatorType typeA = new IndicatorType( "IndicatorTypeA", 100, false );
        IndicatorType typeB = new IndicatorType( "IndicatorTypeB", 1, false );

        indicatorTypeStore.save( typeA );
        int idA = typeA.getId();
        indicatorTypeStore.save( typeB );
        int idB = typeB.getId();

        typeA = indicatorTypeStore.get( idA );
        assertNotNull( typeA );
        assertEquals( idA, typeA.getId() );

        typeB = indicatorTypeStore.get( idB );
        assertNotNull( typeB );
        assertEquals( idB, typeB.getId() );
    }

    @Test
    public void testUpdateIndicatorType()
    {
        IndicatorType typeA = new IndicatorType( "IndicatorTypeA", 100, false );
        indicatorTypeStore.save( typeA );
        int idA = typeA.getId();
        typeA = indicatorTypeStore.get( idA );
        assertEquals( typeA.getName(), "IndicatorTypeA" );

        typeA.setName( "IndicatorTypeB" );
        indicatorTypeStore.update( typeA );
        typeA = indicatorTypeStore.get( idA );
        assertNotNull( typeA );
        assertEquals( typeA.getName(), "IndicatorTypeB" );
    }

    @Test
    public void testGetAndDeleteIndicatorType()
    {
        IndicatorType typeA = new IndicatorType( "IndicatorTypeA", 100, false );
        IndicatorType typeB = new IndicatorType( "IndicatorTypeB", 1, false );

        indicatorTypeStore.save( typeA );
        int idA = typeA.getId();
        indicatorTypeStore.save( typeB );
        int idB = typeB.getId();

        assertNotNull( indicatorTypeStore.get( idA ) );
        assertNotNull( indicatorTypeStore.get( idB ) );

        indicatorTypeStore.delete( typeA );

        assertNull( indicatorTypeStore.get( idA ) );
        assertNotNull( indicatorTypeStore.get( idB ) );

        indicatorTypeStore.delete( typeB );

        assertNull( indicatorTypeStore.get( idA ) );
        assertNull( indicatorTypeStore.get( idB ) );
    }

    @Test
    public void testGetAllIndicatorTypes()
    {
        IndicatorType typeA = new IndicatorType( "IndicatorTypeA", 100, false );
        IndicatorType typeB = new IndicatorType( "IndicatorTypeB", 1, false );

        indicatorTypeStore.save( typeA );
        indicatorTypeStore.save( typeB );

        List<IndicatorType> types = indicatorTypeStore.getAll();

        assertEquals( types.size(), 2 );
        assertTrue( types.contains( typeA ) );
        assertTrue( types.contains( typeB ) );
    }

    @Test
    public void testGetIndicatorTypeByName()
    {
        IndicatorType typeA = new IndicatorType( "IndicatorTypeA", 100, false );
        IndicatorType typeB = new IndicatorType( "IndicatorTypeB", 1, false );

        indicatorTypeStore.save( typeA );
        int idA = typeA.getId();
        indicatorTypeStore.save( typeB );
        int idB = typeB.getId();

        assertNotNull( indicatorTypeStore.get( idA ) );
        assertNotNull( indicatorTypeStore.get( idB ) );

        typeA = indicatorTypeStore.getByName( "IndicatorTypeA" );
        assertNotNull( typeA );
        assertEquals( typeA.getId(), idA );

        IndicatorType typeC = indicatorTypeStore.getByName( "IndicatorTypeC" );
        assertNull( typeC );
    }


    // -------------------------------------------------------------------------
    // Indicator
    // -------------------------------------------------------------------------

    @Test
    public void testAddIndicator()
    {
        IndicatorType type = new IndicatorType( "IndicatorType", 100, false );

        indicatorTypeStore.save( type );

        Indicator indicatorA = createIndicator( 'A', type );
        Indicator indicatorB = createIndicator( 'B', type );

        indicatorStore.save( indicatorA );
        int idA = indicatorA.getId();
        indicatorStore.save( indicatorB );
        int idB = indicatorB.getId();

        indicatorA = indicatorStore.get( idA );
        assertNotNull( indicatorA );
        assertEq( 'A', indicatorA );

        indicatorB = indicatorStore.get( idB );
        assertNotNull( indicatorB );
        assertEq( 'B', indicatorB );
    }

    @Test
    public void testUpdateIndicator()
    {
        IndicatorType type = new IndicatorType( "IndicatorType", 100, false );

        indicatorTypeStore.save( type );

        Indicator indicatorA = createIndicator( 'A', type );
        indicatorStore.save( indicatorA );
        int idA = indicatorA.getId();
        indicatorA = indicatorStore.get( idA );
        assertEq( 'A', indicatorA );

        indicatorA.setName( "IndicatorB" );
        indicatorStore.update( indicatorA );
        indicatorA = indicatorStore.get( idA );
        assertNotNull( indicatorA );
        assertEquals( indicatorA.getName(), "IndicatorB" );
    }

    @Test
    public void testGetAndDeleteIndicator()
    {
        IndicatorType type = new IndicatorType( "IndicatorType", 100, false );

        indicatorTypeStore.save( type );

        Indicator indicatorA = createIndicator( 'A', type );
        Indicator indicatorB = createIndicator( 'B', type );
        Indicator indicatorC = createIndicator( 'C', type );

        indicatorStore.save( indicatorA );
        int idA = indicatorA.getId();
        indicatorStore.save( indicatorB );
        int idB = indicatorB.getId();
        indicatorStore.save( indicatorC );
        int idC = indicatorC.getId();

        assertNotNull( indicatorStore.get( idA ) );
        assertNotNull( indicatorStore.get( idB ) );
        assertNotNull( indicatorStore.get( idC ) );

        indicatorStore.delete( indicatorA );
        indicatorStore.delete( indicatorC );

        assertNull( indicatorStore.get( idA ) );
        assertNotNull( indicatorStore.get( idB ) );
        assertNull( indicatorStore.get( idC ) );
    }

    @Test
    public void testGetAllIndicators()
    {
        IndicatorType type = new IndicatorType( "IndicatorType", 100, false );

        indicatorTypeStore.save( type );

        Indicator indicatorA = createIndicator( 'A', type );
        Indicator indicatorB = createIndicator( 'B', type );

        indicatorStore.save( indicatorA );
        indicatorStore.save( indicatorB );

        List<Indicator> indicators = indicatorStore.getAll();

        assertEquals( indicators.size(), 2 );
        assertTrue( indicators.contains( indicatorA ) );
        assertTrue( indicators.contains( indicatorB ) );
    }

    @Test
    public void testGetIndicatorByName()
    {
        IndicatorType type = new IndicatorType( "IndicatorType", 100, false );

        indicatorTypeStore.save( type );

        Indicator indicatorA = createIndicator( 'A', type );
        Indicator indicatorB = createIndicator( 'B', type );

        indicatorStore.save( indicatorA );
        int idA = indicatorA.getId();
        indicatorStore.save( indicatorB );
        int idB = indicatorB.getId();

        assertNotNull( indicatorStore.get( idA ) );
        assertNotNull( indicatorStore.get( idB ) );

        indicatorA = indicatorStore.getByName( "IndicatorA" );
        assertNotNull( indicatorA );
        assertEq( 'A', indicatorA );

        Indicator indicatorC = indicatorStore.getByName( "IndicatorC" );
        assertNull( indicatorC );
    }

    @Test
    public void testGetIndicatorByShortName()
    {
        IndicatorType type = new IndicatorType( "IndicatorType", 100, false );

        indicatorTypeStore.save( type );

        Indicator indicatorA = createIndicator( 'A', type );
        Indicator indicatorB = createIndicator( 'B', type );

        indicatorStore.save( indicatorA );
        int idA = indicatorA.getId();
        indicatorStore.save( indicatorB );
        int idB = indicatorB.getId();

        assertNotNull( indicatorStore.get( idA ) );
        assertNotNull( indicatorStore.get( idB ) );

        indicatorA = indicatorStore.getByShortName( "IndicatorShortA" );
        assertNotNull( indicatorA );
        assertEq( 'A', indicatorA );

        Indicator indicatorC = indicatorStore.getByShortName( "IndicatorShortC" );
        assertNull( indicatorC );
    }

    @Test
    public void testGetIndicatorsWithoutGroups()
    {
        IndicatorType type = new IndicatorType( "IndicatorType", 100, false );

        indicatorTypeStore.save( type );

        Indicator indicatorA = createIndicator( 'A', type );
        Indicator indicatorB = createIndicator( 'B', type );
        Indicator indicatorC = createIndicator( 'C', type );

        indicatorStore.save( indicatorA );
        indicatorStore.save( indicatorB );
        indicatorStore.save( indicatorC );
        
        IndicatorGroup igA = createIndicatorGroup( 'A' );
        
        igA.addIndicator( indicatorA );
        igA.addIndicator( indicatorB );
        
        idObjectManager.save( igA );
        
        List<Indicator> indicators = indicatorStore.getIndicatorsWithoutGroups();
        
        assertEquals( 1, indicators.size() );
        assertTrue( indicators.contains( indicatorC ) );
    }
}
