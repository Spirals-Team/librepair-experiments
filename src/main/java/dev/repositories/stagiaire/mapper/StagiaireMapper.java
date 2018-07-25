package dev.repositories.stagiaire.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dev.entites.Stagiaire;

public class StagiaireMapper implements RowMapper<Stagiaire> {

	@Override
	public Stagiaire mapRow(ResultSet rs, int rowNum) throws SQLException {
		Stagiaire s = new Stagiaire();
		s.setId(rs.getLong("id"));
		s.setNom(rs.getString("nom"));
		s.setPrenom(rs.getString("prenom"));
		s.setEmail(rs.getString("email"));
		s.setPhotoUrl(rs.getString("photo_url"));
		return s;

	}

}
