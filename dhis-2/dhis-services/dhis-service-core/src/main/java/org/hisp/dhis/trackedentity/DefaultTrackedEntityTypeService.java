package org.hisp.dhis.trackedentity;

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

import org.hisp.dhis.common.GenericIdentifiableObjectStore;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Chau Thu Tran
 */
@Transactional
public class DefaultTrackedEntityTypeService
    implements TrackedEntityTypeService
{
    // -------------------------------------------------------------------------
    // Dependencies
    // -------------------------------------------------------------------------

    private GenericIdentifiableObjectStore<TrackedEntityType> trackedEntityTypeStore;

    public void setTrackedEntityTypeStore( GenericIdentifiableObjectStore<TrackedEntityType> trackedEntityTypeStore )
    {
        this.trackedEntityTypeStore = trackedEntityTypeStore;
    }

    // -------------------------------------------------------------------------
    // TrackedEntityType
    // -------------------------------------------------------------------------

    @Override
    public int addTrackedEntityType( TrackedEntityType trackedEntityType )
    {
        trackedEntityTypeStore.save( trackedEntityType );

        return trackedEntityType.getId();
    }

    @Override
    public void deleteTrackedEntityType( TrackedEntityType trackedEntityType )
    {
        trackedEntityTypeStore.delete( trackedEntityType );
    }

    @Override
    public void updateTrackedEntityType( TrackedEntityType trackedEntityType )
    {
        trackedEntityTypeStore.update( trackedEntityType );
    }

    @Override
    public TrackedEntityType getTrackedEntityType( int id )
    {
        return trackedEntityTypeStore.get( id );
    }

    @Override
    public TrackedEntityType getTrackedEntityType( String uid )
    {
        return trackedEntityTypeStore.getByUid( uid );
    }

    @Override
    public TrackedEntityType getTrackedEntityByName( String name )
    {
        return trackedEntityTypeStore.getByName( name );
    }

    @Override
    public List<TrackedEntityType> getAllTrackedEntityType()
    {
        return trackedEntityTypeStore.getAll();
    }
}
