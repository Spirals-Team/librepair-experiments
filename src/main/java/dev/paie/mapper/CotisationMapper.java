package dev.paie.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dev.paie.entite.Cotisation;

public class CotisationMapper implements RowMapper<Cotisation> {
	@Override
	public Cotisation mapRow(ResultSet rs, int rowNum) throws SQLException {
		Cotisation g = new Cotisation(rowNum, null, null, null, null);
		g.setId(rs.getInt("ID"));
		g.setCode(rs.getString("CODE"));
		g.setLibelle(rs.getString("LIBELLE"));
		g.setTauxPatronal(rs.getBigDecimal("TAUXPATRONAL"));
		g.setTauxSalarial(rs.getBigDecimal("TAUXSALARIAL"));
		return g;
	}
}
