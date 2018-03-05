package pl.lebiedzinski;

import org.junit.Before;
import org.junit.Test;
import repository.RastamanRepository;
import repository.RastamanRepositoryFactory;

import static org.junit.Assert.*;

public class RastamanTest {

    RastamanRepository rastamanRepository;

    @Test
    public void FindRastaman() {
        Rastaman high = new Rastaman();
        assertNotNull(high);

    }
    @Test
    public void getById(){
        int idToFind = 1;
        assertNotNull(rastamanRepository.getById(idToFind));
    }

    @Test
    public void addRstaman(){
        Rastaman marley = new Rastaman();
        marley.setId((int) 1);
        marley.setType("Testowy 1");
        rastamanRepository.addRastaman(marley);
        assertNotNull(rastamanRepository.getById(marley.getId()));

    }

    @Test
    public void deleteRastaman(){
        Rastaman marley = rastamanRepository.getById(1);
        rastamanRepository.deleteRastaman(marley);
        if (rastamanRepository.getAll().size() > 0){
            assertNotNull(rastamanRepository.getAll());
        }else {
            assertNull(rastamanRepository.getById(marley.getId()));
        }
    }

    @Test
    public void updateRastaman(){
        Rastaman gentleman = new Rastaman();
        gentleman.setId(1);
        gentleman.setName("Gentleman");
        gentleman.setGrams(5);
        gentleman.setType("Stoned");
        int marleyToUpdate = 1;
        rastamanRepository.updateRastaman(marleyToUpdate, gentleman);
        assertEquals(rastamanRepository.getById(marleyToUpdate).getType(), gentleman.getType());

        for(Rastaman rastaman : rastamanRepository.getAll()){
            if(gentleman.getId() == marleyToUpdate) {
                assertNotEquals(rastaman.getType(), gentleman.getType());
            }
        }
    }

    @Test
    public void getAll(){
        assertNotNull(rastamanRepository.getAll());
    }

    @Before
    public void initRepository(){
        rastamanRepository = (RastamanRepository) RastamanRepositoryFactory.getInstance();
        Rastaman soja = new Rastaman();
        Rastaman protoje = new Rastaman();
        Rastaman chronixx = new Rastaman();
        Rastaman albarosie = new Rastaman();

        soja.setId(1);
        soja.setName("Soja");
        soja.setGrams(15);
        soja.setType("Stoned");

        protoje.setId(2);
        protoje.setName("Protoje");
        protoje.setGrams(10);
        protoje.setType("High");

        chronixx.setId(3);
        chronixx.setName("Chronixx");
        chronixx.setGrams(7);
        chronixx.setType("High AF");

        albarosie.setId(4);
        albarosie.setName("Albarosie");
        albarosie.setGrams(9);
        albarosie.setType("Plain");

        rastamanRepository.addRastaman(soja);
        rastamanRepository.addRastaman(protoje);
        rastamanRepository.addRastaman(chronixx);
        rastamanRepository.addRastaman(albarosie);
    }
}