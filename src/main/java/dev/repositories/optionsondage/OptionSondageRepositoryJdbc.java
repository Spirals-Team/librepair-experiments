package dev.repositories.optionsondage;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import dev.entites.OptionSondage;

public class OptionSondageRepositoryJdbc implements OptionSondageRepository {

	private JdbcTemplate jdbcTemplate;

	public OptionSondageRepositoryJdbc(DataSource datasource) {
		super();
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public List<OptionSondage> findAll() {
		String query = "SELECT * FROM option_sondage";
		RowMapper<OptionSondage> mapper = (ResultSet rs, int rowNum) -> {
			OptionSondage os = new OptionSondage();
			os.setId(rs.getLong("id"));
			os.setLibelle(rs.getString("libelle"));
			os.setDescription(rs.getString("description"));
			return os;
		};
		return jdbcTemplate.query(query, mapper);
	}

	@Override
	public void save(OptionSondage entite) {
		String query = "INSERT INTO option_sondage (libelle, description) values (?,?)";
		jdbcTemplate.update(query, entite.getLibelle(), entite.getDescription());

	}

	@Override
	public void update(OptionSondage entiteAvecId) {
		String query = "UPDATE option_sondage set libelle=?, description=? where id=?";
		jdbcTemplate.update(query, entiteAvecId.getLibelle(), entiteAvecId.getDescription(), entiteAvecId.getId());
	}

	@Override
	public void delete(OptionSondage entite) {
		String query1 = "DELETE FROM sondage_option_sondage WHERE id_option_sondage = ?";
		jdbcTemplate.update(query1, entite.getId());

		String query2 = "DELETE FROM option_sondage WHERE id = ?";
		jdbcTemplate.update(query2, entite.getId());
	}

	@Override
	public Optional<OptionSondage> findById(Long id) {
		throw new NotImplementedException("non implémenté");
	}

}
