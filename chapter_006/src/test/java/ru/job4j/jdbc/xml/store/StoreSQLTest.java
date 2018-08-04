package ru.job4j.jdbc.xml.store;

import org.junit.Test;
import ru.job4j.jdbc.xml.connect.ConnectionSqlite;
import ru.job4j.jdbc.xml.load.Config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Yury Matskevich
 */
public class StoreSQLTest {
	//configuration file of connection
	private Config config = new Config("/configLite.properties");

	//gets all the fields from a db and places them in an ArrayList
	private List<Integer> getListOfFields() throws SQLException {
		List<Integer> list = new ArrayList<>();
		try (Connection conn = new ConnectionSqlite(config).getConnection();
			 Statement st = conn.createStatement();
			 ResultSet rs = st.executeQuery("SELECT * FROM entry")) {
			while (rs.next()) {
				list.add(rs.getInt(1));
			}
		}
		return list;
	}

	@Test
	public void whenGenerate5ElemsThenThereAre5FieldsInDb()
			throws SQLException {
		new StoreSQL(config).generate(7);
		List<Integer> actualList = getListOfFields();
		assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7), actualList);
	}
}