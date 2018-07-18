package dev.repositories.classe;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import dev.entites.Classe;
import dev.repositories.classe.mapper.ClasseMapper;

public class ClasseRepositoryJdbc implements ClasseRepository {

	// NE PAS IMPLEMENTER A CE STADE, NOUS ECRIRONS LE TEST AVANT
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public ClasseRepositoryJdbc(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public List<Classe> findAll() {
		String sqlFind = "SELECT * FROM classe";
		List<Classe> Classes = jdbcTemplate.query(sqlFind, new ClasseMapper());
		return Classes;
	}

	@Override
	public void save(Classe classe) {
		// insertion
		String sqlSave = "INSERT INTO classe (nom) VALUES(?)";
		jdbcTemplate.update(sqlSave, classe.getNom());
	}

	@Override
	public void update(Classe classe) {
		String sqlUpdate = "UPDATE classe SET nom = ?";
		jdbcTemplate.update(sqlUpdate, classe.getNom());
	}

	@Override
	public void delete(Classe classe) {
		String sqlDelete = "DELETE FROM classe WHERE id = ?";
		jdbcTemplate.update(sqlDelete, classe.getId());
	}

	@Override
	public Optional<Classe> findById(Long id) {
		String sqlbyId = "SELECT * FROM classe WHERE id = ?";
		Classe classe = jdbcTemplate.queryForObject(sqlbyId, new ClasseMapper(), id);
		return Optional.ofNullable(classe);
	}
}
