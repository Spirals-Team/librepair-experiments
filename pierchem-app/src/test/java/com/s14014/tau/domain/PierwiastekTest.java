
package com.s14014.tau.domain;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.s14014.tau.repository.*;

public class PierwiastekTest {

    IPierwiastekRepository pierwiastekRepository;



    @Test
    public void addTest(){
        
        Pierwiastek pierwiastek = new Pierwiastek();
        pierwiastek.setId(2);
        pierwiastek.setNazwa("Lit");
        pierwiastek.setNrGrupy(1);
        pierwiastek.setNrOkresu(2);
        pierwiastek.setLiczbaElektronow(1);

        pierwiastekRepository.add(pierwiastek);
        assertNotNull(pierwiastekRepository.getPierwiastekById(pierwiastek.getId()));


    }

    @Test
    public void updateTest(){
        Pierwiastek pierwiastekToUpdate = pierwiastekRepository.getPierwiastekById(3);

        pierwiastekToUpdate.setNazwa("siarka");
        pierwiastekRepository.updateById(pierwiastekToUpdate);

        assertEquals(pierwiastekRepository.getPierwiastekById(3).getNazwa(), pierwiastekToUpdate.getNazwa());
        assertNotNull(pierwiastekRepository.getPierwiastekById(1));


    }

    @Test
    public void findTest(){
       Pierwiastek pierwiastek = pierwiastekRepository.getPierwiastekById(2);

        assertNotNull(pierwiastek);
        assertEquals("lit", pierwiastekRepository.getPierwiastekById(2).getNazwa());
    }

    @Test
    public void getAllTest(){
        List<Pierwiastek> tabMendelejewa = pierwiastekRepository.getAllPierwiastki();

        assertNotNull(tabMendelejewa);
       Pierwiastek pierwiastek = tabMendelejewa.get(3);
       assertNotNull(pierwiastek);

        try{
            Pierwiastek pierwiastekToCatch = tabMendelejewa.get(0);
        }

        catch(IndexOutOfBoundsException aIndexOutOfBoundsException){
            assertThat(aIndexOutOfBoundsException.getMessage(), is("Index: 3, Size: 3"));
        }
    }

    @Test
    public void deleteTest(){

        
        pierwiastekRepository.deleteById(1);

        List<Pierwiastek> pierwiastki = pierwiastekRepository.getAllPierwiastki();

        assertNull(pierwiastekRepository.getPierwiastekById(1).getNazwa());
        assertEquals(true, !pierwiastki.isEmpty());
    }

    
    @Before
    public void initRepository(){
       pierwiastekRepository = PierwiastekRepositoryFactory.getInstance();
        Pierwiastek wodor = new Pierwiastek(0, "wodor", 1, 1, 1);

        Pierwiastek hel = new Pierwiastek(1, "hel", 1, 18, 2);
        Pierwiastek lit = new Pierwiastek(2, "lit", 2, 1, 1);
        Pierwiastek magnez = new Pierwiastek(3, "magnez", 2, 2, 2);
        Pierwiastek siarka = new Pierwiastek(4, "siarka", 2, 13, 3);

        pierwiastekRepository.add(wodor);
        pierwiastekRepository.add(hel);
        pierwiastekRepository.add(lit);
        pierwiastekRepository.add(magnez);
        pierwiastekRepository.add(siarka);
    }


}