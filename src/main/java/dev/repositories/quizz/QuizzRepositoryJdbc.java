package dev.repositories.quizz;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import dev.entites.Quizz;

public class QuizzRepositoryJdbc implements QuizzRepository {

	// outil JdbcTemplate fourni par Spring JDBC
	private JdbcTemplate jdbcTemplate;

	@Autowired // injection de la source de données
	public QuizzRepositoryJdbc(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public List<Quizz> findAll() {
		String sql = "SELECT * FROM quizz";
		// exécution de la requête et récupération du résultat
		// this.jdbcTemplate.query(sql, new QuizzMapper())
		throw new NotImplementedException("Implémentez la méthode avant de l'utiliser, bon dieu");
	}

	@Override
	public void save(Quizz entite) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Quizz entiteAvecId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Quizz entite) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<Quizz> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
