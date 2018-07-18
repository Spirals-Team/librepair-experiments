package dev.repositories.concours.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dev.entites.Concours;

public class ConcoursMapper implements RowMapper<Concours> {

	@Override
	public Concours mapRow(ResultSet rs, int rowNum) throws SQLException {
		Concours c = new Concours();
		c.setId(rs.getLong("id"));
		c.setTitre(rs.getString("titre"));
		// c.setPrenom(rs.getString("prenom"));
		// c.setEmail(rs.getString("email"));
		// c.setPhotoUrl(rs.getString("photo_url"));
		return c;

	}

}
