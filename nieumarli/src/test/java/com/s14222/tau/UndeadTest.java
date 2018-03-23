package com.s14222.tau;

import com.s14222.tau.dbdemo.service.UndeadManagerImpl;
import com.s14222.tau.domain.Undead;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

public class UndeadTest {

    @Test
    public void TestDodania(){
        Undead undead = new Undead(0, "Trups",1,100,100);
        try {
            UndeadManagerImpl undeadManager = new UndeadManagerImpl();
            undeadManager.addUndead(undead);
            List<Undead> undeads = undeadManager.getAllUndeads();
            for(Undead u : undeads) {
                if(u.getNazwa().contains("Trups")){
                    //undeadManager.deleteUndead(u);
                    return;
                }
            }
        } catch (SQLException e) {
            fail();
        }
        fail();

    }

    @Test
    public void TestUsuniecia() {
        Undead undead = new Undead(0, "Trups", 1, 100, 100);

        try {
            UndeadManagerImpl undeadManager = new UndeadManagerImpl();
            //undeadManager.addUndead(undead);
            List<Undead> undeads = undeadManager.getAllUndeads();

            for (Undead u : undeads) {
                if (u.getNazwa().contains("Trups")) {
                    undeadManager.deleteUndead(u);
                    System.out.println("Usunieto Undead: " + u.nazwa);
                    return;
                }
            }
        } catch (SQLException e) {
            fail();
        }
        fail();
    }
}

