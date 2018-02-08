package com.rgross.repository;

import com.rgross.model.Location;
import com.rgross.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ryan_gross on 10/5/17.
 */
@Repository
public interface VendorRepository extends CrudRepository<Vendor, Long> {

    Vendor findByVendorName(String vendorName);

//    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM Vendor u WHERE u.vendorName= ?1 AND u.streetAddress= ?2 AND  u.vendorLocation = ?3")
//    boolean existsByVendorNameAndStreetAddressAndVendorLocation(String vendorName, String streetAddress, Location vendorLocation);


}
