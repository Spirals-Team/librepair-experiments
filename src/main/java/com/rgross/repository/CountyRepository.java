package com.rgross.repository;

import com.rgross.model.County;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by ryan_gross on 11/11/17.
 */
@Repository
public interface CountyRepository extends CrudRepository<County, Long>
{

    County findByfipsNumber(String fipsNumber);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN 'true' ELSE 'false' END FROM County c WHERE c.fipsNumber = ?1")
    boolean containsCounty(String fipsNumber);


}
