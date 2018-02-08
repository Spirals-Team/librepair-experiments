package com.rgross.repository;

import com.rgross.model.NaicsCode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ryan_gross on 11/18/17.
 */

@Repository
public interface NaicsCodeRepository extends CrudRepository<NaicsCode, Long> {

    NaicsCode findByNaicsCode(Integer naicsCode);
//
//    @Query(value ="SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM NaicsCode c WHERE c.naicsCode = ?1", nativeQuery = true)
//    boolean containsNaicsCode(Integer naicsCode);

}
