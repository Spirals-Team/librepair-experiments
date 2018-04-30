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
import nl.mpi.tg.eg.frinex.model.TagPairData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @since Jul 21, 2015 4:42:51 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@RepositoryRestResource(collectionResourceRel = "tagevents", path = "tagevents")
public interface TagPairRepository extends PagingAndSortingRepository<TagPairData, Long> {

    @Query("select distinct new TagPairData(userId, screenName, eventTag, tagValue1, tagValue2, eventMs, tagDate) from TagPairData order by tagDate asc")
    List<TagPairData> findAllDistinctRecords();

    @Query("select distinct new TagPairData(userId, screenName, eventTag, tagValue1, tagValue2, eventMs, tagDate) from TagPairData where userId = :userId order by tagDate asc, eventTag desc")
    List<TagPairData> findByUserIdOrderByTagDateAsc(@Param("userId") String userId);

    @Query("select distinct new TagPairData(userId, screenName, eventTag, tagValue1, tagValue2, eventMs, tagDate) from TagPairData where userId = :userId and eventTag = :eventTag order by tagDate asc")
    List<TagPairData> findByUserIdAndEventTagOrderByTagDateAsc(@Param("userId") String userId, @Param("eventTag") String eventTag);
}
