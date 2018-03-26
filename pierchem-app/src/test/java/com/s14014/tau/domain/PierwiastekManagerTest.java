/*package com.s14014.tau.domain;

import static org.junit.Assert.*;
import java.sql.*;

import com.s14014.tau.repository.PierwiastekManagerImpl;
import com.s14014.tau.repository.PierwiastekRepository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@Ignore
@RunWith(JUnit4.class)
public class PierwiastekManagerTest{
    PierwiastekRepository pierwiastekManager;

    public PierwiastekManagerTest() throws SQLException{
        String url = "jdbc:hsqldb:hsql://localhost/workd";
        pierwiastekManager = new PierwiastekManagerImpl(DriverManager.getConnection(url));

    }

    @Test
    public void checkAdding() throws Exception{
        Pierwiastek pierwiastek = new Pierwiastek();
        pierwiastek.setNazwa("Lit");
        pierwiastek.setNrGrupy(1);
        pierwiastek.setNrOkresu(2);
        pierwiastek.setLiczbaElektronow(1);

        assertEquals(1, pierwiastekManager.addPierwiastek(pierwiastek))
    }
}
*/