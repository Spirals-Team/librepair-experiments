package dev.repositories.concours;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import dev.entites.Concours;
import dev.entites.Quizz;
import dev.entites.Stagiaire;
import dev.repositories.concours.mapper.ConcoursMapper;
import dev.repositories.quizz.QuizzRepositoryJdbc;
import dev.repositories.stagiaire.StagiaireRepositoryJdbc;

public class ConcoursRepositoryJdbc implements ConcoursRepository {

	private JdbcTemplate jdbcTemplate;
	StagiaireRepositoryJdbc SRJ;
	QuizzRepositoryJdbc QRJ;

	public ConcoursRepositoryJdbc(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public List<Concours> findAll() {
		String sql = "SELECT * FROM Concours";
		String sql2 = "SELECT id_stagiaire FROM concours_stagiaire";
		String sql3 = "SELECT id_quizz FROM concours_quizz";
		List<Concours> cc = jdbcTemplate.query(sql, new ConcoursMapper());
		return cc;

	}

	@Override
	public void save(Concours entite) {
		String concours_sql = "INSERT INTO concours (titre) VALUES(?)";
		jdbcTemplate.update(concours_sql, entite.getTitre());
		for (Stagiaire s : entite.getParticipants()) {
			String participant_sql = "INSERT INTO concours_stagiaire (id_concours,id_stagiaire) VALUES(?,?)";
			jdbcTemplate.update(participant_sql, entite.getId(), s.getId());
		}
		for (Quizz q : entite.getQuizzes()) {
			String quizz_sql = "INSERT INTO concours_quizz (id_concours,id_quizz) VALUES(?,?)";
			jdbcTemplate.update(quizz_sql, entite.getId(), q.getId());
		}
	}

	@Override
	public void update(Concours entiteAvecId) {
		String concours_sql = "UPDATE concours SET titre=? WHERE id = ?";
		jdbcTemplate.update(concours_sql, entiteAvecId.getId());

		String sqlDelete = "DELETE FROM concours_stagiaire,concours_quizz WHERE id_concours = ?";
		jdbcTemplate.update(sqlDelete, entiteAvecId.getId());

		for (Stagiaire s : entiteAvecId.getParticipants()) {
			String participant_sql = "INSERT INTO concours_stagiaire (id_concours,id_stagiaire) VALUES(?,?)";
			jdbcTemplate.update(participant_sql, entiteAvecId.getId(), s.getId());
		}
		for (Quizz q : entiteAvecId.getQuizzes()) {
			String quizz_sql = "INSERT INTO concours_quizz (id_concours,id_quizz) VALUES(?,?)";
			jdbcTemplate.update(quizz_sql, entiteAvecId.getId(), q.getId());
		}

	}

	@Override
	public void delete(Concours entite) {
		String sqlDelete = "DELETE FROM concours_stagiaire,concours_quizz WHERE id_concours = ?";
		jdbcTemplate.update(sqlDelete, entite.getId());
		String sqlDelete2 = "DELETE FROM concours WHERE id = ?";
		jdbcTemplate.update(sqlDelete2, entite.getId());

	}

	@Override
	public Optional<Concours> findById(Long id) {
		String sqlbyId = "SELECT * FROM concours WHERE id = ?";
		Concours c = jdbcTemplate.queryForObject(sqlbyId, new ConcoursMapper(), id);
		return Optional.ofNullable(c);
	}

}
