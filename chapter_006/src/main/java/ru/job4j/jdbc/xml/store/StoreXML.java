package ru.job4j.jdbc.xml.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.jdbc.xml.connect.ConnectionSqlite;
import ru.job4j.jdbc.xml.entity.Entries;
import ru.job4j.jdbc.xml.entity.Entry;
import ru.job4j.jdbc.xml.load.Config;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yury Matskevich
 */
public class StoreXML {
	private static final Logger LOG = LoggerFactory
			.getLogger(StoreXML.class);
	private Config conf;
	private File file;
	private String querySelect;

	public StoreXML(Config conf, File file) {
		this.conf = conf;
		this.file = file;
		querySelect = conf.getProperty("db.select");
	}

	/**
	 * Creates .xml file from date of db
	 */
	public void createXML() {
		save(getList());
	}

	/**
	 * Creates .xml file from List of {@link Entry}
	 * @param list a list of {@link Entry}
	 */
	private void save(List<Entry> list) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Entries.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(new Entries(list), file);
		} catch (JAXBException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * Gets List of {@link Entry} rom a db's date
	 * @return List of {@link Entry}
	 */
	private List<Entry> getList() {
		List<Entry> entries = new ArrayList<>();
		try (Connection conn = new ConnectionSqlite(conf).getConnection();
			 Statement st = conn.createStatement();
			 ResultSet rs = st.executeQuery(querySelect)) {
			while (rs.next()) {
				entries.add(new Entry(rs.getInt(1)));
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return entries;
	}
}
