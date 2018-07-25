package dev.repositories.duel;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import dev.entites.Duel;
import dev.entites.Quizz;
import dev.entites.Stagiaire;

public class DuelRepositoryJdbc implements DuelRepository {

	RowMapper<Duel> mapper = (ResultSet rs, int rowNum) -> {
		Duel duel = new Duel();
		duel.setId(rs.getLong("id"));

		Stagiaire sA = new Stagiaire();
		sA.setId(rs.getLong("stagiairea_id"));
		duel.setStagiaireA(sA);

		Stagiaire sB = new Stagiaire();
		sA.setId(rs.getLong("stagiaireb_id"));
		duel.setStagiaireB(sB);

		Quizz q = new Quizz();
		q.setId(rs.getLong("quizz_id"));
		duel.setQuizz(q);

		return duel;
	};

	/**
	 * @param jdbcTemplate
	 */
	public DuelRepositoryJdbc(DataSource dataSource) {
		super();
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Duel> findAll() {
		String query = "select * from duel;";

		return this.jdbcTemplate.query(query, mapper);
	}

	@Override
	public void save(Duel duel) {
		String query = "insert into duel(stagiairea_id, stagiaireb_id, quizz_id) values (?, ?, ?);";
		jdbcTemplate.update(query, duel.getStagiaireA().getId(), duel.getStagiaireB().getId(), duel.getQuizz().getId());
	}

	@Override
	public void update(Duel duelAvecId) {
		String query = "update duel set stagiairea_id=?, stagiaireb_id=?, quizz_id=? where id=?;";
		jdbcTemplate.update(query, duelAvecId.getStagiaireA().getId(), duelAvecId.getStagiaireB().getId(),
				duelAvecId.getQuizz().getId(), duelAvecId.getId());
	}

	@Override
	public void delete(Duel duel) {
		String query = "delete from duel where id=?;";
		jdbcTemplate.update(query, duel.getId());
	}

	@Override
	public Optional<Duel> findById(Long id) {
		String query = "select * from duel where id=?;";
		return Optional.ofNullable(jdbcTemplate.queryForObject(query, mapper, id));
	}

}
