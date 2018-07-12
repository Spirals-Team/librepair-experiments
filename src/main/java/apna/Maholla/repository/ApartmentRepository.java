package apna.Maholla.repository;

import apna.Maholla.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {

    Apartment findByApartmentuniqueid(String apartmentuniqueid);
    Apartment findFirstById(Integer id);

}
