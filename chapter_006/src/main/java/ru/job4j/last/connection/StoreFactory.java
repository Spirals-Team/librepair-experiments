package ru.job4j.last.connection;

import ru.job4j.last.config.Config;

/**
 * @author Yury Matskevich
 */
public class StoreFactory {
	private Config conf;

	/**
	 * Creates a new {@link StoreFactory} for creatting
	 * connections for a current db.
	 * @param confName a name of .properties file
	 * which contain all the settings and is located
	 * in resources falder
	 */
	public StoreFactory(String confName) {
		conf = new Config(confName);
	}

	/**
	 * Gets {@link IConnection} depending on a db
	 * @param name a name of a db
	 * @return {@link IConnection}
	 * @throws NoSuchDbException if there is no settings for a
	 * current db
	 */
	public IConnection createConnection(String name) throws NoSuchDbException {
		IConnection conn = null;
		if (name.equals("sqlite")) {
			conn = new ConnectionSqlite(conf);
		}
		/*
		we can add some other db here if we want
		to adapting the app to anothre db
		 */
		if (conn == null) {
			//throws some exeption releted to not existing such a db
			throw new NoSuchDbException("There is no settings for such db");
		}
		return conn;
	}
}
