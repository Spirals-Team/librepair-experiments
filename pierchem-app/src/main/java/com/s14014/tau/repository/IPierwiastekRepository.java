package com.s14014.tau.repository;

import com.s14014.tau.domain.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IPierwiastekRepository{

    public Connection getConnection();
    public void setConnection(Connection connection) throws SQLException;

    List<Pierwiastek> getAllPierwiastki();
    int add(Pierwiastek p);
    Pierwiastek getPierwiastekById(int id);
    int deleteById(int id);
    int updateById(Pierwiastek p);
    void dropTable();
	

    
    




}