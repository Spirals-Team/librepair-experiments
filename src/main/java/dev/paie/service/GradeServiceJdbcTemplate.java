package dev.paie.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
		// TODO Auto-generated method stub
		String sql = "INSERT INTO Grade (code, nbHeuresBase, tauxBase, id) VALUES(?,?,?,?)";
		jdbcTemplate.update(sql, nouveauGrade.getCode(), nouveauGrade.getNbHeuresBase(), nouveauGrade.getTauxBase(),
				nouveauGrade.getId());

	}

	@Override
	public void mettreAJour(Grade grade) {
		// TODO Auto-generated method stub
		String sql = "UPDATE Grade set code = ?, nbHeuresBase = ?, tauxBase = ? WHERE id = ?";
		jdbcTemplate.update(sql, grade.getCode(), grade.getNbHeuresBase(), grade.getTauxBase(), grade.getId());

	}

	@Override
	public List<Grade> lister() {
		String sql = "SELECT * FROM Grade";
		List<Grade> grades = jdbcTemplate.query(sql, new GradeMapper());
		return grades;
	}

	@Override
	public void supprimer() {
		String sql = "delete FROM Grade";
		jdbcTemplate.update(sql);
	}
}
