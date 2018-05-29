package dev.paie.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dev.paie.entite.Grade;

public class GradeMapper implements RowMapper<Grade> {

	@Override
	public Grade mapRow(ResultSet rs, int rowNum) throws SQLException {
		Grade gr = new Grade();
		gr.setId(rs.getInt("ID"));
		gr.setCode(rs.getString("CODE"));
		gr.setNbHeuresBase(rs.getBigDecimal("NBHEURESBASE"));
		gr.setTauxBase(rs.getBigDecimal("TAUXBASE"));

		return gr;
	}

}
