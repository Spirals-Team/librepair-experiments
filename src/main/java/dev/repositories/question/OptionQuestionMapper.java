package dev.repositories.question;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dev.entites.OptionQuestion;

public class OptionQuestionMapper implements RowMapper<OptionQuestion> {

	@Override
	public OptionQuestion mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		OptionQuestion oq = new OptionQuestion();
		oq.setLibelle(rs.getString("libelle"));
		int bit = rs.getByte("ok");
		if (bit == 0) {
			oq.setOk(false);
		} else {
			oq.setOk(true);
		}
		return oq;
	}

}
