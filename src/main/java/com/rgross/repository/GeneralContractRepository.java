package com.rgross.repository;

import com.rgross.contract.GeneralContract;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * Created by ryan_gross on 10/5/17.
 */

@Repository
public interface GeneralContractRepository extends CrudRepository<GeneralContract, Long> {

    List<GeneralContract> findAllByFiscalYear(int fiscalYear);

    @Query(value = "select c from GeneralContract c where c.placeOfPerformance IN (Select p from PlaceOfPerformance p where p.placeOfPerformanceLocation IN (select l from Location l where l.state = :state)) AND c.fiscalplaceOfPerformanceLocationYear = :fiscalYear)", nativeQuery = true)
    List<GeneralContract> findByStateAndFiscalYear(String state, int fiscalYear);

    @Query(value = "select c from GeneralContract c")
    List<GeneralContract> findAllContracts();

}


