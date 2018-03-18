package org.leagueoftests.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.leagueoftests.app.Champions;

public interface ChampionsRepository{
    public void createTables() throws SQLException;

    public Connection getConnection();

	public void setConnection(Connection connection) throws SQLException;

    public List<Champions> getAll();
    public int add(Champions c);
    public int delete(Champions c);
    public int update(Champions oldChampion, Champions newChampion);
    public Champions getById(int id);
    public Champions getByBEPrice(int priceBE);
    public Champions getByRPPrice(int priceRP);
}