package com.s14014.tau.domain;


public class Pierwiastek {

    private  int id;
    private  String nazwa;
    private  int nrOkresu;
    private  int nrGrupy;
    private  int liczbaElektronow;
    
    
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }

    public String getNazwa(){
        return nazwa;
    }

    public void setNazwa(String nazwa){
        this.nazwa = nazwa;
    }

    public int getNrOkresu(){
        return nrOkresu;
    }

    public void setNrOkresu(int nrOkresu){
        this.nrOkresu = nrOkresu;
    }

    public int getNrGrupy(){
        return nrGrupy;
    }

    public void setNrGrupy(int nrGrupy){
        this.nrGrupy = nrGrupy;
    }

    public int getLiczbaElektronow(){
        return liczbaElektronow;
    }

    public void setLiczbaElektronow(int liczbaElektronow){
        this.liczbaElektronow = liczbaElektronow;
    }

    

	public Pierwiastek(int id, String nazwa, int nrOkresu, int nrGrupy, int liczbaElektronow) {

        this.id = id;
        this.nazwa = nazwa;
        this.nrOkresu = nrOkresu;
        this.nrGrupy = nrGrupy;
        this.liczbaElektronow = liczbaElektronow;
	}

	public Pierwiastek() {
	}

    /*public void przepiszElektrony(){

        if(nrGrupy == 1 || nrGrupy == 2){
            liczbaElektronow = nrGrupy;
        }

        else if(nrGrupy == 13 || nrGrupy == 14 || nrGrupy == 15 || nrGrupy == 16|| nrGrupy == 17 || nrGrupy == 18){
            liczbaElektronow = nrGrupy = 10;
        }

        else{
            liczbaElektronow = 2;
        }

    } */

}