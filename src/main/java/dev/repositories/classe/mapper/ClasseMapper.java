package dev.repositories.classe.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dev.entites.Classe;

public class ClasseMapper implements RowMapper<Classe> {

	@Override
	public Classe mapRow(ResultSet rs, int rowNum) throws SQLException {
		Classe c = new Classe();
		c.setId(new Long(rs.getInt("id")));
		c.setNom(rs.getString("nom"));
		return c;
	}

}