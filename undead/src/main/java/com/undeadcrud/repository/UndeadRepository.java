package com.undeadcrud.repository;

import com.undeadcrud.domain.Undead;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UndeadRepository {


    Connection getConnection();
    void setConnection(Connection connection) throws SQLException;
    List<Undead> getAll() throws SQLException;
    Undead getById(int id) throws SQLException;
    int addUndead(Undead undead);
    int deleteUndead(Undead undead) throws SQLException;
    int updateUndead(int id, Undead newUndead) throws SQLException;
    //void dropDatatable() throws SQLException;
    /*
    public List<Undead> getAll();
    public void initDatabase();
    public Undead getById(int id);
    public void addUndead(Undead undead);
    public void deleteUndead(Undead undead);
    public void updateUndead(Undead newUndead);
    */


}