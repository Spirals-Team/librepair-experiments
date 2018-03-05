package repository;

import pl.lebiedzinski.Rastaman;

import java.util.List;

public interface RastamanRepository {

    public List<Rastaman> getAll();
    public void initDatabase();
    public Rastaman getById(int id);
    public void addRastaman(Rastaman rastaman);
    public void deleteRastaman(Rastaman rastaman);
    public void updateRastaman(int oldId, Rastaman newRastaman);

}
