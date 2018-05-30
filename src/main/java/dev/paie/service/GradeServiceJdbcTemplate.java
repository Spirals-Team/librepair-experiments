package dev.paie.service;

import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import dev.paie.entite.Grade;

@Service
public class GradeServiceJdbcTemplate implements GradeService {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public GradeServiceJdbcTemplate(DataSource dataSource) {
		super();
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void sauvegarder(Grade nouveauGrade) {
		String sql = "INSERT INTO grade VALUES ('" + nouveauGrade.getId() + "','" + nouveauGrade.getCode() + "','"
				+ nouveauGrade.getNbHeuresBase() + "','" + nouveauGrade.getTauxBase() + "')";
		jdbcTemplate.execute(sql);

	}

	@Override
	public void mettreAJour(Grade grade) {
		String sql = "Update grade Set code = '" + grade.getCode() + "', nb_heures_base = '" + grade.getNbHeuresBase()
				+ "', taux_base = '" + grade.getTauxBase() + "' WHERE id = '" + grade.getId() + "'";
		jdbcTemplate.execute(sql);

	}

	@Override
	public List<Grade> lister() {
		String sql = "SELECT * FROM GRADE";
		RowMapper<Grade> mapper = (ResultSet rs, int rowNum) -> {
			Grade p = new Grade();
			p.setId(rs.getInt("ID"));
			p.setCode(rs.getString("code"));
			p.setNbHeuresBase(rs.getBigDecimal("nb_heures_base"));
			p.setTauxBase(rs.getBigDecimal("taux_base"));
			return p;
		};
		List<Grade> listGrade = jdbcTemplate.query(sql, mapper);
		return listGrade;
	}
}
