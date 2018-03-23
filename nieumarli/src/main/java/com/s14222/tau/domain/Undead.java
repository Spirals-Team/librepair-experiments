package com.s14222.tau.domain;

public class Undead{

    public int id;
    public String nazwa;
    public int level;
    public int zycie;
    public int sila;


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    
    }

    public String getNazwa(){
        return nazwa;
    }

    public void setNazwa(String nazwa){
        this.nazwa = nazwa;
    
    }

    public int getZycie(){
        return zycie;
    }

    public void setZycie(int zycie){
        this.zycie = zycie;
    
    }
    public int getLevel(){
        return level;
    }

    public void setLevel(int level){
        this.level = level;
    
    }
    public int getSila(){
        return sila;
    }

    public void setSila(int sila){
        this.sila = sila;
    
    }
    public Undead(){}
    public Undead(int id, String nazwa, int level, int zycie, int sila){
        this.id = id;
        this.nazwa = nazwa;
        this.level = level;
        this.zycie = zycie;
        this.sila = sila;
    }
}
