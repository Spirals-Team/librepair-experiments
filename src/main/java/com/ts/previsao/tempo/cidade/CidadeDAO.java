package com.ts.previsao.tempo.cidade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ts.previsao.tempo.database.DataBaseConnectionFactory;

public class CidadeDAO {
	private Connection connection;

	public CidadeDAO() throws ClassNotFoundException, SQLException {
		this.connection = new DataBaseConnectionFactory().conectar();
	}

	public boolean createTablePrevisao() throws SQLException {
		Statement stmt = this.connection.createStatement();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("create table if not exists").append("tbcidade");
		sqlBuilder.append("(");
		sqlBuilder.append("id int not null").append(",");
		sqlBuilder.append("nome varchar(255) not null").append(",");
		sqlBuilder.append("uf char(2) not null").append(",");
		sqlBuilder.append("atualizacao date not null").append(",");
		sqlBuilder.append("primary key (id)");
		sqlBuilder.append(")");
		stmt.executeUpdate(sqlBuilder.toString());
		stmt.close();
		return true;
	}

	public boolean insertCidade(CidadeRepository cidade) throws SQLException {
		/* o campo atualizacao irá receber o valor padrão, ou seja, null */
		String sql = "insert or ignore into tbcidade(id,nome,uf) values(?,?,?)";
		PreparedStatement stmt = this.connection.prepareStatement(sql);
		stmt.setInt(1, cidade.getId());
		stmt.setString(2, cidade.getNome());
		stmt.setString(3, cidade.getUf());
		stmt.execute();
		stmt.close();
		this.connection.commit();
		return true;
	}

	public List<CidadeRepository> selectAllCidade() throws SQLException {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select * from tbcidade");
		Statement stmt = this.connection.createStatement();
		ResultSet rs = stmt.executeQuery(sqlBuilder.toString());
		List<CidadeRepository> lista = new ArrayList<>();
		CidadeRepository cidade;
		while (rs.next()) {
			cidade = new CidadeRepository();
			cidade.setId(rs.getInt("id"));
			cidade.setNome(rs.getString("nome"));
			cidade.setUf(rs.getString("uf"));
			cidade.setAtualizacao(rs.getString("atualizacao"));
			lista.add(cidade);
		}
		rs.close();
		stmt.close();
		this.connection.commit();
		return lista;
	}

	public CidadeRepository selectById(Integer id) throws SQLException {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select * from tbcidade where id=?");
		PreparedStatement stmt = this.connection.prepareStatement(sqlBuilder.toString());
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		CidadeRepository cidade = new CidadeRepository();
		while (rs.next()) {
			cidade.setId(rs.getInt("id"));
			cidade.setNome(rs.getString("nome"));
			cidade.setUf(rs.getString("uf"));
			cidade.setAtualizacao(rs.getString("atualizacao"));
		}
		rs.close();
		stmt.close();
		this.connection.commit();
		return cidade;
	}
}
