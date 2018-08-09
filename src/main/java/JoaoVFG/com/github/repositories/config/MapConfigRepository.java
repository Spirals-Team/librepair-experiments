package JoaoVFG.com.github.repositories.config;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import JoaoVFG.com.github.entity.config.MapConfig;

@Repository
public interface MapConfigRepository extends JpaRepository<MapConfig, Integer>{

	@Transactional(readOnly = true)
	@Query("SELECT mapConfig from MapConfig mapConfig where mapConfig.id = :id")
	public MapConfig buscaPorId(@Param("id") Integer id);
	
	
	@Transactional(readOnly = true)
	public MapConfig findBynameKey(String nameKey);
	
	
}
