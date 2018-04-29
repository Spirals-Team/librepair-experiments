package com.ts.previsao.tempo.previsao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.ts.previsao.tempo.cidade.CidadeRepository;
import com.ts.previsao.tempo.database.DataBaseConnectionFactory;

public class PrevisaoDAO {

    private Connection connection;

    public PrevisaoDAO() throws ClassNotFoundException, SQLException {
        this.connection = new DataBaseConnectionFactory().conectar();
    }

    public boolean createTablePrevisao() throws SQLException {
        Statement stmt = this.connection.createStatement();
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("create table if not exists").append("tbprevisao");
        sqlBuilder.append("(");
        sqlBuilder.append("id int not null").append(",");
        sqlBuilder.append("dia date not null").append(",");
        sqlBuilder.append("tempo char(3) not null").append(",");
        sqlBuilder.append("minima float not null").append(",");
        sqlBuilder.append("maxima float not null").append(",");
        sqlBuilder.append("iuv float not null").append(",");
        sqlBuilder.append("primary key (id, dia)").append(",");
        sqlBuilder.append("foreign key (id) references tbcidade(id)");
        sqlBuilder.append(")");
        stmt.executeUpdate(sqlBuilder.toString());
        stmt.close();
        return true;
    }

    public boolean insertPrevisao(PrevisaoRepository previsao) throws SQLException {
        /* o campo atualizacao irá receber o valor padrão, ou seja, null */
        String sql = "insert or ignore into tbprevisao(id, dia, tempo, minima, maxima, iuv) values(?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        stmt.setInt(1, previsao.getId());
        stmt.setString(2, previsao.getDia());
        stmt.setString(3, previsao.getTempo());
        stmt.setDouble(4, previsao.getMinima());
        stmt.setDouble(5, previsao.getMaxima());
        stmt.setDouble(6, previsao.getIuv());
        stmt.execute();
        stmt.close();
        this.connection.commit();
        return true;
    }

    public List<CidadeRepository> selectCidade(String sql) throws SQLException {
        Statement stmt = this.connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
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
}
