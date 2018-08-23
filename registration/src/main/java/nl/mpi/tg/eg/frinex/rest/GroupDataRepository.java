/*
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics
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

import java.util.List;
import nl.mpi.tg.eg.frinex.model.GroupData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * @since Nov 25, 2016 3:13:46 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@RepositoryRestResource(collectionResourceRel = "groupdata", path = "groupdata")
public interface GroupDataRepository extends PagingAndSortingRepository<GroupData, Long> {

    @Override
    List<GroupData> findAll();

    public Page<GroupData> findByGroupUUID(@Param("groupUUID") String groupUUID, Pageable pageable);

    @Override
    @RestResource(exported = false)
    public abstract <S extends GroupData> S save(S entity);

    @Override
    @RestResource(exported = false)
    public abstract <S extends GroupData> Iterable<S> save(Iterable<S> entities);

    @Override
    @RestResource(exported = false)
    public abstract void delete(Long id);

    @Override
    @RestResource(exported = false)
    public abstract void delete(GroupData entity);

    @Override
    @RestResource(exported = false)
    public abstract void delete(Iterable<? extends GroupData> entities);

    @Override
    @RestResource(exported = false)
    public abstract void deleteAll();
}
