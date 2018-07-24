package dev.repositories.stagiaire;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import dev.entites.Stagiaire;
import dev.repositories.stagiaire.mapper.StagiaireMapper;

public class StagiaireRepositoryJdbc implements StagiaireRepository {

	private JdbcTemplate jdbcTemplate;

	public StagiaireRepositoryJdbc(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public List<Stagiaire> findAll() {
		String sql = "SELECT * FROM stagiaire";
		List<Stagiaire> stagiaires = jdbcTemplate.query(sql, new StagiaireMapper());
		return stagiaires;
	}

	@Override
	public void save(Stagiaire stagiaire) {
		// insertion
		String sql = "INSERT INTO stagiaire (nom, prenom, email, photo_url) VALUES(?,?,?,?)";
		jdbcTemplate.update(sql, stagiaire.getNom(), stagiaire.getPrenom(), stagiaire.getEmail(),
				stagiaire.getPhotoUrl());
	}

	@Override
	public void update(Stagiaire s) {
		String sqlUpdate = "UPDATE stagiaire SET nom = ?, prenom = ?, email = ?, photo_url = ? WHERE id = ? ";
		jdbcTemplate.update(sqlUpdate, s.getNom(), s.getPrenom(), s.getEmail(), s.getPhotoUrl(), s.getId());
	}

	@Override
	public void delete(Stagiaire stagiaire) {
		String sqlDelete = "DELETE FROM stagiaire WHERE id = ?";
		jdbcTemplate.update(sqlDelete, stagiaire.getId());
	}

	@Override
	public Optional<Stagiaire> findById(Long id) {
		String sqlbyId = "SELECT * FROM stagiaire WHERE id = ?";
		Stagiaire s = jdbcTemplate.queryForObject(sqlbyId, new StagiaireMapper(), id);
		return Optional.ofNullable(s);
	}

}
