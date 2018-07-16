package dev.repositories.examen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import dev.entites.Classe;
import dev.entites.Examen;
import dev.entites.Note;
import dev.entites.Quizz;

public class ExamenMapper implements RowMapper<Examen> {

	@Override
	public Examen mapRow(ResultSet rs, int rowNum) throws SQLException {
		Examen exam = new Examen();
		exam.setTitre(rs.getString("titre"));
		exam.setId(new Long(rs.getInt("id")));
		Classe classeTemp = new Classe();
		classeTemp.setId(new Long(rs.getInt("id_classe")));
		exam.setClasse(classeTemp);
		exam.setQuizz(new Quizz());
		List<Note> listeNote = new ArrayList<>();
		exam.setNotes(listeNote);
		return exam;
	}

}
