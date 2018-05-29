package dev.paie.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import dev.paie.entite.Grade;
import dev.paie.service.GradeService.GradeService;

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
		// TODO Auto-generated method stub
		String sqlSave = "INSERT INTO grade (code,nbHeuresBase,tauxBase) VALUES (? , ?, ?)";
		jdbcTemplate.update(sqlSave, nouveauGrade.getCode(), nouveauGrade.getNbHeuresBase(),
				nouveauGrade.getTauxBase());

	}

	@Override
	public void mettreAJour(Grade grade) {
		// TODO Auto-generated method stub
		String sqlUpdate = "UPDATE grade SET code= ?,nbHeuresBase= ?,tauxBase=? WHERE id=?";
		jdbcTemplate.update(sqlUpdate, grade.getCode(), grade.getNbHeuresBase(), grade.getTauxBase() , grade.getId());
	}

	@Override
	public List<Grade> lister() {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM grade";
		RowMapper<Grade> mapper = new RowMapper<Grade>() {
			@Override
			public Grade mapRow(ResultSet arg0, int arg1) throws SQLException {
				Grade grade = new Grade();
				grade.setId(arg0.getInt("id"));
				grade.setNbHeuresBase(arg0.getBigDecimal("nbHeuresBase"));
				grade.setTauxBase(arg0.getBigDecimal("tauxBase"));
				grade.setCode(arg0.getString("code"));
				return grade;
			}
		};
		return jdbcTemplate.query(sql, mapper);
	}

}