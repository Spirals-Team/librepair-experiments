package dev.repositories.concours.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import dev.entites.Concours;
import dev.entites.Quizz;
import dev.entites.Stagiaire;
import dev.repositories.quizz.QuizzRepositoryJdbc;
import dev.repositories.stagiaire.StagiaireRepositoryJdbc;

public class ConcoursMapper implements RowMapper<Concours> {

	StagiaireRepositoryJdbc SRJ;
	QuizzRepositoryJdbc QRJ;

	@Override
	public Concours mapRow(ResultSet rs, int rowNum) throws SQLException {
		Concours c = new Concours();
		c.setId(rs.getLong("id"));
		c.setTitre(rs.getString("titre"));
		List<Stagiaire> participants = null;
		List<Quizz> quizzes = null;

		/*
		 * String sqlbyId =
		 * "SELECT * FROM concours_quizz  WHERE id_concours = ?";
		 * quizzes.add(QRJ.findById(jdbcTemplate.query(sqlbyId)));
		 *
		 * String sqlbyId =
		 * "SELECT * FROM concours_stagiaire  WHERE id_concours = ?"; Concours
		 * concours = jdbcTemplate.queryForObject(sqlbyId, new ConcoursMapper(),
		 * rs.getLong("id"));
		 *
		 * for (Stagiaire s : entiteAvecId.getParticipants()) { String
		 * participant_sql =
		 * "INSERT INTO concours_stagiaire (id_concours,id_stagiaire) VALUES(?,?)"
		 * ; jdbcTemplate.update(participant_sql, entiteAvecId.getId(),
		 * s.getId()); } for (Quizz q : entiteAvecId.getQuizzes()) { String
		 * quizz_sql =
		 * "INSERT INTO concours_quizz (id_concours,id_quizz) VALUES(?,?)";
		 * jdbcTemplate.update(quizz_sql, entiteAvecId.getId(), q.getId()); }
		 */

		c.setParticipants(participants);
		c.setQuizzes(quizzes);

		return c;

	}

}
