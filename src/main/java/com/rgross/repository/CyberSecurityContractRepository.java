package com.rgross.repository;

import com.rgross.contract.CyberSecurityContract;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ryan_gross on 12/26/17.
 */
@Repository
public interface CyberSecurityContractRepository extends CrudRepository<CyberSecurityContract, Long> {

    List<CyberSecurityContract> findAllByDunsNumber(String dunsNumber);

}
