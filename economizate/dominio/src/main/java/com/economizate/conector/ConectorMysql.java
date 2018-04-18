package com.economizate.conector;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConectorMysql {
	
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost/economizate?";
	private static final String USER = "user";
	private static final String PASS = "userpass";
	
	private Connection conector;
	
	public Connection conectar() {
		try {
			Class.forName(DRIVER);
			
			conector = DriverManager.getConnection(URL, USER, PASS);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return conector;
	}
	
	public Connection getConector() {
		return conector!= null ? conector : conectar();
	}
	
}
