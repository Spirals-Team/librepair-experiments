package py.org.fundacionparaguaya.pspserver.network.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import py.org.fundacionparaguaya.pspserver.network.entities.ApplicationEntity;

import java.util.List;

/**
 * Created by rodrigovillalba on 8/27/17.
 */
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {

    ApplicationEntity findById(Long id);

    List<ApplicationEntity> findByIsActive(boolean isActive);

    List<ApplicationEntity> findByIsHubAndIsActive(boolean isHub, boolean isActive);

    List<ApplicationEntity> findByIsPartnerAndIsActive(boolean isPartner, boolean isActive);

}
