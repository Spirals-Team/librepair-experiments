/*package com.s14222.tau.finder;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import com.s14222.tau.repository.UndeadRepository;
import com.s14222.tau.domain.*;
import java.util.List;
import org.junit.Test;


public class UndeadTest {
  
  /*
  @Test
    public void testTrupa() {
      Trup trup = new Trup();
      trup.sila = 3;
      trup.aktywujSuperMoc();
      assertNotNull(trup);
    }

    @Test
    public void KiedySuperMocAktywowana_ToZwiekszSileO3() {
      Trup trup = new Trup();
      trup.sila = 3;
      trup.aktywujSuperMoc();
      assertEquals(trup.sila, 6);

    }


    UndeadRepository undeadRepository;
    Undead undead = new Undead(1, "Zombie", 1, 100, 10);


    @Test
    public void dodawanieTest(){
        

        undeadRepository.add(undead);
        assertNotNull(undeadRepository.getById(undead.getId()));


    }

    @Test
    public void updateTest(){
        Undead undeadToUpdate = undeadRepository.getById(1);

        undeadToUpdate.setNazwa("Zombie");
        undeadRepository.updateById(undeadToUpdate.getId());

        assertEquals(undeadRepository.getById(1), undeadToUpdate);
        assertThat(undeadToUpdate.getNazwa(), is("Zombie"));
    }

    @Test
    public void znajdzPoId(){
        Undead undead = undeadRepository.getById(1);

        assertNotNull(undead);
        assertEquals("Zombie", undead.getNazwa());
    }

    @Test
    public void getAllTest(){
        List<Undead> undeadList = undeadRepository.getAll();

        assertNotNull(undeadList);
        assertThat(undeadList, hasItem(undeadRepository.getById(1)));

        try{
            Undead undeadToCatch = undeadList.get(0);
        }

        catch(IndexOutOfBoundsException aIndexOutOfBoundsException){
            assertThat(aIndexOutOfBoundsException.getMessage(), is("Index: 0, Size: 0"));
        }
    }

    @Test
    public void usunPoId(){
        undeadRepository.deleteById(1);
        assertNull(undeadRepository.getById(1));
    }

    @Test
    public void inicjujRepozytorium(){
        Undead zombie = new Undead(1, "Zombie", 1, 100, 10);
        Undead wampir = new Undead(2, "Wampir", 2, 250, 50);
        Undead gargulec = new Undead(3, "Gargulec", 2, 200, 50);

        undeadRepository.add(zombie);
        undeadRepository.add(wampir);
        undeadRepository.add(gargulec);
    }

}
*/