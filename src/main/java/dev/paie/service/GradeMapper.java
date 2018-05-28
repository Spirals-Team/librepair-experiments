package dev.paie.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dev.paie.entite.Grade;

public class GradeMapper implements RowMapper<Grade> {

	@Override
	public Grade mapRow(ResultSet res, int rowNum) throws SQLException {
		Grade grade = new Grade();
		grade.setId(res.getInt("id"));
		grade.setCode(res.getString("code"));
		grade.setNbHeuresBase(res.getBigDecimal("nbHeuresBase"));
		grade.setTauxBase(res.getBigDecimal("tauxBase"));
		return grade;
	}

}
