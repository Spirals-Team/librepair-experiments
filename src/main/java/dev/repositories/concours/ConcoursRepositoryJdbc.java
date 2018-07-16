package dev.repositories.concours;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import dev.entites.Concours;
import dev.repositories.concours.mapper.ConcoursMapper;

public class ConcoursRepositoryJdbc implements ConcoursRepository {

	private JdbcTemplate jdbcTemplate;

	public ConcoursRepositoryJdbc(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public List<Concours> findAll() {
		String sql = "SELECT * FROM Concours";
		List<Concours> concours = jdbcTemplate.query(sql, new ConcoursMapper());
		return concours;

	}

	@Override
	public void save(Concours entite) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Concours entiteAvecId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Concours entite) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<Concours> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
