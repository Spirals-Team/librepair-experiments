package dev.repositories.examen;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import dev.entites.Examen;

public class ExamenRepositoryJdbc implements ExamenRepository {

	private JdbcTemplate jdbcTemplate;

	public ExamenRepositoryJdbc(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public List<Examen> findAll() {
		String sql = "SELECT * FROM EXAMEN";
		return jdbcTemplate.query(sql, new ExamenMapper());
	}

	@Override
	public void save(Examen entite) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Examen entiteAvecId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Examen entite) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<Examen> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
