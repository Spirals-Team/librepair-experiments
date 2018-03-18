package org.leagueoftests.repository;

import java.util.List;

import org.leagueoftests.app.Champions;

public interface ChampionsRepository{
    public void initDatabase();
    public List<Champions> getAll();
    public void add(Champions c);
    public void delete(Champions c);
    public void update(Champions oldChampion, Champions newChampion);
    public Champions getById(int id);
    public Champions getByBEPrice(int priceBE);
    public Champions getByRPPrice(int priceRP);
}