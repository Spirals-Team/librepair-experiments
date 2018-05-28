package dev.paie.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import dev.paie.entite.Grade;
import dev.paie.util.GradeMapper;

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
		String sqlInsert = "INSERT INTO grade (id, CODE, NB_HEURES_BASE, TAUX_BASE) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sqlInsert, nouveauGrade.getId(), nouveauGrade.getCode(), nouveauGrade.getNbHeuresBase(),
				nouveauGrade.getTauxBase());
	}

	@Override
	public void mettreAJour(Grade grade) {
		String sqlUpdate = "UPDATE grade SET CODE = ?, NB_HEURES_BASE = ?, TAUX_BASE = ? WHERE id = ?";
		jdbcTemplate.update(sqlUpdate, grade.getCode(), grade.getNbHeuresBase(), grade.getTauxBase(), grade.getId());
	}

	@Override
	public List<Grade> lister() {
		String sqlSelect = "SELECT * FROM grade";
		List<Grade> listGrade = jdbcTemplate.query(sqlSelect, new GradeMapper());
		return listGrade;
	}

}
