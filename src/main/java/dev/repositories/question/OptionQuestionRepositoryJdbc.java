package dev.repositories.question;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import dev.entites.OptionQuestion;

public class OptionQuestionRepositoryJdbc implements OptionQuestionRepository {

	// outil JdbcTemplate fourni par Spring JDBC
	private JdbcTemplate jdbcTemplate;

	@Autowired // injection de la source de données
	public OptionQuestionRepositoryJdbc(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public List<OptionQuestion> findAll() {
		String sql = "SELECT * FROM OPTION_QUESTION";
		List<OptionQuestion> optionQuestions = jdbcTemplate.query(sql, new OptionQuestionMapper());
		return optionQuestions;
	}

	@Override
	public void save(OptionQuestion entite) {

	}

	@Override
	public void update(OptionQuestion entiteAvecId) {

	}

	@Override
	public void delete(OptionQuestion entite) {

	}

	@Override
	public Optional<OptionQuestion> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
