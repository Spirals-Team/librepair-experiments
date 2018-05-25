package dev.paie.services;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dev.paie.entite.Grade;

public class GradeMapper implements RowMapper<Grade> {

	@Override
	public Grade mapRow(ResultSet rs, int row) throws SQLException {
		Grade g = new Grade();
		g.setId(rs.getInt("id"));
		g.setCode(rs.getString("code"));
		g.setNbHeuresBase(rs.getBigDecimal("nbheurebase"));
		g.setTauxBase(rs.getBigDecimal("tauxbase"));
		return g;
	}

}
