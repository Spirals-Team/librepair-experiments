/*
 * Copyright (C) 2018 Max Planck Institute for Psycholinguistics, Nijmegen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.mpi.tg.eg.frinex.rest;

import nl.mpi.tg.eg.frinex.model.AudioData;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * @since Aug 13, 2018 4:34:41 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@RepositoryRestResource(collectionResourceRel = "audiodata", path = "audiodata")
public interface AudioDataRepository extends PagingAndSortingRepository<AudioData, Long> {

    @Override
    @RestResource(exported = false)
    public abstract <S extends AudioData> S save(S entity);

    @Override
    @RestResource(exported = false)
    public abstract <S extends AudioData> Iterable<S> save(Iterable<S> entities);

    @Override
    @RestResource(exported = false)
    public abstract void delete(Long id);

    @Override
    @RestResource(exported = false)
    public abstract void delete(AudioData entity);

    @Override
    @RestResource(exported = false)
    public abstract void delete(Iterable<? extends AudioData> entities);

    @Override
    @RestResource(exported = false)
    public abstract void deleteAll();
}
