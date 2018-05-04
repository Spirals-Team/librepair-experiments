package com.ts.previsao.tempo.previsao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ts.previsao.tempo.DataBaseConnection;

public class PrevisaoDAO extends DataBaseConnection {

	private static PrevisaoDAO instance;

	public boolean createTablePrevisao() {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("create table if not exists ").append("tbprevisao");
		sqlBuilder.append("(");
		sqlBuilder.append("id int not null").append(",");
		sqlBuilder.append("dia varchar(10) not null").append(",");
		sqlBuilder.append("tempo char(3) not null").append(",");
		sqlBuilder.append("minima float not null").append(",");
		sqlBuilder.append("maxima float not null").append(",");
		sqlBuilder.append("iuv float not null").append(",");
		sqlBuilder.append("primary key (id, dia)").append(",");
		sqlBuilder.append("foreign key (id) references tbcidade(id)");
		sqlBuilder.append(")");
		try (Connection conn = DriverManager.getConnection(URL); Statement stmt = conn.createStatement()) {
			stmt.execute(sqlBuilder.toString());
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public boolean insertPrevisao(PrevisaoRepository previsao) {
		String sql = "insert or ignore into tbprevisao(id, dia, tempo, minima, maxima, iuv) values(?, ?, ?, ?, ?, ?)";
		try (Connection conn = this.conecta(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, previsao.getId());
			stmt.setString(2, previsao.getDia());
			stmt.setString(3, previsao.getTempo());
			stmt.setDouble(4, previsao.getMinima());
			stmt.setDouble(5, previsao.getMaxima());
			stmt.setDouble(6, previsao.getIuv());
			stmt.executeUpdate();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public List<PrevisaoRepository> selectAllPrevisao(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from tbprevisao where id=").append(id);
		List<PrevisaoRepository> previsoes = new ArrayList<>();
		try (Connection conn = this.conecta();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString())) {
			PrevisaoRepository previsao;
			while (rs.next()) {
				previsao = new PrevisaoRepository();
				previsao.setDia(rs.getString("dia"));
				previsao.setId(rs.getInt("id"));
				previsao.setIuv(rs.getDouble("iuv"));
				previsao.setMaxima(rs.getDouble("maxima"));
				previsao.setMinima(rs.getDouble("minima"));
				previsao.setTempo(rs.getString("tempo"));
				previsoes.add(previsao);
			}
		} catch (Exception e) {
			return null;
		}
		return previsoes;
	}

	public boolean removeAllPrevisao(Integer id) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("delete from tbprevisao where id=").append(id);
		try (Connection conn = this.conecta(); PreparedStatement stmt = conn.prepareStatement(sqlBuilder.toString())) {
			stmt.executeUpdate();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static PrevisaoDAO getPrevisaoDAO() {
		if (instance == null) {
			instance = new PrevisaoDAO();
		}
		return instance;
	}
}
