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
import nl.mpi.tg.eg.frinex.model.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

/**
 * @since Jun 30, 2015 4:43:06 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@RepositoryRestResource(collectionResourceRel = "participants", path = "participants")
public interface ParticipantRepository extends PagingAndSortingRepository<Participant, Long> {

//    Participant findById(@Param("id") long id);
    Page<Participant> findByStaleCopy(@Param("staleCopy") boolean staleCopy, Pageable pageable);

    List<Participant> findByUserId(@Param("userId") String userId);

    List<Participant> findByStaleCopyAndUserId(@Param("staleCopy") boolean staleCopy, @Param("userId") String userId);

//    @Query("select distinct new Participant() from Participant order by submitDate desc")
    List<Participant> findAllByOrderBySubmitDateDesc();

    @Query("select distinct userId from Participant")
    List<String> findDistinctUserIdByOrderBySubmitDateDesc();

//    int countByWorkerId(@Param("workerId") String workerId);
    @Transactional
    @Modifying
    @Query("update Participant set staleCopy = true where userId = :userId")
    void setAsStaleByUserId(@Param("userId") String userId);

    @Override
    @RestResource(exported = false)
    public abstract <S extends Participant> S save(S entity);

    @Override
    @RestResource(exported = false)
    public abstract <S extends Participant> Iterable<S> save(Iterable<S> entities);

    @Override
    @RestResource(exported = false)
    public abstract void delete(Long id);

    @Override
    @RestResource(exported = false)
    public abstract void delete(Participant entity);

    @Override
    @RestResource(exported = false)
    public abstract void delete(Iterable<? extends Participant> entities);

    @Override
    @RestResource(exported = false)
    public abstract void deleteAll();
}
