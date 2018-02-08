package com.rgross.repository;

import com.rgross.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Ryan on 10/1/2017.
 */
@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {



    List<Location> findAllByZipCode(String zipCode);

    Location findByZipCode(String zipCode);

    Long countByStateAndCityAndZipCode(String city, String state, String zipCode);

//    @Query(value = "SELECT COUNT(*) FROM Location l WHERE l.zipCode = ?2 AND l.city = ?1 AND l.state = ?3", nativeQuery = true)
//    int searchForDuplicates(@Param("city") String city, @Param("zipCode")String zipCode, @Param("state")String state);


//    @Query(value = "SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM Location u WHERE u.city= ?1 AND u.county IN (SELECT * FROM County c WHERE c.fipsCode = ?2)", nativeQuery =true)
//    boolean existsByCityAndFipsCode(String city, String fipsCode);

    List<Location> findAllByState(String state);

}
