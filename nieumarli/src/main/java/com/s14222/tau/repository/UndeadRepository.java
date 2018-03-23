package com.s14222.tau.repository;
import com.s14222.tau.domain.*;
import java.util.List;

public interface UndeadRepository{

    public void initDatabase();

    public Undead getById(int id);
    public List<Undead> getAll();
    public void add(Undead u);
    public Undead deleteById(int id);
    public Undead updateById(int id);
	
}