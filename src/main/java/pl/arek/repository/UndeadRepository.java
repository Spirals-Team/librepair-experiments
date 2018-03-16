package pl.arek.repository;

import pl.arek.domain.Undead;

import java.sql.SQLException;
import java.util.List;

public interface UndeadRepository {
    public List<Undead> getAll();
    public void initDatabase();
    public Undead getById(int id) throws SQLException;
    public void addUndead(Undead undead);
    public void deleteUndead(Undead undead);
    public void updateUndead(int oldId, Undead newUndead) throws SQLException;
    public void dropDatatable() throws SQLException;


    }
