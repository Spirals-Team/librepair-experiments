package com.ts.previsao.tempo.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.ts.previsao.tempo.cidade.CidadeDAO;
import com.ts.previsao.tempo.previsao.PrevisaoDAO;

public class DataBaseConnectionFactory {
    public Connection conectar() throws SQLException, ClassNotFoundException {
        Connection conexao = null;
        Class.forName("org.sqlite.JDBC");
        File bd = new File("bdprevisao.db");
        /* verifica se o arquivo do BD existe na raiz do projeto */
        if (!bd.exists()) {
            /* cria o arquivo do BD na raiz do projeto e cria uma conexão para o BD */
            conexao = DriverManager.getConnection("jdbc:sqlite:bdprevisao.db");
            /* como o BD não existe então é necessário criar as tabelas */
            new CidadeDAO().createTablePrevisao();
            new PrevisaoDAO().createTablePrevisao();
        } else {
            /* cria uma conexão com o BD */
            conexao = DriverManager.getConnection("jdbc:sqlite:bdprevisao.db");
        }
        conexao.setAutoCommit(false);
        return conexao;
    }
}
