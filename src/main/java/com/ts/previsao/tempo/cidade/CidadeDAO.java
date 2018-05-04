package com.ts.previsao.tempo.cidade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ts.previsao.tempo.DataBaseConnection;

public class CidadeDAO extends DataBaseConnection {

	private static CidadeDAO instance;

	public boolean createTableCidade() {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("create table if not exists ").append("tbcidade");
		sqlBuilder.append("(");
		sqlBuilder.append("id int not null").append(",");
		sqlBuilder.append("nome varchar(255) not null").append(",");
		sqlBuilder.append("uf char(2) not null").append(",");
		sqlBuilder.append("atualizacao varchar(10) not null").append(",");
		sqlBuilder.append("primary key (id)");
		sqlBuilder.append(")");
		try (Connection conn = DriverManager.getConnection(URL); Statement stmt = conn.createStatement()) {
			stmt.execute(sqlBuilder.toString());
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public boolean insertCidade(CidadeRepository cidade) {
		String sql = "insert or ignore into tbcidade(id, nome, uf, atualizacao) values(?, ?, ?, ?)";
		try (Connection conn = this.conecta(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, cidade.getId());
			stmt.setString(2, cidade.getNome());
			stmt.setString(3, cidade.getUf());
			stmt.setString(4, cidade.getAtualizacao());
			stmt.executeUpdate();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public List<CidadeRepository> selectAllCidade() {
		String sql = "select * from tbcidade";
		List<CidadeRepository> cidades = new ArrayList<>();
		try (Connection conn = this.conecta();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			CidadeRepository cidade;
			while (rs.next()) {
				cidade = new CidadeRepository();
				cidade.setId(rs.getInt("id"));
				cidade.setNome(rs.getString("nome"));
				cidade.setUf(rs.getString("uf"));
				cidade.setAtualizacao(rs.getString("atualizacao"));
				cidades.add(cidade);
			}
		} catch (Exception e) {
			return null;
		}
		return cidades;
	}

	public boolean atualizaCidade(CidadeRepository cidade) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("UPDATE ").append("tbcidade ").append("set ");
		sqlBuilder.append("nome = ?").append(",");
		sqlBuilder.append("uf = ?").append(",");
		sqlBuilder.append("atualizacao = ? ");
		sqlBuilder.append("where id = ?");
		try (Connection conn = this.conecta(); PreparedStatement stmt = conn.prepareStatement(sqlBuilder.toString())) {
			stmt.setString(1, cidade.getNome());
			stmt.setString(2, cidade.getUf());
			stmt.setString(3, cidade.getAtualizacao());
			stmt.setInt(4, cidade.getId());
			stmt.executeUpdate();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static CidadeDAO getCidadeDao() {
		if (instance == null) {
			instance = new CidadeDAO();
		}
		return instance;
	}
}
