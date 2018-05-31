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

	RowMapper<Grade> mapper = (ResultSet rs, int rowNum) -> {
		Grade g = new Grade();
		g.setId(rs.getInt("ID"));
		g.setCode(rs.getString("CODE"));
		g.setNbHeuresBase(rs.getBigDecimal("nbHeuresBase"));
		g.setTauxBase(rs.getBigDecimal("tauxBase"));
		return g;
	};

	@Autowired
	public GradeServiceJdbcTemplate(DataSource dataSource) {
		super();
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void sauvegarder(Grade nouveauGrade) {
		String sql = "INSERT INTO GRADE (CODE, nbHeuresBase, tauxBase) VALUES(?,?,?)";
		jdbcTemplate.update(sql, nouveauGrade.getCode(), nouveauGrade.getNbHeuresBase(), nouveauGrade.getTauxBase());

	}

	@Override
	public void mettreAJour(Grade grade) {
		String sqlUpdate = "UPDATE GRADE SET CODE = ? WHERE ID = ? ";
		jdbcTemplate.update(sqlUpdate, grade.getCode(), grade.getId());
	}

	@Override
	public List<Grade> lister() {
		String sql = "SELECT * FROM GRADE";
		List<Grade> grades = jdbcTemplate.query(sql, mapper);
		return grades;
	}

}
