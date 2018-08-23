/*
 * Copyright (C) 2015 Max Planck Institute for Psycholinguistics
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
import nl.mpi.tg.eg.frinex.model.ScreenData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * @since Jul 2, 2015 3:02:49 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@RepositoryRestResource(collectionResourceRel = "screenviews", path = "screenviews")
public interface ScreenDataRepository extends PagingAndSortingRepository<ScreenData, Long> {

    @Query("select distinct new ScreenData(userId, screenName, viewDate) from ScreenData order by viewDate asc")
    List<ScreenData> findAllDistinctRecords();

    @Query("select distinct new ScreenData(userId, screenName, viewDate) from ScreenData where userId = :userId order by viewDate asc")
    List<ScreenData> findByUserIdOrderByViewDateAsc(@Param("userId") String userId);

//    @Query("select count distinct new ScreenData(userId, screenName, viewDate) from ScreenData where userId = :userId and screenName = :screenName")
    int countDistinctViewDateByUserIdAndScreenName(@Param("userId") String userId, @Param("screenName") String screenName);

    @Override
    @RestResource(exported = false)
    public abstract <S extends ScreenData> S save(S entity);

    @Override
    @RestResource(exported = false)
    public abstract <S extends ScreenData> Iterable<S> save(Iterable<S> entities);

    @Override
    @RestResource(exported = false)
    public abstract void delete(Long id);

    @Override
    @RestResource(exported = false)
    public abstract void delete(ScreenData entity);

    @Override
    @RestResource(exported = false)
    public abstract void delete(Iterable<? extends ScreenData> entities);

    @Override
    @RestResource(exported = false)
    public abstract void deleteAll();
}
