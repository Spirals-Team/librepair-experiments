package dev.paie.service;

import java.math.BigDecimal;
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
		String sqlSave = "INSERT INTO grade (code, nbHeuresBase, tauxBase) VALUES(?, ?, ?)";
		jdbcTemplate.update(sqlSave, nouveauGrade.getCode(), nouveauGrade.getNbHeuresBase(),
				nouveauGrade.getTauxBase());
	}

	@Override
	public void mettreAJour(Grade grade) {
		String sqlUpdate = "UPDATE grade SET code=?, nbHeuresBase=?, tauxBase=? WHERE id=?";
		jdbcTemplate.update(sqlUpdate, grade.getCode(), grade.getNbHeuresBase(), grade.getTauxBase(), grade.getId());
	}

	@Override
	public List<Grade> lister() {

		RowMapper<Grade> mapper = (ResultSet rs, int rowNum) -> {
			Grade grade = new Grade();
			grade.setId(rs.getInt("id"));
			grade.setCode(rs.getString("code"));
			grade.setNbHeuresBase(new BigDecimal(rs.getDouble("nbHeuresBase")));
			grade.setTauxBase(new BigDecimal(rs.getDouble("tauxBase")));
			return grade;
		};

		String sqlList = "SELECT * from grade";
		List<Grade> listeRes = jdbcTemplate.query(sqlList, mapper);
		return listeRes;
	}

}