package academia.business.restriction;

import academia.model.Activity;
import academia.model.Restriction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestrictionRepository extends JpaRepository<Restriction, Long> {

    Restriction findByName(String name);

    //List<Restriction> findByActivity(Activity activity);

}
